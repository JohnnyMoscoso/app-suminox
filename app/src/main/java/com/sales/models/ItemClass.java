package com.sales.models;

public class ItemClass {

    public int id;
    public String categoryCode;
    public String code;
    public String description;
    public String tax;
    public String measureUnitCode;
    public String litersLbs;
    public String order;
    public String categoryName;
    public String measureUnitName;
    public String state;
    public String stock;

    public String quantity;
    public String total;
    public String position;

    public ItemClass(){}

    public ItemClass(int id, String categoryCode, String code, String description, String tax, String measureUnitCode, String litersLbs, String order, String categoryName,
                     String measureUnitName, String state, String stock, String quantity, String total, String position) {
        this.id = id;
        this.categoryCode = categoryCode;
        this.code = code;
        this.description = description;
        this.tax = tax;
        this.measureUnitCode = measureUnitCode;
        this.litersLbs = litersLbs;
        this.order = order;
        this.categoryName = categoryName;
        this.measureUnitName = measureUnitName;
        this.state = state;
        this.stock = stock;
        this.quantity = quantity;
        this.total = total;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
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

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getMeasureUnitCode() {
        return measureUnitCode;
    }

    public void setMeasureUnitCode(String measureUnitCode) {
        this.measureUnitCode = measureUnitCode;
    }

    public String getLitersLbs() {
        return litersLbs;
    }

    public void setLitersLbs(String litersLbs) {
        this.litersLbs = litersLbs;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMeasureUnitName() {
        return measureUnitName;
    }

    public void setMeasureUnitName(String measureUnitName) {
        this.measureUnitName = measureUnitName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
