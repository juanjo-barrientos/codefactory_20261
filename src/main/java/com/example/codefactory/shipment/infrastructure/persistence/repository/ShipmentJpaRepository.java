package com.example.codefactory.shipment.infrastructure.persistence.repository;

import com.example.codefactory.shipment.infrastructure.persistence.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentJpaRepository extends JpaRepository<ShipmentEntity, Long> {
	Optional<ShipmentEntity> findByCodigoRastreo(String codigoRastreo);

	boolean existsByCodigoRastreo(String codigoRastreo);
}
