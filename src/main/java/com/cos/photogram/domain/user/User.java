package com.cos.photogram.domain.user;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.cos.photogram.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

	//지금 이테이블은 1이고 이미지테이블은 N이니까 OneToMany
	//나는 연관관계의 주인이 아니고 테이블에 이 컬럼을 만들지마라
	//그리고 User를 select할때 해당 User id로 등록된 image들을 다 가져와라
	//LAZY = User를 select할때 해당 User id로 등록된 image들을 가져오지마 -> 대신 getImages() 함수의 image들이 호출될땐 가져와
	//Eager = User를 select할때 항상 User id로 등록된 image들을 전부 Join해서 가져와
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY) //양방향매핑
	@JsonIgnoreProperties({"user"}) //images 엔티티 내부에 있는 user은 무시하고 파싱함 (중요!!! 무한참조를막음)
	private List<Image> images; //did

	private LocalDateTime createDate;

	@PrePersist //디비에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
;
}
