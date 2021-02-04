package com.sales.models;

public class TypeDocumentClass {

    public int id;
    public String code;
    public String description;
    public String state;

    public TypeDocumentClass(){}

    public TypeDocumentClass(int id, String code, String description, String state) {
        this.id = id;
        this.code = code;
        this.description = description;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
