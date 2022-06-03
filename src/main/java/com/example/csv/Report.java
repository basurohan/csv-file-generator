package com.example.csv;

import org.springframework.stereotype.Component;

@Component
public class Report {
    private Integer storeNbr;
    private Integer tagId;
    private String storeName;

    public Report() {
    }

    public Report(Integer storeNbr, Integer tagId, String storeName) {
        this.storeNbr = storeNbr;
        this.tagId = tagId;
        this.storeName = storeName;
    }

    public Integer getStoreNbr() {
        return storeNbr;
    }

    public void setStoreNbr(Integer storeNbr) {
        this.storeNbr = storeNbr;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
