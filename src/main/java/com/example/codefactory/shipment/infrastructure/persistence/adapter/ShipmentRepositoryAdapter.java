package com.example.codefactory.shipment.infrastructure.persistence.adapter;

import com.example.codefactory.shipment.domain.model.Shipment;
import com.example.codefactory.shipment.domain.port.ShipmentRepository;
import com.example.codefactory.shipment.infrastructure.mapper.ShipmentMapper;
import com.example.codefactory.shipment.infrastructure.persistence.entity.PersonEntity;
import com.example.codefactory.shipment.infrastructure.persistence.entity.ShipmentEntity;
import com.example.codefactory.shipment.infrastructure.persistence.repository.PersonJpaRepository;
import com.example.codefactory.shipment.infrastructure.persistence.repository.ShipmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShipmentRepositoryAdapter implements ShipmentRepository {
	private final ShipmentJpaRepository shipmentJpaRepository;
	private final PersonJpaRepository personJpaRepository;
	private final ShipmentMapper shipmentMapper;

	public ShipmentRepositoryAdapter(
			ShipmentJpaRepository shipmentJpaRepository,
			PersonJpaRepository personJpaRepository,
			ShipmentMapper shipmentMapper
	) {
		this.shipmentJpaRepository = shipmentJpaRepository;
		this.personJpaRepository = personJpaRepository;
		this.shipmentMapper = shipmentMapper;
	}

	@Override
	public Optional<Shipment> findById(Long id) {
		return shipmentJpaRepository.findById(id).map(shipmentMapper::toDomain);
	}

	@Override
	public Optional<Shipment> findByTrackingCode(String trackingCode) {
		return shipmentJpaRepository.findByCodigoRastreo(trackingCode).map(shipmentMapper::toDomain);
	}

	@Override
	public boolean existsByTrackingCode(String trackingCode) {
		return shipmentJpaRepository.existsByCodigoRastreo(trackingCode);
	}

	@Override
	public Shipment save(Shipment shipment) {
		ShipmentEntity entity = shipment.getId() == null ? createEntity(shipment) : shipmentJpaRepository.findById(shipment.getId()).orElseThrow();

		if (shipment.getSenderId() != null) {
			PersonEntity sender = personJpaRepository.findById(shipment.getSenderId()).orElseThrow();
			entity.setSender(sender);
		}

		ShipmentEntity saved = shipmentJpaRepository.save(entity);
		return shipmentMapper.toDomain(saved);
	}

	private ShipmentEntity createEntity(Shipment shipment) {
		PersonEntity sender = personJpaRepository.findById(shipment.getSenderId()).orElseThrow();
		PersonEntity recipient = personJpaRepository.findById(shipment.getRecipientId()).orElseThrow();
		return new ShipmentEntity(
				sender,
				recipient,
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
