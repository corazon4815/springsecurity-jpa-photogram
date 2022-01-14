package com.cos.photogram.handler.ex;

import java.util.Map;

public class CustomApiException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CustomApiException(String message){
        super(message); //message는 getter함수를 안만들어도됨 -> RuntimeException에 getter함수가 있으니까 부모한테 메시지를 던지기만 하면됨
    }

}
