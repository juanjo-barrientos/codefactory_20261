package com.example.codefactory;

import com.example.codefactory.shipment.infrastructure.persistence.entity.PersonEntity;
import com.example.codefactory.shipment.infrastructure.persistence.entity.ShipmentEntity;
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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
	private ObjectMapper objectMapper;

	private Long shipmentId;

	@BeforeEach
	void setUp() {
		packageJpaRepository.deleteAll();
		shipmentJpaRepository.deleteAll();
		personJpaRepository.deleteAll();

		ShipmentEntity shipment = new ShipmentEntity(
				null,
				null,
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
				"/api/shipments/{id}/package",
				jsonEntity(body),
				String.class,
				shipmentId
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		JsonNode json = objectMapper.readTree(response.getBody());
		org.junit.jupiter.api.Assertions.assertEquals(shipmentId.longValue(), json.get("shipmentId").asLong());
		org.junit.jupiter.api.Assertions.assertTrue(json.get("idPaquete").asLong() > 0);
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
				"/api/shipments/{id}/package",
				jsonEntity(body),
				String.class,
				999999
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void registerPackage_whenAlreadyExists_returns409() {
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
				"/api/shipments/{id}/package",
				jsonEntity(body),
				String.class,
				shipmentId
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.CREATED, first.getStatusCode());

		ResponseEntity<String> second = restTemplate.postForEntity(
				"/api/shipments/{id}/package",
				jsonEntity(body),
				String.class,
				shipmentId
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.CONFLICT, second.getStatusCode());
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
				"/api/shipments/{id}/package",
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
				"/api/shipments/{id}/sender",
				jsonEntity(body),
				String.class,
				shipmentId
		);
		org.junit.jupiter.api.Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		JsonNode json = objectMapper.readTree(response.getBody());
		org.junit.jupiter.api.Assertions.assertEquals(shipmentId.longValue(), json.get("shipmentId").asLong());
		org.junit.jupiter.api.Assertions.assertTrue(json.get("senderId").asText().length() > 0);

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
