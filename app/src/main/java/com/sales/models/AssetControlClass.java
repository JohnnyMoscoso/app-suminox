package com.sales.models;

public class AssetControlClass {

    //Head
    public int id;
    public String code;
    public String assignment;
    public String dateAssign;
    public String codeBranch;
    public String codeOffice;
    public String codeRoute;
    public String codeCustomer;
    public String idUserApp;
    public String nameOffice;
    public String nameBranch;
    public String nameRoute;
    public String nameCustomer;
    public String state;

    //Detail
    public String dateTask;
    public String hourTask;
    public String dateEndTask;
    public String hourEndTask;
    public String origin;
    public String equipment;
    public String type;
    public String task;
    public String answer;
    public String codeCustomerTask;
    public String stateTask;
    public String idTask;
    public String load;

    public AssetControlClass(){}

    public AssetControlClass(int id, String code, String assignment, String dateAssign, String codeBranch,
                             String codeOffice, String codeRoute, String codeCustomer, String idUserApp,
                             String nameOffice, String nameBranch, String nameRoute, String nameCustomer,
                             String state, String dateTask, String hourTask, String dateEndTask, String hourEndTask,
                             String origin, String equipment, String type, String task, String answer,
                             String codeCustomerTask, String stateTask, String idTask, String load) {
        this.id = id;
        this.code = code;
        this.assignment = assignment;
        this.dateAssign = dateAssign;
        this.codeBranch = codeBranch;
        this.codeOffice = codeOffice;
        this.codeRoute = codeRoute;
        this.codeCustomer = codeCustomer;
        this.idUserApp = idUserApp;
        this.nameOffice = nameOffice;
        this.nameBranch = nameBranch;
        this.nameRoute = nameRoute;
        this.nameCustomer = nameCustomer;
        this.state = state;
        this.dateTask = dateTask;
        this.hourTask = hourTask;
        this.dateEndTask = dateEndTask;
        this.hourEndTask = hourEndTask;
        this.origin = origin;
        this.equipment = equipment;
        this.type = type;
        this.task = task;
        this.answer = answer;
        this.codeCustomerTask = codeCustomerTask;
        this.stateTask = stateTask;
        this.idTask = idTask;

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

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getDateAssign() {
        return dateAssign;
    }

    public void setDateAssign(String dateAssign) {
        this.dateAssign = dateAssign;
    }

    public String getCodeBranch() {
        return codeBranch;
    }

    public void setCodeBranch(String codeBranch) {
        this.codeBranch = codeBranch;
    }

    public String getCodeOffice() {
        return codeOffice;
    }

    public void setCodeOffice(String codeOffice) {
        this.codeOffice = codeOffice;
    }

    public String getCodeRoute() {
        return codeRoute;
    }

    public void setCodeRoute(String codeRoute) {
        this.codeRoute = codeRoute;
    }

    public String getCodeCustomer() {
        return codeCustomer;
    }

    public void setCodeCustomer(String codeCustomer) {
        this.codeCustomer = codeCustomer;
    }

    public String getIdUserApp() {
        return idUserApp;
    }

    public void setIdUserApp(String idUserApp) {
        this.idUserApp = idUserApp;
    }

    public String getNameOffice() {
        return nameOffice;
    }

    public void setNameOffice(String nameOffice) {
        this.nameOffice = nameOffice;
    }

    public String getNameBranch() {
        return nameBranch;
    }

    public void setNameBranch(String nameBranch) {
        this.nameBranch = nameBranch;
    }

    public String getNameRoute() {
        return nameRoute;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDateTask() {
        return dateTask;
    }

    public void setDateTask(String dateTask) {
        this.dateTask = dateTask;
    }

    public String getHourTask() {
        return hourTask;
    }

    public void setHourTask(String hourTask) {
        this.hourTask = hourTask;
    }

    public String getDateEndTask() {
        return dateEndTask;
    }

    public void setDateEndTask(String dateEndTask) {
        this.dateEndTask = dateEndTask;
    }

    public String getHourEndTask() {
        return hourEndTask;
    }

    public void setHourEndTask(String hourEndTask) {
        this.hourEndTask = hourEndTask;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCodeCustomerTask() {
        return codeCustomerTask;
    }

    public void setCodeCustomerTask(String codeCustomerTask) {
        this.codeCustomerTask = codeCustomerTask;
    }

    public String getStateTask() {
        return stateTask;
    }

    public void setStateTask(String stateTask) {
        this.stateTask = stateTask;
    }

    public String getIdTask() {
        return idTask;
    }

    public void setIdTask(String idTask) {
        this.idTask = idTask;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }
}
