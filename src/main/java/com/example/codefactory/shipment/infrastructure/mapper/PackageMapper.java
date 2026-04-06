package com.example.codefactory.shipment.infrastructure.mapper;

import com.example.codefactory.shipment.domain.model.Package;
import com.example.codefactory.shipment.infrastructure.persistence.entity.PackageEntity;
import org.springframework.stereotype.Component;

@Component
public class PackageMapper {
	public Package toDomain(PackageEntity entity) {
		return new Package(
				entity.getId(),
				entity.getShipment().getId(),
				entity.getPeso(),
				entity.getLargo(),
				entity.getAncho(),
				entity.getAlto(),
				entity.getDescripcion()
		);
	}
}
