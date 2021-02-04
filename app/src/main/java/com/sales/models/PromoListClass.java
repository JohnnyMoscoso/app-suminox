package com.sales.models;

public class PromoListClass {

    public int id;
    public String code;
    public String codeItemOne;
    public String quantityOne;
    public String categoryOne;
    public String unitMeasureOne;
    public String codeItemTwo;
    public String quantityTwo;
    public String categoryTwo;
    public String unitMeasureTwo;
    public String nameItemOne;
    public String nameItemTwo;
    public String nameUnitOne;
    public String nameUnitTwo;
    public String state;

    public PromoListClass(){}

    public PromoListClass(int id, String code, String codeItemOne, String quantityOne, String unitMeasureOne,
                          String codeItemTwo, String quantityTwo, String unitMeasureTwo, String nameItemOne, String nameItemTwo,
                          String nameUnitOne, String nameUnitTwo, String state, String categoryOne, String categoryTwo) {
        this.id = id;
        this.code = code;
        this.codeItemOne = codeItemOne;
        this.quantityOne = quantityOne;
        this.unitMeasureOne = unitMeasureOne;
        this.codeItemTwo = codeItemTwo;
        this.quantityTwo = quantityTwo;
        this.unitMeasureTwo = unitMeasureTwo;
        this.nameItemOne = nameItemOne;
        this.nameItemTwo = nameItemTwo;
        this.nameUnitOne = nameUnitOne;
        this.nameUnitTwo = nameUnitTwo;
        this.categoryOne = categoryOne;
        this.categoryTwo = categoryTwo;
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

    public String getCodeItemOne() {
        return codeItemOne;
    }

    public void setCodeItemOne(String codeItemOne) {
        this.codeItemOne = codeItemOne;
    }

    public String getQuantityOne() {
        return quantityOne;
    }

    public void setQuantityOne(String quantityOne) {
        this.quantityOne = quantityOne;
    }

    public String getUnitMeasureOne() {
        return unitMeasureOne;
    }

    public void setUnitMeasureOne(String unitMeasureOne) {
        this.unitMeasureOne = unitMeasureOne;
    }

    public String getCodeItemTwo() {
        return codeItemTwo;
    }

    public void setCodeItemTwo(String codeItemTwo) {
        this.codeItemTwo = codeItemTwo;
    }

    public String getQuantityTwo() {
        return quantityTwo;
    }

    public void setQuantityTwo(String quantityTwo) {
        this.quantityTwo = quantityTwo;
    }

    public String getUnitMeasureTwo() {
        return unitMeasureTwo;
    }

    public void setUnitMeasureTwo(String unitMeasureTwo) {
        this.unitMeasureTwo = unitMeasureTwo;
    }

    public String getNameItemOne() {
        return nameItemOne;
    }

    public void setNameItemOne(String nameItemOne) {
        this.nameItemOne = nameItemOne;
    }

    public String getNameItemTwo() {
        return nameItemTwo;
    }

    public void setNameItemTwo(String nameItemTwo) {
        this.nameItemTwo = nameItemTwo;
    }

    public String getNameUnitOne() {
        return nameUnitOne;
    }

    public void setNameUnitOne(String nameUnitOne) {
        this.nameUnitOne = nameUnitOne;
    }

    public String getNameUnitTwo() {
        return nameUnitTwo;
    }

    public void setNameUnitTwo(String nameUnitTwo) {
        this.nameUnitTwo = nameUnitTwo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCategoryOne() {
        return categoryOne;
    }

    public void setCategoryOne(String categoryOne) {
        this.categoryOne = categoryOne;
    }

    public String getCategoryTwo() {
        return categoryTwo;
    }

    public void setCategoryTwo(String categoryTwo) {
        this.categoryTwo = categoryTwo;
    }
}
