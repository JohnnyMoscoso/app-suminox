package com.sales.models;

public class DiscountListClass {

    public int id;
    public String code;
    public String codeCustomer;
    public String codeCategory;
    public String codeItem;
    public String discountPercentage;
    public String dateInitial;
    public String dateFinish;
    public String state;

    public DiscountListClass(){}

    public DiscountListClass(int id, String code, String codeCustomer, String codeCategory, String codeItem, String discountPercentage, String dateInitial, String dateFinish, String state) {
        this.id = id;
        this.code = code;
        this.codeCustomer = codeCustomer;
        this.codeCategory = codeCategory;
        this.codeItem = codeItem;
        this.discountPercentage = discountPercentage;
        this.dateInitial = dateInitial;
        this.dateFinish = dateFinish;
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

    public String getCodeCustomer() {
        return codeCustomer;
    }

    public void setCodeCustomer(String codeCustomer) {
        this.codeCustomer = codeCustomer;
    }

    public String getCodeCategory() {
        return codeCategory;
    }

    public void setCodeCategory(String codeCategory) {
        this.codeCategory = codeCategory;
    }

    public String getCodeItem() {
        return codeItem;
    }

    public void setCodeItem(String codeItem) {
        this.codeItem = codeItem;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getDateInitial() {
        return dateInitial;
    }

    public void setDateInitial(String dateInitial) {
        this.dateInitial = dateInitial;
    }

    public String getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(String dateFinish) {
        this.dateFinish = dateFinish;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
