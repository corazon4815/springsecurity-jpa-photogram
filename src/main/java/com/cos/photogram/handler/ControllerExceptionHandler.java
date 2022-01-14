package com.cos.photogram.handler;

import com.cos.photogram.handler.ex.CustomValidationException;
import com.cos.photogram.util.Script;
import com.cos.photogram.web.dto.CMRespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

//    @ExceptionHandler(CustomValidationException.class)
//    //public CMRespDto<Map<String,String>> validationException(CustomValidationException e){
//    public CMRespDto<?> validationException(CustomValidationException e){ //?를쓰면 알아서 타입을 추측해서 해줌
//        return new CMRespDto(-1, e.getMessage(), e.getErrorMap());
//    }

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e){ //?를쓰면 알아서 타입을 추측해서 해줌
        return Script.back((e.getErrorMap().toString()));
    }
}
