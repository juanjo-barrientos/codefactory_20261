package com.example.codefactory.shipment.infrastructure.persistence.adapter;

import com.example.codefactory.shipment.domain.model.Person;
import com.example.codefactory.shipment.domain.port.PersonRepository;
import com.example.codefactory.shipment.infrastructure.mapper.PersonMapper;
import com.example.codefactory.shipment.infrastructure.persistence.entity.PersonEntity;
import com.example.codefactory.shipment.infrastructure.persistence.repository.PersonJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonRepositoryAdapter implements PersonRepository {
	private final PersonJpaRepository personJpaRepository;
	private final PersonMapper personMapper;

	public PersonRepositoryAdapter(PersonJpaRepository personJpaRepository, PersonMapper personMapper) {
		this.personJpaRepository = personJpaRepository;
		this.personMapper = personMapper;
	}

	@Override
	public Optional<Person> findById(String id) {
		return personJpaRepository.findById(id).map(personMapper::toDomain);
	}

	@Override
	public Person save(Person person) {
		PersonEntity saved = personJpaRepository.save(personMapper.toEntity(person));
		return personMapper.toDomain(saved);
	}
}
