package com.sales.models;

public class LoadImageClass {

    public int id;
    public String code;
    public String name;
    public String codeHive;
    public String codeApiary;
    public String description;
    public String path;
    public String load;

    public LoadImageClass() {
    }

    public LoadImageClass(int id, String code, String name, String codeHive, String codeApiary, String description, String path, String load) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.codeHive = codeHive;
        this.codeApiary = codeApiary;
        this.description = description;
        this.path = path;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeHive() {
        return codeHive;
    }

    public void setCodeHive(String codeHive) {
        this.codeHive = codeHive;
    }

    public String getCodeApiary() {
        return codeApiary;
    }

    public void setCodeApiary(String codeApiary) {
        this.codeApiary = codeApiary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }
}
