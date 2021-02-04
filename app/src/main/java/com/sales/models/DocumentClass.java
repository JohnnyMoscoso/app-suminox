package com.sales.models;


public class DocumentClass {

    public int id;
    public String documentType;
    public String route;
    public String documentName;
    public String documentSerial;
    public String lowLimit;
    public String topLimit;
    public String actualLimit;
    public String date;
    public String emaleg;
    public String decnum;
    public String state;
    public String lastNumber;

    public DocumentClass() {}

    public DocumentClass(int id, String documentType, String route, String documentName, String documentSerial, String lowLimit, String topLimit, String actualLimit, String date, String emaleg, String decnum, String state, String lastNumber) {
        this.id = id;
        this.documentType = documentType;
        this.route = route;
        this.documentName = documentName;
        this.documentSerial = documentSerial;
        this.lowLimit = lowLimit;
        this.topLimit = topLimit;
        this.actualLimit = actualLimit;
        this.date = date;
        this.emaleg = emaleg;
        this.decnum = decnum;
        this.state = state;
        this.lastNumber = lastNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentSerial() {
        return documentSerial;
    }

    public void setDocumentSerial(String documentSerial) {
        this.documentSerial = documentSerial;
    }

    public String getLowLimit() {
        return lowLimit;
    }

    public void setLowLimit(String lowLimit) {
        this.lowLimit = lowLimit;
    }

    public String getTopLimit() {
        return topLimit;
    }

    public void setTopLimit(String topLimit) {
        this.topLimit = topLimit;
    }

    public String getActualLimit() {
        return actualLimit;
    }

    public void setActualLimit(String actualLimit) {
        this.actualLimit = actualLimit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmaleg() {
        return emaleg;
    }

    public void setEmaleg(String emaleg) {
        this.emaleg = emaleg;
    }

    public String getDecnum() {
        return decnum;
    }

    public void setDecnum(String decnum) {
        this.decnum = decnum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(String lastNumber) {
        this.lastNumber = lastNumber;
    }
}
