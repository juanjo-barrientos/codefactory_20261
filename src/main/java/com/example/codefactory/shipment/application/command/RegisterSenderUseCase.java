package com.example.codefactory.shipment.application.command;

import com.example.codefactory.shipment.application.dto.RegisterSenderRequest;
import com.example.codefactory.shipment.application.dto.RegisterSenderResponse;
import com.example.codefactory.shipment.domain.model.Person;
import com.example.codefactory.shipment.domain.model.Shipment;
import com.example.codefactory.shipment.domain.port.PersonRepository;
import com.example.codefactory.shipment.domain.port.ShipmentRepository;
import com.example.codefactory.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RegisterSenderUseCase {
	private final ShipmentRepository shipmentRepository;
	private final PersonRepository personRepository;

	public RegisterSenderUseCase(ShipmentRepository shipmentRepository, PersonRepository personRepository) {
		this.shipmentRepository = shipmentRepository;
		this.personRepository = personRepository;
	}

	@Transactional
	public RegisterSenderResponse register(Long shipmentId, RegisterSenderRequest request) {
		Shipment shipment = shipmentRepository.findById(shipmentId)
				.orElseThrow(() -> new NotFoundException("Shipment not found: " + shipmentId));

		String personId = UUID.randomUUID().toString();
		Person sender = new Person(
				personId,
				request.nombre(),
				request.telefono(),
				request.correoElectronico(),
				request.direccion(),
				request.referencias()
		);
		personRepository.save(sender);

		Shipment updated = shipment.withSenderId(personId);
		shipmentRepository.save(updated);

		return new RegisterSenderResponse(shipmentId, personId);
	}
}
