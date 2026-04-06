package com.example.codefactory.shipment.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterSenderRequest(
		@NotBlank @Size(max = 255) String nombre,
		@NotBlank @Size(max = 50) String telefono,
		@NotBlank @Email @Size(max = 255) String correoElectronico,
		@NotBlank @Size(max = 500) String direccion,
		@Size(max = 500) String referencias
) {
}
