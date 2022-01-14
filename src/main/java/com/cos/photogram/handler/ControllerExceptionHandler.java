package com.cos.photogram.handler;

import com.cos.photogram.handler.ex.CustomApiException;
import com.cos.photogram.handler.ex.CustomException;
import com.cos.photogram.handler.ex.CustomValidationApiException;
import com.cos.photogram.handler.ex.CustomValidationException;
import com.cos.photogram.util.Script;
import com.cos.photogram.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return Script.back(e.getErrorMap().toString());
    }

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationException e){ //?를쓰면 알아서 타입을 추측해서 해줌
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e){
        return Script.back(e.getMessage());
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> ApiException(CustomApiException e){ //?를쓰면 알아서 타입을 추측해서 해줌
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
