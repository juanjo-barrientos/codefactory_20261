package com.example.codefactory.shipment.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateShipmentRequest(
		@NotNull @Valid PersonRequest sender,
		@NotNull @Valid PersonRequest recipient,
		@NotBlank String tipoServicio,
		@Min(1) @Max(5) Integer nivelPrioridad,
		@NotNull LocalDate fechaEnvio,
		LocalDate fechaEstimada,
		@PositiveOrZero BigDecimal costoTotal,
		String instruccionesEnvio
) {
}
