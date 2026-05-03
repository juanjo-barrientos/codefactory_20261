package com.example.codefactory.shipment.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class LogisticEvent {
	private final Long id;
	private final Long shipmentId;
	private final String tipoEvento;
	private final String estadoResultante;
	private final String ubicacion;
	private final String observacion;
	private final LocalDateTime fechaEvento;

	public LogisticEvent(
			Long id,
			Long shipmentId,
			String tipoEvento,
			String estadoResultante,
			String ubicacion,
			String observacion,
			LocalDateTime fechaEvento
	) {
		this.id = id;
		this.shipmentId = Objects.requireNonNull(shipmentId);
		this.tipoEvento = Objects.requireNonNull(tipoEvento);
		this.estadoResultante = Objects.requireNonNull(estadoResultante);
		this.ubicacion = ubicacion;
		this.observacion = observacion;
		this.fechaEvento = Objects.requireNonNull(fechaEvento);
	}

	public Long getId() {
		return id;
	}

	public Long getShipmentId() {
		return shipmentId;
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
