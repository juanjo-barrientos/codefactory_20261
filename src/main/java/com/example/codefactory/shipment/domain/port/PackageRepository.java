package com.example.codefactory.shipment.domain.port;

import com.example.codefactory.shipment.domain.model.Package;

import java.util.Optional;

public interface PackageRepository {
	Optional<Package> findByShipmentId(Long shipmentId);

	Package save(Package aPackage);
}
