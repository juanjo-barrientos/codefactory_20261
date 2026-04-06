package com.example.codefactory.shipment.domain.model;

import java.util.Objects;

public class Person {
	private final String id;
	private final String nombre;
	private final String telefono;
	private final String correoElectronico;
	private final String direccion;
	private final String referencias;

	public Person(
			String id,
			String nombre,
			String telefono,
			String correoElectronico,
			String direccion,
			String referencias
	) {
		this.id = Objects.requireNonNull(id);
		this.nombre = Objects.requireNonNull(nombre);
		this.telefono = Objects.requireNonNull(telefono);
		this.correoElectronico = Objects.requireNonNull(correoElectronico);
		this.direccion = Objects.requireNonNull(direccion);
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
