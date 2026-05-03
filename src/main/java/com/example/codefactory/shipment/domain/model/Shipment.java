package com.example.codefactory.shipment.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Shipment {
	private final Long id;
	private final String senderId;
	private final String recipientId;
	private final String tipoServicio;
	private final Integer nivelPrioridad;
	private final String codigoRastreo;
	private final String estadoActual;
	private final LocalDate fechaEnvio;
	private final LocalDate fechaEstimada;
	private final LocalDate fechaActualizacion;
	private final BigDecimal costoTotal;
	private final String instruccionesEnvio;

	public Shipment(Long id, String senderId, String recipientId, String tipoServicio, Integer nivelPrioridad, String codigoRastreo, String estadoActual, LocalDate fechaEnvio, LocalDate fechaEstimada, LocalDate fechaActualizacion, BigDecimal costoTotal, String instruccionesEnvio) {
		this.id = id;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.tipoServicio = Objects.requireNonNull(tipoServicio);
		this.nivelPrioridad = Objects.requireNonNull(nivelPrioridad);
		this.codigoRastreo = Objects.requireNonNull(codigoRastreo);
		this.estadoActual = Objects.requireNonNull(estadoActual);
		this.fechaEnvio = Objects.requireNonNull(fechaEnvio);
		this.fechaEstimada = fechaEstimada;
		this.fechaActualizacion = fechaActualizacion;
		this.costoTotal = Objects.requireNonNull(costoTotal);
		this.instruccionesEnvio = instruccionesEnvio;
	}

	public Long getId() {
		return id;
	}

	public String getSenderId() {
		return senderId;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public String getTipoServicio() {
		return tipoServicio;
	}

	public Integer getNivelPrioridad() {
		return nivelPrioridad;
	}

	public String getCodigoRastreo() {
		return codigoRastreo;
	}

	public String getEstadoActual() {
		return estadoActual;
	}

	public LocalDate getFechaEnvio() {
		return fechaEnvio;
	}

	public LocalDate getFechaEstimada() {
		return fechaEstimada;
	}

	public LocalDate getFechaActualizacion() {
		return fechaActualizacion;
	}

	public BigDecimal getCostoTotal() {
		return costoTotal;
	}

	public String getInstruccionesEnvio() {
		return instruccionesEnvio;
	}

	public Shipment withSenderId(String senderId) {
		return new Shipment(this.id, senderId, this.recipientId, this.tipoServicio, this.nivelPrioridad, this.codigoRastreo, this.estadoActual, this.fechaEnvio, this.fechaEstimada, this.fechaActualizacion, this.costoTotal, this.instruccionesEnvio);
	}
}
