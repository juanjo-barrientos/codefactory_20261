package com.example.codefactory.shipment.presentation;

import com.example.codefactory.shipment.application.command.GetShipmentHistoryUseCase;
import com.example.codefactory.shipment.application.command.RegisterLogisticEventUseCase;
import com.example.codefactory.shipment.application.dto.LogisticEventResponse;
import com.example.codefactory.shipment.application.dto.RegisterLogisticEventRequest;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/tracking")
public class TrackingController {
	private final RegisterLogisticEventUseCase registerLogisticEventUseCase;
	private final GetShipmentHistoryUseCase getShipmentHistoryUseCase;

	public TrackingController(
			RegisterLogisticEventUseCase registerLogisticEventUseCase,
			GetShipmentHistoryUseCase getShipmentHistoryUseCase
	) {
		this.registerLogisticEventUseCase = registerLogisticEventUseCase;
		this.getShipmentHistoryUseCase = getShipmentHistoryUseCase;
	}

	@PostMapping("/shipments/{shipmentId}/events")
	public ResponseEntity<EntityModel<LogisticEventResponse>> registerEvent(
			@PathVariable Long shipmentId,
			@Valid @RequestBody RegisterLogisticEventRequest request
	) {
		LogisticEventResponse response = registerLogisticEventUseCase.register(shipmentId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(toEventModel(response));
	}

	@GetMapping("/shipments/{shipmentId}/history")
	public ResponseEntity<CollectionModel<EntityModel<LogisticEventResponse>>> getHistory(@PathVariable Long shipmentId) {
		List<LogisticEventResponse> response = getShipmentHistoryUseCase.getHistory(shipmentId);
		List<EntityModel<LogisticEventResponse>> models = response.stream().map(this::toEventModel).toList();
		return ResponseEntity.ok(CollectionModel.of(
				models,
				linkTo(methodOn(TrackingController.class).getHistory(shipmentId)).withSelfRel()
		));
	}

	private EntityModel<LogisticEventResponse> toEventModel(LogisticEventResponse response) {
		return EntityModel.of(
				response,
				linkTo(methodOn(TrackingController.class).getHistory(response.shipmentId())).withRel("history")
		);
	}
}
