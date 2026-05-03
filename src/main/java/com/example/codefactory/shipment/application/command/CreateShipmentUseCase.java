package com.example.codefactory.shipment.application.command;

import com.example.codefactory.shipment.application.dto.CreateShipmentRequest;
import com.example.codefactory.shipment.application.dto.PersonRequest;
import com.example.codefactory.shipment.application.dto.ShipmentResponse;
import com.example.codefactory.shipment.domain.model.Person;
import com.example.codefactory.shipment.domain.model.Shipment;
import com.example.codefactory.shipment.domain.port.PersonRepository;
import com.example.codefactory.shipment.domain.port.ShipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class CreateShipmentUseCase {
	private static final int MAX_TRACKING_CODE_ATTEMPTS = 10;

	private final ShipmentRepository shipmentRepository;
	private final PersonRepository personRepository;

	public CreateShipmentUseCase(ShipmentRepository shipmentRepository, PersonRepository personRepository) {
		this.shipmentRepository = shipmentRepository;
		this.personRepository = personRepository;
	}

	@Transactional
	public ShipmentResponse create(CreateShipmentRequest request) {
		Person sender = personRepository.save(toPerson(request.sender()));
		Person recipient = personRepository.save(toPerson(request.recipient()));
		String trackingCode = generateUniqueTrackingCode();
		LocalDate fechaEnvio = request.fechaEnvio();
		Shipment toSave = new Shipment(
				null,
				sender.getId(),
				recipient.getId(),
				request.tipoServicio(),
				request.nivelPrioridad() != null ? request.nivelPrioridad() : 3,
				trackingCode,
				"Creado",
				fechaEnvio,
				request.fechaEstimada(),
				fechaEnvio,
				request.costoTotal() != null ? request.costoTotal() : BigDecimal.ZERO,
				request.instruccionesEnvio()
		);
		Shipment saved = shipmentRepository.save(toSave);
		return toResponse(saved);
	}

	private Person toPerson(PersonRequest request) {
		return new Person(
				UUID.randomUUID().toString(),
				request.nombre(),
				request.telefono(),
				request.correoElectronico(),
				request.direccion(),
				request.referencias()
		);
	}

	private String generateUniqueTrackingCode() {
		for (int attempt = 0; attempt < MAX_TRACKING_CODE_ATTEMPTS; attempt++) {
			String trackingCode = "TRK-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
			if (!shipmentRepository.existsByTrackingCode(trackingCode)) {
				return trackingCode;
			}
		}
		throw new IllegalStateException("Unable to generate unique tracking code");
	}

	private ShipmentResponse toResponse(Shipment shipment) {
		return new ShipmentResponse(
				shipment.getId(),
				shipment.getSenderId(),
				shipment.getRecipientId(),
				shipment.getTipoServicio(),
				shipment.getNivelPrioridad(),
				shipment.getCodigoRastreo(),
				shipment.getEstadoActual(),
				shipment.getFechaEnvio(),
				shipment.getFechaEstimada(),
				shipment.getFechaActualizacion(),
				shipment.getCostoTotal(),
				shipment.getInstruccionesEnvio()
		);
	}
}
