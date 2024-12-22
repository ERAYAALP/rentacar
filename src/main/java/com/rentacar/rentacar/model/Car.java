package com.rentacar.rentacar.model;
import com.rentacar.rentacar.enums.FuelType;
import com.rentacar.rentacar.enums.TransmissionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cars")

public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private int year;

    private String colour;

    private Double dailyPrice;

    private Double km;

    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column(name = "units_in_stock")
    private int unitInStock;

    @Column(name = "brand_id")
    private Long brandId;

    private String image;

    private Boolean active;
}
