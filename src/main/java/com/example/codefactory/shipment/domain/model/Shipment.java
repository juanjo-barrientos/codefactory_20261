package com.example.codefactory.shipment.domain.model;

import java.util.Objects;

public class Shipment {
	private final Long id;
	private final String senderId;
	private final String recipientId;

	public Shipment(Long id, String senderId, String recipientId) {
		this.id = Objects.requireNonNull(id);
		this.senderId = senderId;
		this.recipientId = recipientId;
	}

	public Long getId() {
		return id;
	}

	public String getSenderId() {
		return senderId;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public Shipment withSenderId(String senderId) {
		return new Shipment(this.id, senderId, this.recipientId);
	}
}
