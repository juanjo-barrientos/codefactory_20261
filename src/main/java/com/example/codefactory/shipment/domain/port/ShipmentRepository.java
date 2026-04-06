package com.example.codefactory.shipment.domain.port;

import com.example.codefactory.shipment.domain.model.Shipment;

import java.util.Optional;

public interface ShipmentRepository {
	Optional<Shipment> findById(Long id);

	Shipment save(Shipment shipment);
}
