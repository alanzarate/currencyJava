package com.lan.bo.currencyjava.model.entity;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorModel {
    @JsonProperty("error")
    private Map<String, String > error;

    

    public Map<String, String> getError() {
        return error;
    }
    public void setError(Map<String, String> error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return error.toString();
    }
}
