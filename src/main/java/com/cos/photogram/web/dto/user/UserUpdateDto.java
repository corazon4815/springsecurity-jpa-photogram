package com.cos.photogram.web.dto.user;

import com.cos.photogram.domain.user.User;
import lombok.Data;

@Data
public class UserUpdateDto {
    private String name;
    private String password;
    private String website;
    private String bio;
    private String phone;
    private String gender;

    //안바뀔애들도있으니까 이대로 쓰면 조금 위험
    public User toEntity() {
        return User.builder()
                .name(name)
                .password(password)
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }
}
