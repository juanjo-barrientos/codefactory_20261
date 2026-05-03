package com.example.codefactory.shipment.infrastructure.mapper;

import com.example.codefactory.shipment.domain.model.Shipment;
import com.example.codefactory.shipment.infrastructure.persistence.entity.ShipmentEntity;
import org.springframework.stereotype.Component;

@Component
public class ShipmentMapper {
	public Shipment toDomain(ShipmentEntity entity) {
		String senderId = entity.getSender() != null ? entity.getSender().getId() : null;
		String recipientId = entity.getRecipient() != null ? entity.getRecipient().getId() : null;
		return new Shipment(
				entity.getId(),
				senderId,
				recipientId,
				entity.getTipoServicio(),
				entity.getNivelPrioridad(),
				entity.getCodigoRastreo(),
				entity.getEstadoActual(),
				entity.getFechaEnvio(),
				entity.getFechaEstimada(),
				entity.getFechaActualizacion(),
				entity.getCostoTotal(),
				entity.getInstruccionesEnvio()
		);
	}
}
