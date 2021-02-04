package com.sales.utils;

import ir.mirrajabi.searchdialog.core.Searchable;

public class SearchModel implements Searchable {

    private String mTitle;
    private String code;

    public SearchModel(String mTitle, String code){
        this.mTitle = mTitle;
        this.code = code;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle){
        this.mTitle = mTitle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
