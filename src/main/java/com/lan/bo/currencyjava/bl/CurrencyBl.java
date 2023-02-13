package com.lan.bo.currencyjava.bl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lan.bo.currencyjava.customException.WrongFormatException;
import com.lan.bo.currencyjava.model.dto.ResponseDto;
import com.lan.bo.currencyjava.model.entity.ErrorModel;
import com.lan.bo.currencyjava.model.entity.ResponseModel;
import com.lan.bo.currencyjava.service.CurrencyService;

@Service
public class CurrencyBl {

    private CurrencyService currencyService;

    public  CurrencyBl(CurrencyService currencyService){
        this.currencyService = currencyService;
    }

    public Object getData(String from, String to, String amount) throws WrongFormatException, JsonMappingException, JsonProcessingException{
       
        BigDecimal amount2 = BigDecimal.valueOf(Double.valueOf(amount));
        
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
            Map<String, Object> toReturn = new HashMap<>();
            toReturn.put("from", from);
            toReturn.put("to", to);
            toReturn.put("amount", amount);
            toReturn.put("result", response.getResult());
            toReturn.put("date", response.getDate());

            return new ResponseDto<Map<String,Object>>(toReturn, null, true);

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
        }

        return new ResponseDto<String>(null , message, false);
    }
    
}
