package com.sales.models;

public class SynchronizationClass {

    public int id;
    public String className;
    public String dateHourStart;
    public String dateHourEnd;
    public String registerNumber;
    public String failureNumber;
    public String state;            // 1 -> Si faiulure number  == 0 ||  0 -> failure numbers > 0

    public SynchronizationClass(){

    }

    public SynchronizationClass(int id, String className, String dateHourStart, String dateHourEnd, String registerNumber, String failureNumber, String state) {
        this.id = id;
        this.className = className;
        this.dateHourStart = dateHourStart;
        this.dateHourEnd = dateHourEnd;
        this.registerNumber = registerNumber;
        this.failureNumber = failureNumber;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDateHourStart() {
        return dateHourStart;
    }

    public void setDateHourStart(String dateHourStart) {
        this.dateHourStart = dateHourStart;
    }

    public String getDateHourEnd() {
        return dateHourEnd;
    }

    public void setDateHourEnd(String dateHourEnd) {
        this.dateHourEnd = dateHourEnd;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getFailureNumber() {
        return failureNumber;
    }

    public void setFailureNumber(String failureNumber) {
        this.failureNumber = failureNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
