package it.softlab.app_oco.model;

/**
 * Created by claudio on 5/3/17.
 */

public class Product {
    private final String name;
    private final String location;
    private final String price;

    public Product(String name, String location, String price) {
        this.name = name;
        this.location = location;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }
}
