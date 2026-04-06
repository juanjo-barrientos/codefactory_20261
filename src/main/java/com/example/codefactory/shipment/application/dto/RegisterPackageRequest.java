package com.example.codefactory.shipment.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RegisterPackageRequest(
		@NotNull @Positive BigDecimal peso,
		@NotNull @Positive BigDecimal largo,
		@NotNull @Positive BigDecimal ancho,
		@NotNull @Positive BigDecimal alto,
		@Size(max = 500) String descripcion
) {
}
