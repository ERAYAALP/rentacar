package com.rentacar.rentacar.controller;

import com.rentacar.rentacar.dto.OrderRequest;
import com.rentacar.rentacar.model.Order;
import com.rentacar.rentacar.repository.OrderRepository;
import com.rentacar.rentacar.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/order")
@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<Order> doOrder(@RequestBody OrderRequest orderRequest){

        Order savedOrder = orderService.doOrder(orderRequest.getUserId(),
                orderRequest.getCarId(),
                orderRequest.getRentDay(),
                orderRequest.getDeliveryDay());
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);

    }
}