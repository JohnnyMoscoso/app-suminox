package com.sales.models;

public class SynOrderClass {

    public int id;
    public String type;
    public String codeCustomer;
    public String nameCustomer;
    public String numberDocument;
    public String numeration;
    public String codeRoute;
    public String idUserApp;
    public String dateHour;
    public String ruc;
    public String stateNubeFact;
    public String operationExonerated;
    public String operationTaxed;
    public String igv;
    public String totalPay;
    public String stateCharge;
    public String load;

    public SynOrderClass() {}

    public SynOrderClass(int id, String type, String codeCustomer, String nameCustomer, String numberDocument,
                         String numeration, String codeRoute, String idUserApp, String dateHour, String ruc,
                         String stateNubeFact, String operationExonerated, String operationTaxed, String igv,
                         String totalPay, String stateCharge, String load) {
        this.id = id;
        this.type = type;
        this.codeCustomer = codeCustomer;
        this.nameCustomer = nameCustomer;
        this.numberDocument = numberDocument;
        this.numeration = numeration;
        this.codeRoute = codeRoute;
        this.idUserApp = idUserApp;
        this.dateHour = dateHour;
        this.ruc = ruc;
        this.stateNubeFact = stateNubeFact;
        this.operationExonerated = operationExonerated;
        this.operationTaxed = operationTaxed;
        this.igv = igv;
        this.totalPay = totalPay;
        this.stateCharge = stateCharge;
        this.load = load;
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

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getNumberDocument() {
        return numberDocument;
    }

    public void setNumberDocument(String numberDocument) {
        this.numberDocument = numberDocument;
    }

    public String getNumeration() {
        return numeration;
    }

    public void setNumeration(String numeration) {
        this.numeration = numeration;
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

    public String getDateHour() {
        return dateHour;
    }

    public void setDateHour(String dateHour) {
        this.dateHour = dateHour;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getStateNubeFact() {
        return stateNubeFact;
    }

    public void setStateNubeFact(String stateNubeFact) {
        this.stateNubeFact = stateNubeFact;
    }

    public String getOperationExonerated() {
        return operationExonerated;
    }

    public void setOperationExonerated(String operationExonerated) {
        this.operationExonerated = operationExonerated;
    }

    public String getOperationTaxed() {
        return operationTaxed;
    }

    public void setOperationTaxed(String operationTaxed) {
        this.operationTaxed = operationTaxed;
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
