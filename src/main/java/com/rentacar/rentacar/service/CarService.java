package com.rentacar.rentacar.service;
import com.rentacar.rentacar.exception.CarNotFoundException;
import com.rentacar.rentacar.model.Car;
import com.rentacar.rentacar.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class CarService {

    private static final String UPLOAD_DIR = "upload";

    @Autowired
    private CarRepository carRepository;

    public Car createCar(MultipartFile file, Car car){
        if (Objects.nonNull(file)){
            String imagePath = saveFile(file, car.getModel());
            car.setImage(imagePath);
        }else {
            Car existCar = carRepository.findById(car.getId()).orElseThrow(() -> new CarNotFoundException("Car not found = " + car.getModel()));
            car.setImage(existCar.getImage());
        }
        return carRepository.save(car);
    }

    private String saveFile(MultipartFile file,String carModel){
        carModel = carModel.replaceAll("\\s", "");
        String fileName = carModel + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path uploadPath = Path.of(UPLOAD_DIR);
        Path filePath;
        try {
            Files.createDirectories(uploadPath);
            filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return filePath.toString();
    }

    public List<Car> getCarListByBrandId(Long brandId){
        return carRepository.findCarListByBrandId(brandId);
    }

    public Car getCar(Long id){
        return carRepository.getCarById(id).orElseThrow(() -> new CarNotFoundException("Car not found = " + id));
    }

    public void activeOrDeactiveCar(Long id, Boolean isActive){
        carRepository.updateCarActive(isActive, id);
    }

    public void deleteCar(Long id){
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("car not found = " + id));
        try {
            Files.delete(Paths.get(car.getImage()));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        carRepository.deleteById(id);
    }

    public List<Car> getAllCars(){
        return carRepository.getAllCarsList();
    }
}