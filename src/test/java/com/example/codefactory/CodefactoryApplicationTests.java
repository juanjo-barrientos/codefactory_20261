package com.example.codefactory;

import com.example.codefactory.shipment.infrastructure.persistence.entity.PersonEntity;
import com.example.codefactory.shipment.infrastructure.persistence.entity.ShipmentEntity;
import com.example.codefactory.shipment.infrastructure.persistence.repository.LogisticEventJpaRepository;
import com.example.codefactory.shipment.infrastructure.persistence.repository.PackageJpaRepository;
import com.example.codefactory.shipment.infrastructure.persistence.repository.PersonJpaRepository;
import com.example.codefactory.shipment.infrastructure.persistence.repository.ShipmentJpaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;


@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = {
				"spring.datasource.url=jdbc:h2:mem:codefactory-test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE",
				"spring.datasource.username=sa",
				"spring.datasource.password=",
				"spring.datasource.driver-class-name=org.h2.Driver",
				"spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
				"spring.flyway.enabled=false",
				"spring.jpa.hibernate.ddl-auto=create-drop"
		}
)
class CodefactoryApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ShipmentJpaRepository shipmentJpaRepository;

	@Autowired
	private PackageJpaRepository packageJpaRepository;

	@Autowired
	private PersonJpaRepository personJpaRepository;

	@Autowired
	private LogisticEventJpaRepository logisticEventJpaRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private Long shipmentId;

	@BeforeEach
	void setUp() {
		logisticEventJpaRepository.deleteAll();
		packageJpaRepository.deleteAll();
		shipmentJpaRepository.deleteAll();
		personJpaRepository.deleteAll();

		PersonEntity sender = personJpaRepository.save(new PersonEntity(
				"P-SENDER-" + System.nanoTime(),
				"Sender",
				"3001234567",
				"sender" + System.nanoTime() + "@test.com",
				"Calle 1",
				null
		));
		PersonEntity recipient = personJpaRepository.save(new PersonEntity(
				"P-RECIPIENT-" + System.nanoTime(),
				"Recipient",
				"3007654321",
				"recipient" + System.nanoTime() + "@test.com",
				"Calle 2",
				null
		));
		ShipmentEntity shipment = new ShipmentEntity(
				sender,
				recipient,
				"STANDARD",
				1,
				"TRK-" + System.nanoTime(),
				"CREADO",
				LocalDate.now(),
				LocalDate.now().plusDays(2),
				LocalDate.now(),
				new BigDecimal("100.00"),
				null
		);
		shipmentId = shipmentJpaRepository.save(shipment).getId();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void registerPackage_returns201() throws Exception {
		String body = """
				{
				  \"peso\": 1.5,
				  \"largo\": 10,
				  \"ancho\": 5,
				  \"alto\": 2,
				  \"descripcion\": \"Caja\"
				}
				""";

		ResponseEntity<String> response = restTemplate.postForEntity(
				"/api/v1/shipments/{id}/package",
				jsonEntity(body),
				String.class,
				shipmentId
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		JsonNode json = objectMapper.readTree(response.getBody());
		org.junit.jupiter.api.Assertions.assertEquals(shipmentId.longValue(), json.get("shipmentId").asLong());
		org.junit.jupiter.api.Assertions.assertTrue(json.get("idPaquete").asLong() > 0);
		org.junit.jupiter.api.Assertions.assertTrue(json.has("_links"));
	}

	@Test
	void createShipment_generatesTrackingCode_andReturns201() throws Exception {
		String body = """
				{
				  \"sender\": {
				    \"nombre\": \"Juan Pérez\",
				    \"telefono\": \"3001112233\",
				    \"correoElectronico\": \"juan.create@test.com\",
				    \"direccion\": \"Calle 10\",
				    \"referencias\": \"Casa blanca\"
				  },
				  \"recipient\": {
				    \"nombre\": \"María Gómez\",
				    \"telefono\": \"3012223344\",
				    \"correoElectronico\": \"maria.create@test.com\",
				    \"direccion\": \"Carrera 43\",
				    \"referencias\": \"Apto 302\"
				  },
				  \"tipoServicio\": \"Express\",
				  \"nivelPrioridad\": 5,
				  \"fechaEnvio\": \"2026-04-28\",
				  \"fechaEstimada\": \"2026-04-30\",
				  \"costoTotal\": 35000.00,
				  \"instruccionesEnvio\": \"Entregar en horario laboral\"
				}
				""";

		ResponseEntity<String> response = restTemplate.postForEntity(
				"/api/v1/shipments",
				jsonEntity(body),
				String.class
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		JsonNode json = objectMapper.readTree(response.getBody());
		org.junit.jupiter.api.Assertions.assertTrue(json.get("codigoRastreo").asText().startsWith("TRK-"));
		org.junit.jupiter.api.Assertions.assertEquals("Creado", json.get("estadoActual").asText());
		org.junit.jupiter.api.Assertions.assertTrue(json.has("_links"));
	}

	@Test
	void getShipmentByTrackingCode_whenExists_returns200() throws Exception {
		ShipmentEntity shipment = shipmentJpaRepository.findById(shipmentId).orElseThrow();

		ResponseEntity<String> response = restTemplate.getForEntity(
				"/api/v1/shipments/tracking/{trackingCode}",
				String.class,
				shipment.getCodigoRastreo()
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		JsonNode json = objectMapper.readTree(response.getBody());
		org.junit.jupiter.api.Assertions.assertEquals(shipment.getCodigoRastreo(), json.get("codigoRastreo").asText());
		org.junit.jupiter.api.Assertions.assertTrue(json.has("_links"));
	}

	@Test
	void registerPackage_whenShipmentNotFound_returns404() throws Exception {
		String body = """
				{
				  \"peso\": 1.5,
				  \"largo\": 10,
				  \"ancho\": 5,
				  \"alto\": 2,
				  \"descripcion\": \"Caja\"
				}
				""";

		ResponseEntity<String> response = restTemplate.postForEntity(
				"/api/v1/shipments/{id}/package",
				jsonEntity(body),
				String.class,
				999999
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void registerPackage_whenAlreadyExists_returns201() {
		String body = """
				{
				  \"peso\": 1.5,
				  \"largo\": 10,
				  \"ancho\": 5,
				  \"alto\": 2,
				  \"descripcion\": \"Caja\"
				}
				""";

		ResponseEntity<String> first = restTemplate.postForEntity(
				"/api/v1/shipments/{id}/package",
				jsonEntity(body),
				String.class,
				shipmentId
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.CREATED, first.getStatusCode());

		ResponseEntity<String> second = restTemplate.postForEntity(
				"/api/v1/shipments/{id}/package",
				jsonEntity(body),
				String.class,
				shipmentId
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.CREATED, second.getStatusCode());
	}

	@Test
	void registerLogisticEvent_returns201() throws Exception {
		String body = """
				{
				  \"tipoEvento\": \"Despacho\",
				  \"estadoResultante\": \"En tránsito\",
				  \"ubicacion\": \"Centro de distribución Medellín\",
				  \"observacion\": \"Salió hacia destino\",
				  \"fechaEvento\": \"2026-04-28T14:20:00\"
				}
				""";

		ResponseEntity<String> response = restTemplate.postForEntity(
				"/api/v1/tracking/shipments/{shipmentId}/events",
				jsonEntity(body),
				String.class,
				shipmentId
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		JsonNode json = objectMapper.readTree(response.getBody());
		org.junit.jupiter.api.Assertions.assertEquals(shipmentId.longValue(), json.get("shipmentId").asLong());
		org.junit.jupiter.api.Assertions.assertEquals("Despacho", json.get("tipoEvento").asText());
		org.junit.jupiter.api.Assertions.assertTrue(json.has("_links"));
	}

	@Test
	void getShipmentHistory_returnsEventsOrderedByDate() {
		String firstBody = """
				{
				  \"tipoEvento\": \"Creación de envío\",
				  \"estadoResultante\": \"Creado\",
				  \"ubicacion\": \"Sucursal Cali\",
				  \"observacion\": \"Registro inicial\",
				  \"fechaEvento\": \"2026-04-28T09:15:00\"
				}
				""";
		String secondBody = """
				{
				  \"tipoEvento\": \"Despacho\",
				  \"estadoResultante\": \"En tránsito\",
				  \"ubicacion\": \"Sucursal Cali\",
				  \"observacion\": \"Salió hacia destino\",
				  \"fechaEvento\": \"2026-04-28T14:20:00\"
				}
				""";

		restTemplate.postForEntity("/api/v1/tracking/shipments/{shipmentId}/events", jsonEntity(secondBody), String.class, shipmentId);
		restTemplate.postForEntity("/api/v1/tracking/shipments/{shipmentId}/events", jsonEntity(firstBody), String.class, shipmentId);

		ResponseEntity<String> response = restTemplate.getForEntity(
				"/api/v1/tracking/shipments/{shipmentId}/history",
				String.class,
				shipmentId
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		org.junit.jupiter.api.Assertions.assertTrue(response.getBody().contains("Creación de envío"));
		org.junit.jupiter.api.Assertions.assertTrue(response.getBody().indexOf("Creación de envío") < response.getBody().indexOf("Despacho"));
	}

	@Test
	void registerPackage_whenInvalidBody_returns400() {
		String body = """
				{
				  \"peso\": -1,
				  \"largo\": 10,
				  \"ancho\": 5,
				  \"alto\": 2,
				  \"descripcion\": \"Caja\"
				}
				""";

		ResponseEntity<String> response = restTemplate.postForEntity(
				"/api/v1/shipments/{id}/package",
				jsonEntity(body),
				String.class,
				shipmentId
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void registerSender_returns201_andAssociatesToShipment() throws Exception {
		String body = """
				{
				  \"nombre\": \"Juan\",
				  \"telefono\": \"3001234567\",
				  \"correoElectronico\": \"juan@test.com\",
				  \"direccion\": \"Calle 1\",
				  \"referencias\": \"Apto 2\"
				}
				""";

		ResponseEntity<String> response = restTemplate.postForEntity(
				"/api/v1/shipments/{id}/sender",
				jsonEntity(body),
				String.class,
				shipmentId
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		JsonNode json = objectMapper.readTree(response.getBody());
		org.junit.jupiter.api.Assertions.assertEquals(shipmentId.longValue(), json.get("shipmentId").asLong());
		org.junit.jupiter.api.Assertions.assertTrue(json.get("senderId").asText().length() > 0);
		org.junit.jupiter.api.Assertions.assertTrue(json.has("_links"));

		ShipmentEntity shipment = shipmentJpaRepository.findById(shipmentId).orElseThrow();
		String senderId = shipment.getSender().getId();
		PersonEntity sender = personJpaRepository.findById(senderId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertEquals("Juan", sender.getNombre());
	}

	private static HttpEntity<String> jsonEntity(String body) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
		return new HttpEntity<>(body, headers);
	}

}
