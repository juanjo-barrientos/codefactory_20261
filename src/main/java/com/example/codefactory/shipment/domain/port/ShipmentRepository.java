package com.example.codefactory.shipment.domain.port;

import com.example.codefactory.shipment.domain.model.Shipment;

import java.util.Optional;

public interface ShipmentRepository {
	Optional<Shipment> findById(Long id);

	Optional<Shipment> findByTrackingCode(String trackingCode);

	boolean existsByTrackingCode(String trackingCode);

	Shipment save(Shipment shipment);
}
