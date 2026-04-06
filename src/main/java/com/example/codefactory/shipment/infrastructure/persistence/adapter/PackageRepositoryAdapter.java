package com.example.codefactory.shipment.infrastructure.persistence.adapter;

import com.example.codefactory.shipment.domain.model.Package;
import com.example.codefactory.shipment.domain.port.PackageRepository;
import com.example.codefactory.shipment.infrastructure.mapper.PackageMapper;
import com.example.codefactory.shipment.infrastructure.persistence.entity.PackageEntity;
import com.example.codefactory.shipment.infrastructure.persistence.entity.ShipmentEntity;
import com.example.codefactory.shipment.infrastructure.persistence.repository.PackageJpaRepository;
import com.example.codefactory.shipment.infrastructure.persistence.repository.ShipmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PackageRepositoryAdapter implements PackageRepository {
	private final PackageJpaRepository packageJpaRepository;
	private final ShipmentJpaRepository shipmentJpaRepository;
	private final PackageMapper packageMapper;

	public PackageRepositoryAdapter(
			PackageJpaRepository packageJpaRepository,
			ShipmentJpaRepository shipmentJpaRepository,
			PackageMapper packageMapper
	) {
		this.packageJpaRepository = packageJpaRepository;
		this.shipmentJpaRepository = shipmentJpaRepository;
		this.packageMapper = packageMapper;
	}

	@Override
	public Optional<Package> findByShipmentId(Long shipmentId) {
		return packageJpaRepository.findByShipment_Id(shipmentId).map(packageMapper::toDomain);
	}

	@Override
	public Package save(Package aPackage) {
		ShipmentEntity shipment = shipmentJpaRepository.findById(aPackage.getShipmentId()).orElseThrow();
		PackageEntity toSave = new PackageEntity(
				shipment,
				aPackage.getPeso(),
				aPackage.getLargo(),
				aPackage.getAncho(),
				aPackage.getAlto(),
				aPackage.getDescripcion()
		);
		PackageEntity saved = packageJpaRepository.save(toSave);
		return packageMapper.toDomain(saved);
	}
}
