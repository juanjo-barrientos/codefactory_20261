package com.example.codefactory.shipment.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ShipmentResponse(
		Long id,
		String senderId,
		String recipientId,
		String tipoServicio,
		Integer nivelPrioridad,
		String codigoRastreo,
		String estadoActual,
		LocalDate fechaEnvio,
		LocalDate fechaEstimada,
		LocalDate fechaActualizacion,
		BigDecimal costoTotal,
		String instruccionesEnvio
) {
}
