package com.example.codefactory.shipment.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Package {
	private final Long id;
	private final Long shipmentId;
	private final BigDecimal peso;
	private final BigDecimal largo;
	private final BigDecimal ancho;
	private final BigDecimal alto;
	private final String descripcion;

	public Package(
			Long id,
			Long shipmentId,
			BigDecimal peso,
			BigDecimal largo,
			BigDecimal ancho,
			BigDecimal alto,
			String descripcion
	) {
		this.id = id;
		this.shipmentId = Objects.requireNonNull(shipmentId);
		this.peso = Objects.requireNonNull(peso);
		this.largo = Objects.requireNonNull(largo);
		this.ancho = Objects.requireNonNull(ancho);
		this.alto = Objects.requireNonNull(alto);
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public Long getShipmentId() {
		return shipmentId;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public BigDecimal getLargo() {
		return largo;
	}

	public BigDecimal getAncho() {
		return ancho;
	}

	public BigDecimal getAlto() {
		return alto;
	}

	public String getDescripcion() {
		return descripcion;
	}
}
