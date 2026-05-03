package com.example.codefactory.shipment.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "packages")
public class PackageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_paquete", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipment_id", nullable = false)
	private ShipmentEntity shipment;

	@Column(name = "peso", nullable = false, precision = 10, scale = 2)
	private BigDecimal peso;

	@Column(name = "largo", precision = 10, scale = 2)
	private BigDecimal largo;

	@Column(name = "ancho", precision = 10, scale = 2)
	private BigDecimal ancho;

	@Column(name = "alto", precision = 10, scale = 2)
	private BigDecimal alto;

	@Column(name = "descripcion", length = 500)
	private String descripcion;

	protected PackageEntity() {
	}

	public PackageEntity(ShipmentEntity shipment, BigDecimal peso, BigDecimal largo, BigDecimal ancho, BigDecimal alto, String descripcion) {
		this.shipment = shipment;
		this.peso = peso;
		this.largo = largo;
		this.ancho = ancho;
		this.alto = alto;
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public ShipmentEntity getShipment() {
		return shipment;
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
