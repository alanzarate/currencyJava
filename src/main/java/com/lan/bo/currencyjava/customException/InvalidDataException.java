package com.lan.bo.currencyjava.customException;

public class InvalidDataException extends Exception{
    public InvalidDataException(final String message){
        super(message);
    }
    public InvalidDataException(){
        super();
        
    }
}
