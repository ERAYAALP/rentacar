package com.rentacar.rentacar.service;

import com.rentacar.rentacar.exception.BrandDeleteException;
import com.rentacar.rentacar.exception.BrandDuplicateException;
import com.rentacar.rentacar.exception.BrandNotFoundException;
import com.rentacar.rentacar.model.Brand;
import com.rentacar.rentacar.repository.BrandRepository;
import com.rentacar.rentacar.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service

public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CarRepository carRepository;

    public Brand createBrand(Brand brand){
        if (brand.getName() == null || brand.getName().trim().isEmpty()){
            throw new IllegalArgumentException("Brand name can not be empty.");
        }
        Optional<Brand> optionalBrand = brandRepository.findBrandByName(brand.getName());
        if (optionalBrand.isPresent()){
            throw new BrandDuplicateException("Brand is already exist." + brand.getName());
        }
        return brandRepository.save(brand);
    }

    public void deleteBrand(Long id){
        Long getCarCountByProductId = carRepository.getCarCountByProductId(id);
        if (getCarCountByProductId > 0){
            throw new BrandDeleteException("You can not delete this brand, because brand has = " + getCarCountByProductId + " cars.");
        }
        brandRepository.deleteById(id);
    }

    public Brand getBrand(Long id){
        return brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException("Brand not found, id = " + id));
    }

    public List<Brand> getAllBrandList(){
        List<Brand> brandList = brandRepository.findAll();
        brandList.sort(Comparator.comparingLong(Brand::getId));
        return brandRepository.findAll();
    }

    public Optional<Brand> updateBrand(Long id, Brand brand){
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isPresent()){
            Brand existingBrand = optionalBrand.get();
            existingBrand.setName(brand.getName());
            Brand updatedBrand = brandRepository.save(existingBrand);
            return Optional.of(updatedBrand);
        }else {
            return Optional.empty();
        }
    }
}