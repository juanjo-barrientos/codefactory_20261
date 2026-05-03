package com.example.codefactory.shipment.application.command;

import com.example.codefactory.shipment.application.dto.LogisticEventResponse;
import com.example.codefactory.shipment.application.dto.RegisterLogisticEventRequest;
import com.example.codefactory.shipment.domain.model.LogisticEvent;
import com.example.codefactory.shipment.domain.port.LogisticEventRepository;
import com.example.codefactory.shipment.domain.port.ShipmentRepository;
import com.example.codefactory.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterLogisticEventUseCase {
	private final ShipmentRepository shipmentRepository;
	private final LogisticEventRepository logisticEventRepository;

	public RegisterLogisticEventUseCase(
			ShipmentRepository shipmentRepository,
			LogisticEventRepository logisticEventRepository
	) {
		this.shipmentRepository = shipmentRepository;
		this.logisticEventRepository = logisticEventRepository;
	}

	@Transactional
	public LogisticEventResponse register(Long shipmentId, RegisterLogisticEventRequest request) {
		if (shipmentRepository.findById(shipmentId).isEmpty()) {
			throw new NotFoundException("Shipment not found: " + shipmentId);
		}

		LogisticEvent toSave = new LogisticEvent(
				null,
				shipmentId,
				request.tipoEvento(),
				request.estadoResultante(),
				request.ubicacion(),
				request.observacion(),
				request.fechaEvento()
		);
		LogisticEvent saved = logisticEventRepository.save(toSave);
		return toResponse(saved);
	}

	private LogisticEventResponse toResponse(LogisticEvent event) {
		return new LogisticEventResponse(
				event.getId(),
				event.getShipmentId(),
				event.getTipoEvento(),
				event.getEstadoResultante(),
				event.getUbicacion(),
				event.getObservacion(),
				event.getFechaEvento()
		);
	}
}
