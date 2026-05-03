package com.example.codefactory.shipment.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RegisterLogisticEventRequest(
		@NotBlank String tipoEvento,
		@NotBlank String estadoResultante,
		String ubicacion,
		String observacion,
		@NotNull LocalDateTime fechaEvento
) {
}
