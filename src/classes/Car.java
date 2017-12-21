package classes;

import settings.Business_conf;

/*
 *   Author: Roberto Sanchez A. 
 *   Date:   12/19/17
 */

/**
 * The car class contains attributes according to the statement of the problem.\
 * Price per day setting is in /settings/Business_conf.java
 */
public class Car {
    private int id;
    private String type;
    private String model;
    private float pricePerDay;

    public float getPricePerDay() {
        return pricePerDay;
    }

    public Car(String model, String type) {
        this.type = type;
        this.model = model;
        this.pricePerDay = Business_conf.getCarPrice().get(type);
    }

    public Car(int id, String model, String type) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.pricePerDay = Business_conf.getCarPrice().get(type);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
