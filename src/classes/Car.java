package classes;

/***
 *   Author: Roberto Sanchez A. 
 *   Date:   12/19/17
 ***/
public class Car {
    private int id;
    private String type;
    private String model;

    public Car(String model, String type) {
        this.type = type;
        this.model = model;
    }

    public Car(int id, String model, String type) {
        this.id = id;
        this.type = type;
        this.model = model;
    }

    public long getId() {
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
