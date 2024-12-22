package com.rentacar.rentacar.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter



public class OrderRequest {
    private Long userId;
    private Long carId;
    private LocalDate rentDay;
    private LocalDate deliveryDay;
}
