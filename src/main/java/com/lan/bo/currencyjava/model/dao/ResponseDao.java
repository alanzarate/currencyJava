package com.lan.bo.currencyjava.model.dao;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;

import com.lan.bo.currencyjava.model.entity.ResponseEpModel;

@Component
public interface ResponseDao {
    
    @Insert("""
            INSERT INTO
                MY_RECORDS (R_FROM, R_TO, R_AMOUNT, R_RESULT, R_DATE)
                VALUES (#{ res.from },#{ res.to },#{ res.amount },#{ res.result }, #{ res.date }) ;
            """)
    void createRecord(ResponseEpModel res);
}
