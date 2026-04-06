package com.example.codefactory.shipment.infrastructure.mapper;

import com.example.codefactory.shipment.domain.model.Person;
import com.example.codefactory.shipment.infrastructure.persistence.entity.PersonEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
	public Person toDomain(PersonEntity entity) {
		return new Person(
				entity.getId(),
				entity.getNombre(),
				entity.getTelefono(),
				entity.getCorreoElectronico(),
				entity.getDireccion(),
				entity.getReferencias()
		);
	}

	public PersonEntity toEntity(Person person) {
		return new PersonEntity(
				person.getId(),
				person.getNombre(),
				person.getTelefono(),
				person.getCorreoElectronico(),
				person.getDireccion(),
				person.getReferencias()
		);
	}
}
