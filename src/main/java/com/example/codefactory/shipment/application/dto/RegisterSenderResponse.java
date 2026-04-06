package com.example.codefactory.shipment.application.dto;

public record RegisterSenderResponse(
		Long shipmentId,
		String senderId
) {
}
