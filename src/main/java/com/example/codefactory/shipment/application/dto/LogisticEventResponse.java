package com.example.codefactory.shipment.application.dto;

import java.time.LocalDateTime;

public record LogisticEventResponse(
		Long id,
		Long shipmentId,
		String tipoEvento,
		String estadoResultante,
		String ubicacion,
		String observacion,
		LocalDateTime fechaEvento
) {
}
