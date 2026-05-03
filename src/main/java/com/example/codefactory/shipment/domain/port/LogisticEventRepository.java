package com.example.codefactory.shipment.domain.port;

import com.example.codefactory.shipment.domain.model.LogisticEvent;

import java.util.List;

public interface LogisticEventRepository {
	LogisticEvent save(LogisticEvent logisticEvent);

	List<LogisticEvent> findByShipmentId(Long shipmentId);
}
