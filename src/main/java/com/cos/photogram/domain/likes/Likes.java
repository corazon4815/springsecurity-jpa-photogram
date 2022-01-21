package com.cos.photogram.domain.likes;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.photogram.domain.image.Image;
import com.cos.photogram.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(
		name="likes",
		uniqueConstraints={
			@UniqueConstraint(
				name = "likes_uk",
				columnNames={"imageId","userId"}  //중복유니크로묶음 (같은사람이 같은그림에 좋아요를 두번할수없음)
			)
		}
	)
public class Likes { //N
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	
	@ManyToOne
	@JoinColumn(name = "imageId")
	private Image image; //하나의 이미지는 좋아요가 여러개일수있음 //1

	//오류가 터지고 잡아보자
//	@JsonIgnoreProperties({"images"})
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user; //한명의 유저는 좋아요를 여러개할 수 있음

	private LocalDateTime createDate;

	@PrePersist //디비에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now(); //네이티브쿼리쓸때는 안들어감
	}
}










