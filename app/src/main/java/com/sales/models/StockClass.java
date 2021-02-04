package com.sales.models;

public class StockClass {

    public int id;
    public String codeRoute;
    public String codeItem; //Con esto saber para la sync
    public String quantity;

    public StockClass(){}

    public StockClass(int id, String codeRoute, String codeItem, String quantity) {
        this.id = id;
        this.codeRoute = codeRoute;
        this.codeItem = codeItem;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeRoute() {
        return codeRoute;
    }

    public void setCodeRoute(String codeRoute) {
        this.codeRoute = codeRoute;
    }

    public String getCodeItem() {
        return codeItem;
    }

    public void setCodeItem(String codeItem) {
        this.codeItem = codeItem;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
