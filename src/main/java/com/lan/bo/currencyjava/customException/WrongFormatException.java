package com.lan.bo.currencyjava.customException;

public class WrongFormatException extends Exception{
    private Object obj = null;
    
    public WrongFormatException(){
        super();
        
    }
    // public WrongFormatException(String code){
    //     String message = "";
    //     if(code.equalsIgnoreCase("invalid_to_currency")){
    //         message = "Error in e"
    //     }else if(code.equalsIgnoreCase("invalid_from_currency")){

    //     }else if(code.equalsIgnoreCase("invalid_conversion_amount")){

    //     }
    // }

    public WrongFormatException(final String message){
        super(message);
    }
    public WrongFormatException(final Object obj){
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }
    public void setObj(Object obj) {
        this.obj = obj;
    }
    
}
