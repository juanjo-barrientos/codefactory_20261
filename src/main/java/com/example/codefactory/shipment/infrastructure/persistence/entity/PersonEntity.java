package com.example.codefactory.shipment.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "persons")
public class PersonEntity {
	@Id
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "correo_electronico", unique = true)
	private String correoElectronico;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "referencias")
	private String referencias;

	protected PersonEntity() {
	}

	public PersonEntity(String id, String nombre, String telefono, String correoElectronico, String direccion, String referencias) {
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.correoElectronico = correoElectronico;
		this.direccion = direccion;
		this.referencias = referencias;
	}

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getReferencias() {
		return referencias;
	}
}
