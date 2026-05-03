package com.example.codefactory.shipment.application.command;

import com.example.codefactory.shipment.application.dto.ShipmentResponse;
import com.example.codefactory.shipment.domain.model.Shipment;
import com.example.codefactory.shipment.domain.port.ShipmentRepository;
import com.example.codefactory.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetShipmentByTrackingCodeUseCase {
	private final ShipmentRepository shipmentRepository;

	public GetShipmentByTrackingCodeUseCase(ShipmentRepository shipmentRepository) {
		this.shipmentRepository = shipmentRepository;
	}

	@Transactional(readOnly = true)
	public ShipmentResponse getByTrackingCode(String trackingCode) {
		Shipment shipment = shipmentRepository.findByTrackingCode(trackingCode)
				.orElseThrow(() -> new NotFoundException("Shipment not found for tracking code: " + trackingCode));
		return toResponse(shipment);
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
