package classes;


import settings.Business_conf;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;



/*
 *   Author: Roberto Sanchez A. 
 *   Date:   12/19/17
 */

/**
 * The car class contains attributes according to the statement of the problem.\
 * Price per day setting is in /settings/Business_conf.java
 */


@XmlRootElement
public class Car implements Serializable {
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

    @XmlElement
    public int getId() {
        return id;
    }
    @XmlElement
    public void setId(int id) {
        this.id = id;
    }
    @XmlElement
    public String getType() {
        return type;
    }
    @XmlElement
    public void setType(String type) {
        this.type = type;
    }
    @XmlElement
    public String getModel() {
        return model;
    }
    @XmlElement
    public void setModel(String model) {
        this.model = model;
    }
}
