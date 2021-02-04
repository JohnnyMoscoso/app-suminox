package com.sales.models;

public class SynVisitClass {

    public int id;
    public String idUser;
    public String codeCustomer;
    public String nameCustomer;
    public String codeVisit;
    public String nameVisit;
    public String latitudeCustomer;
    public String longitudeCustomer;
    public String latitude;
    public String longitude;
    public String zone;
    public String rate;
    public String dateHour;
    public String numberCustomerPending;
    public String load;

    public SynVisitClass(){

    }

    public SynVisitClass(int id, String idUser, String codeCustomer, String nameCustomer,
                         String codeVisit, String nameVisit, String latitudeCustomer,
                         String longitudeCustomer, String latitude, String longitude,
                         String zone, String rate, String dateHour, String numberCustomerPending, String load) {
        this.id = id;
        this.idUser = idUser;
        this.codeCustomer = codeCustomer;
        this.nameCustomer = nameCustomer;
        this.codeVisit = codeVisit;
        this.nameVisit = nameVisit;
        this.latitudeCustomer = latitudeCustomer;
        this.longitudeCustomer = longitudeCustomer;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zone = zone;
        this.rate = rate;
        this.dateHour = dateHour;
        this.numberCustomerPending = numberCustomerPending;
        this.load = load;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public String getCodeVisit() {
        return codeVisit;
    }

    public void setCodeVisit(String codeVisit) {
        this.codeVisit = codeVisit;
    }

    public String getNameVisit() {
        return nameVisit;
    }

    public void setNameVisit(String nameVisit) {
        this.nameVisit = nameVisit;
    }

    public String getLatitudeCustomer() {
        return latitudeCustomer;
    }

    public void setLatitudeCustomer(String latitudeCustomer) {
        this.latitudeCustomer = latitudeCustomer;
    }

    public String getLongitudeCustomer() {
        return longitudeCustomer;
    }

    public void setLongitudeCustomer(String longitudeCustomer) {
        this.longitudeCustomer = longitudeCustomer;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDateHour() {
        return dateHour;
    }

    public void setDateHour(String dateHour) {
        this.dateHour = dateHour;
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
