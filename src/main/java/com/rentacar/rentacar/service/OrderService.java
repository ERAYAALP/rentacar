package com.rentacar.rentacar.service;


import com.rentacar.rentacar.exception.CarNotFoundException;
import com.rentacar.rentacar.exception.UserNotFoundException;
import com.rentacar.rentacar.model.Car;
import com.rentacar.rentacar.model.Order;
import com.rentacar.rentacar.model.User;
import com.rentacar.rentacar.repository.CarRepository;
import com.rentacar.rentacar.repository.OrderRepository;
import com.rentacar.rentacar.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j

public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private CarRepository carRepository;


    @Autowired
    private UserRepository userRepository;


    public Order doOrder(Long userId, Long carId, LocalDate rentDate, LocalDate deliveryDate){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found this id = " + userId));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car not found this id = " + carId));

        if (car.getUnitInStock() <= 0){
            throw new RuntimeException("Car stock is insufficient, car id = " + carId);
        }

        Long rentalDays = ChronoUnit.DAYS.between(rentDate, deliveryDate);
        double totalPrice = rentalDays * car.getDailyPrice();

        Order order = new Order();
        order.setUserId(userId);
        order.setCarId(carId);
        order.setRentDay(rentDate);
        order.setDeliveryDay(deliveryDate);
        order.setDailyPrice(car.getDailyPrice());
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        car.setUnitInStock(car.getUnitInStock() - 1);

        if (car.getUnitInStock() == 0){
            car.setActive(false);
        }

        carRepository.save(car);

        return savedOrder;
    }
}