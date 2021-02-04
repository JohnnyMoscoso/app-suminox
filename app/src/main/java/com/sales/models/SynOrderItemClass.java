package com.sales.models;

public class SynOrderItemClass {

    public int id;
    public String numberDocument;
    public String dateHour;
    public String category;
    public String codeItem;
    public String descriptionItem;
    public String quantity;
    public String price;
    public String unitMeasure;
    public String igv;
    public String totalPay;
    public String state;
    public String stateCharge;
    public String load;

    public SynOrderItemClass(){}

    public SynOrderItemClass(int id, String numberDocument, String dateHour, String category, String codeItem,
                             String descriptionItem, String quantity, String price, String unitMeasure, String igv,
                             String totalPay, String state, String stateCharge, String load) {
        this.id = id;
        this.numberDocument = numberDocument;
        this.dateHour = dateHour;
        this.category = category;
        this.codeItem = codeItem;
        this.descriptionItem = descriptionItem;
        this.quantity = quantity;
        this.price = price;
        this.unitMeasure = unitMeasure;
        this.igv = igv;
        this.totalPay = totalPay;
        this.state = state;
        this.stateCharge = stateCharge;
        this.load = load;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumberDocument() {
        return numberDocument;
    }

    public void setNumberDocument(String numberDocument) {
        this.numberDocument = numberDocument;
    }

    public String getDateHour() {
        return dateHour;
    }

    public void setDateHour(String dateHour) {
        this.dateHour = dateHour;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCodeItem() {
        return codeItem;
    }

    public void setCodeItem(String codeItem) {
        this.codeItem = codeItem;
    }

    public String getDescriptionItem() {
        return descriptionItem;
    }

    public void setDescriptionItem(String descriptionItem) {
        this.descriptionItem = descriptionItem;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public String getIgv() {
        return igv;
    }

    public void setIgv(String igv) {
        this.igv = igv;
    }

    public String getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(String totalPay) {
        this.totalPay = totalPay;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCharge() {
        return stateCharge;
    }

    public void setStateCharge(String stateCharge) {
        this.stateCharge = stateCharge;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }
}
