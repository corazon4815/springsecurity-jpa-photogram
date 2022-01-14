package com.cos.photogram.domain.user;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;

//JPA - Java Persistence API (자바로 데이터를 영구적으로 저장할수있는 api를 제공)

@Builder
@AllArgsConstructor //전체 생성자
@NoArgsConstructor //빈생성자
@Data
@Entity //db에 테이블을 생성 //테이블 속성을 변경후 반영을 하려면 create로 바꿔서 실행해야함
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호증가전략이 db회사에 따라 맞춰짐
	private int id; 
	
	@Column(length = 30, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name; // 이름
	private String website; // 자기 홈페이지
	private String bio; // 자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl;
	private String provider; // 제공자 Google, Facebook, Naver
	
	private String role; // USER, ADMIN
	
//	@OneToMany(mappedBy = "user")
//	private List<Image> images;
	
	@CreationTimestamp
	private LocalDate createDate;

	@PrePersist //디비에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDate.now();
	}
;
}
