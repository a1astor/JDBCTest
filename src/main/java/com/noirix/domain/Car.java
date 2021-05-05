package com.noirix.domain;

import java.util.Date;
import java.util.Objects;

public class Car {
    private Long id;
    private Long owner;
    private String name;
    private String model;
    private Date production_date;
    private double price;
    private Long dealer_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getProduction_date() {
        return production_date;
    }

    public void setProduction_date(Date production_date) {
        this.production_date = production_date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(Long dealer_id) {
        this.dealer_id = dealer_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) && Objects.equals(name, car.name) && Objects.equals(owner, car.owner) &&
                Objects.equals(model, car.model) && Objects.equals(production_date, car.production_date) && Objects.equals(dealer_id, car.dealer_id) && Objects.equals(price, car.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, name, model, production_date, price, dealer_id);
    }
}

