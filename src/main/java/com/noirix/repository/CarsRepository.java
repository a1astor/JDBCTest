package com.noirix.repository;

import com.noirix.domain.Car;

import java.util.List;

public interface CarsRepository extends CrudOperations<Long, Car> {

    List<Car> getAllDealerCars(Long dealerID);

    List<Car> getMostExpensiveCars();
}
