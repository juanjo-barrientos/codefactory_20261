package com.example.codefactory.shipment.presentation;

import com.example.codefactory.shipment.application.command.CreateShipmentUseCase;
import com.example.codefactory.shipment.application.command.GetShipmentByTrackingCodeUseCase;
import com.example.codefactory.shipment.application.command.RegisterPackageUseCase;
import com.example.codefactory.shipment.application.command.RegisterSenderUseCase;
import com.example.codefactory.shipment.application.dto.CreateShipmentRequest;
import com.example.codefactory.shipment.application.dto.RegisterPackageRequest;
import com.example.codefactory.shipment.application.dto.RegisterPackageResponse;
import com.example.codefactory.shipment.application.dto.RegisterSenderRequest;
import com.example.codefactory.shipment.application.dto.RegisterSenderResponse;
import com.example.codefactory.shipment.application.dto.ShipmentResponse;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentController {
	private final CreateShipmentUseCase createShipmentUseCase;
	private final GetShipmentByTrackingCodeUseCase getShipmentByTrackingCodeUseCase;
	private final RegisterPackageUseCase registerPackageUseCase;
	private final RegisterSenderUseCase registerSenderUseCase;

	public ShipmentController(
			CreateShipmentUseCase createShipmentUseCase,
			GetShipmentByTrackingCodeUseCase getShipmentByTrackingCodeUseCase,
			RegisterPackageUseCase registerPackageUseCase,
			RegisterSenderUseCase registerSenderUseCase
	) {
		this.createShipmentUseCase = createShipmentUseCase;
		this.getShipmentByTrackingCodeUseCase = getShipmentByTrackingCodeUseCase;
		this.registerPackageUseCase = registerPackageUseCase;
		this.registerSenderUseCase = registerSenderUseCase;
	}

	@PostMapping
	public ResponseEntity<EntityModel<ShipmentResponse>> createShipment(@Valid @RequestBody CreateShipmentRequest request) {
		ShipmentResponse response = createShipmentUseCase.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(toShipmentModel(response));
	}

	@GetMapping("/tracking/{trackingCode}")
	public ResponseEntity<EntityModel<ShipmentResponse>> getByTrackingCode(@PathVariable String trackingCode) {
		ShipmentResponse response = getShipmentByTrackingCodeUseCase.getByTrackingCode(trackingCode);
		return ResponseEntity.ok(toShipmentModel(response));
	}

	@PostMapping("/{id}/package")
	public ResponseEntity<EntityModel<RegisterPackageResponse>> registerPackage(
			@PathVariable("id") Long shipmentId,
			@Valid @RequestBody RegisterPackageRequest request
	) {
		RegisterPackageResponse response = registerPackageUseCase.register(shipmentId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(toPackageModel(response));
	}

	@PostMapping("/{id}/sender")
	public ResponseEntity<EntityModel<RegisterSenderResponse>> registerSender(
			@PathVariable("id") Long shipmentId,
			@Valid @RequestBody RegisterSenderRequest request
	) {
		RegisterSenderResponse response = registerSenderUseCase.register(shipmentId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(toSenderModel(response));
	}

	private EntityModel<ShipmentResponse> toShipmentModel(ShipmentResponse response) {
		return EntityModel.of(
				response,
				linkTo(methodOn(ShipmentController.class).getByTrackingCode(response.codigoRastreo())).withSelfRel(),
				linkTo(methodOn(TrackingController.class).getHistory(response.id())).withRel("history")
		);
	}

	private EntityModel<RegisterPackageResponse> toPackageModel(RegisterPackageResponse response) {
		return EntityModel.of(
				response,
				linkTo(methodOn(TrackingController.class).getHistory(response.shipmentId())).withRel("history")
		);
	}

	private EntityModel<RegisterSenderResponse> toSenderModel(RegisterSenderResponse response) {
		return EntityModel.of(
				response,
				linkTo(methodOn(TrackingController.class).getHistory(response.shipmentId())).withRel("history")
		);
	}
}
