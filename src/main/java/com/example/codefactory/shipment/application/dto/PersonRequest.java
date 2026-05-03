package com.example.codefactory.shipment.application.dto;

import jakarta.validation.constraints.NotBlank;

public record PersonRequest(
		@NotBlank String nombre,
		String telefono,
		String correoElectronico,
		String direccion,
		String referencias
) {
}
