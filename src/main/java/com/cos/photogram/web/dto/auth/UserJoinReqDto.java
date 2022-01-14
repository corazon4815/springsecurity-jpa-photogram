package com.cos.photogram.web.dto.auth;


import com.cos.photogram.domain.user.User;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class UserJoinReqDto {
	@Size(min=2, max=20)
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String email;
	@NotBlank
	private String name;
	
	public User toEntity() {
		return User.builder()
				.username(username) //이름을 기재안했으면 문제 valid 체크
				.password(password)
				.email(email)
				.name(name)
				.build();
	}
}
