package com.lan.bo.currencyjava.bl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lan.bo.currencyjava.customException.InvalidDataException;
import com.lan.bo.currencyjava.customException.WrongFormatException;
import com.lan.bo.currencyjava.daos.ResponseDao;
import com.lan.bo.currencyjava.model.dto.ResponseDto;
import com.lan.bo.currencyjava.model.entity.ErrorModel;
import com.lan.bo.currencyjava.model.entity.ResponseEpModel;
import com.lan.bo.currencyjava.model.entity.ResponseModel;
import com.lan.bo.currencyjava.service.CurrencyService;

@Service
public class CurrencyBl {
   
    @Autowired
    private ResponseDao responseDao;

    private CurrencyService currencyService;
    

    public  CurrencyBl(CurrencyService currencyService){
        this.currencyService = currencyService;
        
    }

    public Object getData(String from, String to, String amount) throws WrongFormatException, JsonMappingException, JsonProcessingException, InvalidDataException{
       
        BigDecimal amount2 = BigDecimal.valueOf(Double.valueOf(amount));
        if(amount2.compareTo(BigDecimal.valueOf(0)) < 0) throw new InvalidDataException("Numero menor a 0");
        
        ResponseEntity<String> response = currencyService.responseByApi(from, to, amount2);
        ObjectMapper obMapper = new ObjectMapper();
        
        if(response.getStatusCode().equals(HttpStatus.OK)){
            ResponseModel responseModel = obMapper.readValue(response.getBody(), ResponseModel.class);
            return responseModel;
        }else if(response.getStatusCode().equals(HttpStatus.BAD_REQUEST)){
            ErrorModel errorModel = obMapper.readValue(response.getBody(), ErrorModel.class);
            throw new WrongFormatException(errorModel);
        }

        throw new WrongFormatException(new ErrorModel());
    
    }

    
    public ResponseDto<?> getValues(String from, String to, String amount){
        String message = "";
        try{
            ResponseModel response = (ResponseModel) getData(from, to, amount);
            ResponseEpModel responseReturn = 
            new ResponseEpModel(from, to,  BigDecimal.valueOf(Double.valueOf(amount)), response.getResult(), response.getDate());

            
            // goes to db
            responseDao.createRecord(responseReturn, responseReturn.castDate());
            // response to user
            return new ResponseDto<ResponseEpModel>(responseReturn, null, true);

        }catch(WrongFormatException ex){
            ErrorModel error = (ErrorModel) ex.getObj();
            
            switch(error.getError().get("code")){
                case "invalid_conversion_amount":
                    message = "Verificar el monto para la conversion";
                    break;
                case "invalid_to_currency":
                    message = "Parametro (to) incorrecto";
                    break;
                case "invalid_from_currency":
                    message = "Parametro (from) incorrecto";
                    break;
                    
            }

          
             
        } catch (JsonMappingException e) {
            message = e.getMessage();
             
        } catch (JsonProcessingException e) {
            message = e.getMessage();
        } catch (NumberFormatException e){
            message = "monto incorrecto";
        } catch (NullPointerException e){
            if(amount == null){
                message = "Falta el parametro necesario de (amount)";
            } 
        } catch(InvalidDataException e){
            message = e.getMessage();
        }

        return new ResponseDto<String>(null , message, false);
    }
    
}
