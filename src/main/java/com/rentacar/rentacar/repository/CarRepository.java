package com.rentacar.rentacar.repository;

import com.rentacar.rentacar.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c WHERE c.brandId = :brandId")
    List<Car> findCarListByBrandId(Long brandId);

    @Transactional
    @Modifying
    @Query("UPDATE Car c SET c.active = :active WHERE c.id = :id")
    void updateCarActive(@Param("active") Boolean isActive, @Param("id") Long id);

    @Query("SELECT c FROM Car c")
    List<Car> getAllCarsList();

    @Query("SELECT c FROM Car c WHERE c.id = :id")
    Optional<Car> getCarById(@Param("id") Long id);

    @Query("SELECT count(c) FROM Car c WHERE c.brandId = :brandId")
    Long getCarCountByProductId(@Param("brandId") Long brandId);
}
