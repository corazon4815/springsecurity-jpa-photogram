package com.cos.photogram.handler.ex;

public class CustomException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CustomException(String message){
        super(message); //message는 getter함수를 안만들어도됨 -> RuntimeException에 getter함수가 있으니까 부모한테 메시지를 던지기만 하면됨
    }

}
