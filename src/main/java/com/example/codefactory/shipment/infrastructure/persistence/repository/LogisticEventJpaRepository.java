package com.example.codefactory.shipment.infrastructure.persistence.repository;

import com.example.codefactory.shipment.infrastructure.persistence.entity.LogisticEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogisticEventJpaRepository extends JpaRepository<LogisticEventEntity, Long> {
	List<LogisticEventEntity> findByShipment_IdOrderByFechaEventoAsc(Long shipmentId);
}
