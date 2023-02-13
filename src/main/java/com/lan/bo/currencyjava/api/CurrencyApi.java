package com.lan.bo.currencyjava.api;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lan.bo.currencyjava.bl.CurrencyBl;
import com.lan.bo.currencyjava.model.dto.ResponseDto;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyApi {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyApi.class);
    private CurrencyBl currencyBl;

    public CurrencyApi(CurrencyBl currencyBl){
        this.currencyBl = currencyBl;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/convert")
    public ResponseDto<?> controllerMethod(
        @RequestHeader Map<String, String> headers,
        @RequestParam Map<String, String> customQuery){
        System.out.println("FROM: " + customQuery.get("from"));
        System.out.println("TO: " + customQuery.get("to"));
        System.out.println("AMOUNT: " + customQuery.get("amount"));


        // headers.forEach( (key, value) -> {
        //     System.out.println(String.format("Header '%s' = %s", key, value));
        // });
        String name = "Sam";
        logger.info("Hi, {}  debugg log", name);
        logger.info("Dummy info message");
        logger.warn("Dummy warning message.");
        logger.error("Dummy error message.");
        
        
        return currencyBl.getValues(customQuery.get("from"), customQuery.get("to"), customQuery.get("amount")  );
         
    }
    
}

