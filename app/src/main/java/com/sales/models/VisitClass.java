package com.sales.models;

public class VisitClass {

    public int id;
    public String description;
    public String officeCode;
    public String officeName;
    public String state;

    public VisitClass(){

    }

    public VisitClass(int id, String description, String officeCode, String officeName, String state) {
        this.id = id;
        this.description = description;
        this.officeCode = officeCode;
        this.officeName = officeName;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
