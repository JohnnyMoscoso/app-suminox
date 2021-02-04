package com.sales.models;

public class ConfigurationClass {

    public int id;
    public String currency;
    public String unitMeasure;
    public String paperSize;

    public ConfigurationClass(){}

    public ConfigurationClass(int id, String currency, String unitMeasure, String paperSize) {
        this.id = id;
        this.currency = currency;
        this.unitMeasure = unitMeasure;
        this.paperSize = paperSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public String getPaperSize() {
        return paperSize;
    }

    public void setPaperSize(String paperSize) {
        this.paperSize = paperSize;
    }
}
