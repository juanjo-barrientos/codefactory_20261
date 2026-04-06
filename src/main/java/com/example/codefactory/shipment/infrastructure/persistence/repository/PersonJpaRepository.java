package com.example.codefactory.shipment.infrastructure.persistence.repository;

import com.example.codefactory.shipment.infrastructure.persistence.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonJpaRepository extends JpaRepository<PersonEntity, String> {
}
