package com.example.codefactory.shipment.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "shipments")
public class ShipmentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_envio", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id", nullable = false)
	private PersonEntity sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipient_id", nullable = false)
	private PersonEntity recipient;

	@Column(name = "tipo_servicio", nullable = false)
	private String tipoServicio;

	@Column(name = "nivel_prioridad", nullable = false)
	private Integer nivelPrioridad;

	@Column(name = "codigo_rastreo", nullable = false, unique = true)
	private String codigoRastreo;

	@Column(name = "estado_actual", nullable = false)
	private String estadoActual;

	@Column(name = "fecha_envio", nullable = false)
	private LocalDate fechaEnvio;

	@Column(name = "fecha_estimada")
	private LocalDate fechaEstimada;

	@Column(name = "fecha_actualizacion")
	private LocalDate fechaActualizacion;

	@Column(name = "costo_total", nullable = false, precision = 12, scale = 2)
	private BigDecimal costoTotal;

	@Column(name = "instrucciones_envio", length = 1000)
	private String instruccionesEnvio;

	@OneToMany(mappedBy = "shipment", fetch = FetchType.LAZY)
	private List<PackageEntity> packages;

	protected ShipmentEntity() {
	}

	public ShipmentEntity(
			PersonEntity sender,
			PersonEntity recipient,
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
		this.sender = sender;
		this.recipient = recipient;
		this.tipoServicio = tipoServicio;
		this.nivelPrioridad = nivelPrioridad;
		this.codigoRastreo = codigoRastreo;
		this.estadoActual = estadoActual;
		this.fechaEnvio = fechaEnvio;
		this.fechaEstimada = fechaEstimada;
		this.fechaActualizacion = fechaActualizacion;
		this.costoTotal = costoTotal;
		this.instruccionesEnvio = instruccionesEnvio;
	}

	public Long getId() {
		return id;
	}

	public PersonEntity getSender() {
		return sender;
	}

	public void setSender(PersonEntity sender) {
		this.sender = sender;
	}

	public PersonEntity getRecipient() {
		return recipient;
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
}
