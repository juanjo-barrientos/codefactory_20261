package com.example.codefactory.shipment.application.dto;

import java.math.BigDecimal;

public record RegisterPackageResponse(
		Long idPaquete,
		Long shipmentId,
		BigDecimal peso,
		BigDecimal largo,
		BigDecimal ancho,
		BigDecimal alto,
		String descripcion
) {
}
