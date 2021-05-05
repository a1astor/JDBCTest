package com.noirix.repository;

import com.noirix.domain.Car;

import java.util.List;

public interface CarsRepository extends CrudOperations<Long, Car> {

    Car getCarByDealerId();

    List<Car> getAllCars();
}
