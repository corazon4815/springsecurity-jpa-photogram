package com.cos.photogram.web;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.service.AuthService;
import com.cos.photogram.web.dto.auth.UserJoinReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@RequiredArgsConstructor //3. 모든 final 필드의 생성자를 만들어줌(DI할때 사용)
@Controller //IOC에 등록됐고 파일을 리턴하는 컨트롤러다
public class AuthController {

    //@Autowired //DI방법 3가지 : 1. DI에서 가져올때 Autowired를 걸거나 AuthController의
    // 2. 생성자를 만들어서 의존성주입해도됨
    private final AuthService authService;

//    public AuthController(AuthService authService){
//        this.authService = authService;
//    }

    //전처리 후처리 : AOP (관점 지향프로그래밍)

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@Valid UserJoinReqDto userJoinReqDto, BindingResult bindingResult) {
        User user = userJoinReqDto.toEntity();
        User userEntity = authService.회원가입(user);
        System.out.println(userEntity);
        return "auth/signin";
    }
}
