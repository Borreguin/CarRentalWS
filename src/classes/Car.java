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
//This element (XmlRootElement) is important
@XmlRootElement(name = "car")
public class Car implements Serializable {

    // the variable: @serialVersionUID is so important for XML format.
    private static final long serialVersionUID = 1L;
    private int id;
    private String type;
    private String model;
    private float pricePerDay;
    private Business_conf business_conf = new Business_conf();
    public Car(){}

    public Car(String model, String type){
        this.type = type;
        this.model = model;
        this.pricePerDay = business_conf.getCarPrice(type);
    }

    public Car(int id, String model, String type){
        this.id = id;
        this.type = type;
        this.model = model;
        this.pricePerDay =  business_conf.getCarPrice(type);
    }

    public int getId() {
        return id;
    }
    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    @XmlElement
    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }
    @XmlElement
    public void setModel(String model) {
        this.model = model;
    }

    public float getPricePerDay() {
        return pricePerDay;
    }
    @XmlElement
    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}
