package com.example.codefactory.shipment.application.command;

import com.example.codefactory.shipment.application.dto.LogisticEventResponse;
import com.example.codefactory.shipment.domain.model.LogisticEvent;
import com.example.codefactory.shipment.domain.port.LogisticEventRepository;
import com.example.codefactory.shipment.domain.port.ShipmentRepository;
import com.example.codefactory.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetShipmentHistoryUseCase {
	private final ShipmentRepository shipmentRepository;
	private final LogisticEventRepository logisticEventRepository;

	public GetShipmentHistoryUseCase(
			ShipmentRepository shipmentRepository,
			LogisticEventRepository logisticEventRepository
	) {
		this.shipmentRepository = shipmentRepository;
		this.logisticEventRepository = logisticEventRepository;
	}

	@Transactional(readOnly = true)
	public List<LogisticEventResponse> getHistory(Long shipmentId) {
		if (shipmentRepository.findById(shipmentId).isEmpty()) {
			throw new NotFoundException("Shipment not found: " + shipmentId);
		}

		return logisticEventRepository.findByShipmentId(shipmentId)
				.stream()
				.map(this::toResponse)
				.toList();
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
