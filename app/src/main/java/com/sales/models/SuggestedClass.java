package com.sales.models;

public class SuggestedClass {

    public int id;
    public String type; //VD (Venta Directa), PC (Pedido Cliente), PV (Pedido Vendedor)
    public String codeCustomer;
    public String codeItem;
    public String descriptionItem;
    public String categoryItem;
    public String quantityItem;
    public String unitMeasureItem;


    public SuggestedClass(){}

    public SuggestedClass(int id, String type, String codeCustomer, String codeItem, String descriptionItem, String categoryItem, String quantityItem, String unitMeasureItem) {
        this.id = id;
        this.type = type;
        this.codeCustomer = codeCustomer;
        this.codeItem = codeItem;
        this.descriptionItem = descriptionItem;
        this.categoryItem = categoryItem;
        this.quantityItem = quantityItem;
        this.unitMeasureItem = unitMeasureItem;
    }

    public int getId() {
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

    public String getCodeCustomer() {
        return codeCustomer;
    }

    public void setCodeCustomer(String codeCustomer) {
        this.codeCustomer = codeCustomer;
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

    public String getCategoryItem() {
        return categoryItem;
    }

    public void setCategoryItem(String categoryItem) {
        this.categoryItem = categoryItem;
    }

    public String getQuantityItem() {
        return quantityItem;
    }

    public void setQuantityItem(String quantityItem) {
        this.quantityItem = quantityItem;
    }

    public String getUnitMeasureItem() {
        return unitMeasureItem;
    }

    public void setUnitMeasureItem(String unitMeasureItem) {
        this.unitMeasureItem = unitMeasureItem;
    }
}
