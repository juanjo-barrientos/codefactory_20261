package com.example.codefactory.shipment.presentation;

import com.example.codefactory.shipment.application.command.RegisterPackageUseCase;
import com.example.codefactory.shipment.application.command.RegisterSenderUseCase;
import com.example.codefactory.shipment.application.dto.RegisterPackageRequest;
import com.example.codefactory.shipment.application.dto.RegisterPackageResponse;
import com.example.codefactory.shipment.application.dto.RegisterSenderRequest;
import com.example.codefactory.shipment.application.dto.RegisterSenderResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {
	private final RegisterPackageUseCase registerPackageUseCase;
	private final RegisterSenderUseCase registerSenderUseCase;

	public ShipmentController(RegisterPackageUseCase registerPackageUseCase, RegisterSenderUseCase registerSenderUseCase) {
		this.registerPackageUseCase = registerPackageUseCase;
		this.registerSenderUseCase = registerSenderUseCase;
	}

	@PostMapping("/{id}/package")
	public ResponseEntity<RegisterPackageResponse> registerPackage(
			@PathVariable("id") Long shipmentId,
			@Valid @RequestBody RegisterPackageRequest request
	) {
		RegisterPackageResponse response = registerPackageUseCase.register(shipmentId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/{id}/sender")
	public ResponseEntity<RegisterSenderResponse> registerSender(
			@PathVariable("id") Long shipmentId,
			@Valid @RequestBody RegisterSenderRequest request
	) {
		RegisterSenderResponse response = registerSenderUseCase.register(shipmentId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
