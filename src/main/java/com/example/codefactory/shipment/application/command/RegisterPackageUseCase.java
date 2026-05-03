package com.example.codefactory.shipment.application.command;

import com.example.codefactory.shipment.application.dto.RegisterPackageRequest;
import com.example.codefactory.shipment.application.dto.RegisterPackageResponse;
import com.example.codefactory.shipment.domain.model.Package;
import com.example.codefactory.shipment.domain.port.PackageRepository;
import com.example.codefactory.shipment.domain.port.ShipmentRepository;
import com.example.codefactory.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterPackageUseCase {
	private final ShipmentRepository shipmentRepository;
	private final PackageRepository packageRepository;

	public RegisterPackageUseCase(ShipmentRepository shipmentRepository, PackageRepository packageRepository) {
		this.shipmentRepository = shipmentRepository;
		this.packageRepository = packageRepository;
	}

	@Transactional
	public RegisterPackageResponse register(Long shipmentId, RegisterPackageRequest request) {
		if (shipmentRepository.findById(shipmentId).isEmpty()) {
			throw new NotFoundException("Shipment not found: " + shipmentId);
		}

		Package toSave = new Package(
				null,
				shipmentId,
				request.peso(),
				request.largo(),
				request.ancho(),
				request.alto(),
				request.descripcion()
		);

		Package saved = packageRepository.save(toSave);
		return new RegisterPackageResponse(
				saved.getId(),
				saved.getShipmentId(),
				saved.getPeso(),
				saved.getLargo(),
				saved.getAncho(),
				saved.getAlto(),
				saved.getDescripcion()
		);
	}
}
