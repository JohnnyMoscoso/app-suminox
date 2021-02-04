package com.sales.models;

public class CategoryClass {

    public int id;
    public String code;
    public String description;
    public String order;
    public String state;

    public CategoryClass(){

    }

    public CategoryClass(int id, String code, String description, String order, String state) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.order = order;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
