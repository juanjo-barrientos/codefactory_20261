package com.example.codefactory.shipment.infrastructure.persistence.adapter;

import com.example.codefactory.shipment.domain.model.LogisticEvent;
import com.example.codefactory.shipment.domain.port.LogisticEventRepository;
import com.example.codefactory.shipment.infrastructure.mapper.LogisticEventMapper;
import com.example.codefactory.shipment.infrastructure.persistence.entity.LogisticEventEntity;
import com.example.codefactory.shipment.infrastructure.persistence.entity.ShipmentEntity;
import com.example.codefactory.shipment.infrastructure.persistence.repository.LogisticEventJpaRepository;
import com.example.codefactory.shipment.infrastructure.persistence.repository.ShipmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogisticEventRepositoryAdapter implements LogisticEventRepository {
	private final LogisticEventJpaRepository logisticEventJpaRepository;
	private final ShipmentJpaRepository shipmentJpaRepository;
	private final LogisticEventMapper logisticEventMapper;

	public LogisticEventRepositoryAdapter(
			LogisticEventJpaRepository logisticEventJpaRepository,
			ShipmentJpaRepository shipmentJpaRepository,
			LogisticEventMapper logisticEventMapper
	) {
		this.logisticEventJpaRepository = logisticEventJpaRepository;
		this.shipmentJpaRepository = shipmentJpaRepository;
		this.logisticEventMapper = logisticEventMapper;
	}

	@Override
	public LogisticEvent save(LogisticEvent logisticEvent) {
		ShipmentEntity shipment = shipmentJpaRepository.findById(logisticEvent.getShipmentId()).orElseThrow();
		LogisticEventEntity toSave = new LogisticEventEntity(
				shipment,
				logisticEvent.getTipoEvento(),
				logisticEvent.getEstadoResultante(),
				logisticEvent.getUbicacion(),
				logisticEvent.getObservacion(),
				logisticEvent.getFechaEvento()
		);
		LogisticEventEntity saved = logisticEventJpaRepository.save(toSave);
		return logisticEventMapper.toDomain(saved);
	}

	@Override
	public List<LogisticEvent> findByShipmentId(Long shipmentId) {
		return logisticEventJpaRepository.findByShipment_IdOrderByFechaEventoAsc(shipmentId)
				.stream()
				.map(logisticEventMapper::toDomain)
				.toList();
	}
}
