package com.cos.photogram.web;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.service.UserService;
import com.cos.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserProfileDto dto = userService.회원프로필(pageUserId, principalDetails.getUser().getId());
        model.addAttribute("dto", dto);
        return "user/profile";
    }
                                                //시큐리티는 세션정보가 여러 단계 안에 숨겨져있음
    @GetMapping("/user/{id}/update")         //@AuthenticationPrincipal ->이거로 세션에 바로접근할수있음
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails){
//        System.out.println(principalDetails.getUser());
        return "user/update";
    }
}
