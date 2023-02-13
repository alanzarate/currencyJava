package com.lan.bo.currencyjava.model.entity;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseModel {
    private Boolean success;
    private Map<String, ?> query;
    private String date;
    private BigDecimal result;
    private String fool;

    public String getFool() {
        return fool;
    }
    public void setFool(String fool) {
        this.fool = fool;
    }

    public BigDecimal getResult() {
        return result;
    }
    public void setResult(BigDecimal result) {
        this.result = result;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public Map<String, ?> getQuery() {
        return query;
    }
    public void setQuery(Map<String, ?> query) {
        this.query = query;
    }
    public Boolean getSuccess() {
        return success;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
