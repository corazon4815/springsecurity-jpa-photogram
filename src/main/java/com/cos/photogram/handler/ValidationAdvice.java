package com.cos.photogram.handler;

import com.cos.photogram.handler.ex.CustomValidationApiException;
import com.cos.photogram.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;

@Component //RestController, Service 모두 component를 상속해서 만들어져있기때문에 애매하면 component
@Aspect
public class ValidationAdvice {
    //*Controller.*(..) 모든 Controller안의 모든함수가 실행될때 작동
    @Around("execution(* com.cos.photogram.web.api.*Controller.*(..))") //시작전이랑 끝날때 모두 실행하고싶을때
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) { //모든 매개변수
            if (arg instanceof BindingResult) { //변수에 BindingResult가 있으면
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성검사 실패함", errorMap);
                }
            }
        }

        //proceedingJoinPoint => profile 함수의 모든곳에 접근할 수 있는 변수
        //profile함수보다 먼저 실행

        return proceedingJoinPoint.proceed(); //profile함수가 실행됨
    }

    @Around("execution(* com.cos.photogram.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) { //모든 매개변수
            if (arg instanceof BindingResult) { //변수에 BindingResult가 있으면
                BindingResult bindingResult = (BindingResult) arg; //다운캐스팅
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                        System.out.println(error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성", errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
