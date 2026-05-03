package com.example.codefactory.shipment.infrastructure.mapper;

import com.example.codefactory.shipment.domain.model.LogisticEvent;
import com.example.codefactory.shipment.infrastructure.persistence.entity.LogisticEventEntity;
import org.springframework.stereotype.Component;

@Component
public class LogisticEventMapper {
	public LogisticEvent toDomain(LogisticEventEntity entity) {
		return new LogisticEvent(
				entity.getId(),
				entity.getShipment().getId(),
				entity.getTipoEvento(),
				entity.getEstadoResultante(),
				entity.getUbicacion(),
				entity.getObservacion(),
				entity.getFechaEvento()
		);
	}
}
