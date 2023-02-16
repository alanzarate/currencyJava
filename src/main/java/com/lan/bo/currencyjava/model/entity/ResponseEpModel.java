package com.lan.bo.currencyjava.model.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class ResponseEpModel {
    private String from;
    private String to;
    private BigDecimal amount;
    private BigDecimal result;
    private String date;

    public ResponseEpModel(String from, String to, BigDecimal amount, BigDecimal result, String date){
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.result = result;
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public String getDate() {
        return date;
    }
    public String getFrom() {
        return from;
    }
    public BigDecimal getResult() {
        return result;
    }
    public String getTo() {
        return to;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public void setResult(BigDecimal result) {
        this.result = result;
    }
    public void setTo(String to) {
        this.to = to;
    }

    public Date castDate(){
        return Date.valueOf(this.date);
    }
}
