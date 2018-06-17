package com.qazlearn.a1.qazlearn;

/**
 * Created by 1 on 30.03.2017.
 */

public class Rating {
    private int id;
    private String name;
    private String price;
    private String description;
    private int got;
    //Constructor

    public Rating(int id, String name, String price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.got = Integer.parseInt(description);
    }

    //Setter, getter
    public int getGot(){return got;}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setGot(int got){this.got = got;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
