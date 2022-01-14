package com.cos.photogram.handler.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException{
    //jvm이 객체를 구분할때 사용
    private static final long serialVersionUID = 1L;

    private String message;
    private Map<String, String> errorMap;

    public CustomValidationException(String message, Map<String, String> errorMap){
        super(message); //message는 getter함수를 안만들어도됨 -> RuntimeException에 getter함수가 있으니까 부모한테 메시지를 던지기만 하면됨
        this.message = message;
        this.errorMap = errorMap;
    }

    //getter
    public Map<String, String> getErrorMap(){
        return errorMap;
    }
}
