package com.example.codefactory.shipment.domain.port;

import com.example.codefactory.shipment.domain.model.Person;

import java.util.Optional;

public interface PersonRepository {
	Optional<Person> findById(String id);

	Person save(Person person);
}
