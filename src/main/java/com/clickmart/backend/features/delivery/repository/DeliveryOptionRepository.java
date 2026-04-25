package com.clickmart.backend.features.delivery.repository;

import com.clickmart.backend.entity.DeliveryOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryOptionRepository extends JpaRepository<DeliveryOption, Long> {
    List<DeliveryOption> findByActiveTrueOrderByPriceAsc();
    Optional<DeliveryOption> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
