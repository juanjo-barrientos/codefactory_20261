package com.example.codefactory.shipment.infrastructure.persistence.repository;

import com.example.codefactory.shipment.infrastructure.persistence.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageJpaRepository extends JpaRepository<PackageEntity, Long> {
	Optional<PackageEntity> findByShipment_Id(Long shipmentId);
}
