package com.sales.models;

public class SynBreakStockClass {

    public int id;
    public String code;
    public String codeRoute;
    public String idUserApp;
    public String codeItem;
    public String nameItem;
    public String dateHour;
    public String price;
    public String numberCustomerPending;
    public String load;


    public SynBreakStockClass(){}

    public SynBreakStockClass(int id, String code, String codeRoute, String idUserApp, String codeItem, String nameItem, String dateHour, String price, String numberCustomerPending, String load) {
        this.id = id;
        this.code = code;
        this.codeRoute = codeRoute;
        this.idUserApp = idUserApp;
        this.codeItem = codeItem;
        this.nameItem = nameItem;
        this.dateHour = dateHour;
        this.price = price;
        this.numberCustomerPending = numberCustomerPending;
        this.load = load;
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

    public String getCodeRoute() {
        return codeRoute;
    }

    public void setCodeRoute(String codeRoute) {
        this.codeRoute = codeRoute;
    }

    public String getIdUserApp() {
        return idUserApp;
    }

    public void setIdUserApp(String idUserApp) {
        this.idUserApp = idUserApp;
    }

    public String getCodeItem() {
        return codeItem;
    }

    public void setCodeItem(String codeItem) {
        this.codeItem = codeItem;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getDateHour() {
        return dateHour;
    }

    public void setDateHour(String dateHour) {
        this.dateHour = dateHour;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumberCustomerPending() {
        return numberCustomerPending;
    }

    public void setNumberCustomerPending(String numberCustomerPending) {
        this.numberCustomerPending = numberCustomerPending;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }
}
