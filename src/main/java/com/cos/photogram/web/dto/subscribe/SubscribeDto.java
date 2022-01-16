package com.cos.photogram.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
	private int id; //로그인한 사람을 확인해서 구독취소 구독하기 하려고
	private String username; //구독정보에 보이는 아이디
	private String profileImageUrl;
	private Integer subscribeState; //구독한생태인지 mariadb에서는 Integer
	private Integer equalUserState; //구독정보의 아이디가 로그인한 사람인지
}
