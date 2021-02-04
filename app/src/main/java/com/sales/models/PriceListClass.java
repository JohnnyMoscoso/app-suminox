package com.sales.models;

public class PriceListClass {

    public int id;
    public String code;
    public String itemCode;
    public String price;
    public String itemDescription;
    public String priceListDescription;
    public String state;

    public PriceListClass(){

    }

    public PriceListClass(int id, String code, String itemCode, String price, String itemDescription, String priceListDescription, String state) {
        this.id = id;
        this.code = code;
        this.itemCode = itemCode;
        this.price = price;
        this.itemDescription = itemDescription;
        this.priceListDescription = priceListDescription;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getPriceListDescription() {
        return priceListDescription;
    }

    public void setPriceListDescription(String priceListDescription) {
        this.priceListDescription = priceListDescription;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
