package com.rentacar.rentacar.controller;
import com.rentacar.rentacar.model.Car;
import com.rentacar.rentacar.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/cars")
@RestController

public class CarController {

    @Autowired
    private CarService carService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Car> createCar(@RequestPart("file") MultipartFile file,
                                         @ModelAttribute("car") Car car){
        return new ResponseEntity<>(carService.createCar(file, car), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Car> updateCar(@RequestPart(value = "file", required = false) MultipartFile file,
                                         @ModelAttribute("car") Car car){
        return new ResponseEntity<>(carService.createCar(file, car), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<Car>> getCarsList(@PathVariable(value = "brandId")Long brandId){
        return new ResponseEntity<>(carService.getCarListByBrandId(brandId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<Car> getCar(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(carService.getCar(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/active/{id}")
    public ResponseEntity<Boolean> activeCarStatus(@PathVariable("id") Long id){
        carService.activeOrDeactiveCar(id, true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/deActive/{id}")
    public ResponseEntity<Boolean> deActiveCarStatus(@PathVariable("id") Long id){
        carService.activeOrDeactiveCar(id, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") Long id){
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Car>> getAllCarsList(){
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

}
/*@RequestPart("cars") Cars cars) frontend'e geçince model attribute silinip bu kullanılacak*/