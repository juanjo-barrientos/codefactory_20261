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

import java.time.LocalDateTime;

@Entity
@Table(name = "logistic_events")
public class LogisticEventEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_evento", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipment_id", nullable = false)
	private ShipmentEntity shipment;

	@Column(name = "tipo_evento", nullable = false)
	private String tipoEvento;

	@Column(name = "estado_resultante", nullable = false)
	private String estadoResultante;

	@Column(name = "ubicacion")
	private String ubicacion;

	@Column(name = "observacion")
	private String observacion;

	@Column(name = "fecha_evento", nullable = false)
	private LocalDateTime fechaEvento;

	protected LogisticEventEntity() {
	}

	public LogisticEventEntity(
			ShipmentEntity shipment,
			String tipoEvento,
			String estadoResultante,
			String ubicacion,
			String observacion,
			LocalDateTime fechaEvento
	) {
		this.shipment = shipment;
		this.tipoEvento = tipoEvento;
		this.estadoResultante = estadoResultante;
		this.ubicacion = ubicacion;
		this.observacion = observacion;
		this.fechaEvento = fechaEvento;
	}

	public Long getId() {
		return id;
	}

	public ShipmentEntity getShipment() {
		return shipment;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public String getEstadoResultante() {
		return estadoResultante;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public String getObservacion() {
		return observacion;
	}

	public LocalDateTime getFechaEvento() {
		return fechaEvento;
	}
}
