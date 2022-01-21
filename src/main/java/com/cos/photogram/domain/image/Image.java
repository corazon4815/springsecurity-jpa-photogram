package com.cos.photogram.domain.image;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.cos.photogram.domain.comment.Comment;
import com.cos.photogram.domain.likes.Likes;
import org.hibernate.annotations.CreationTimestamp;

//import com.cos.photogram.domain.comment.Comment;
//import com.cos.photogram.domain.likes.Likes;
//import com.cos.photogram.domain.tag.Tag;
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
public class Image { //한명의 유저는 여러 이미지를 업로드할수있음
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String caption; // 오늘 나 너무 피곤했어!!
	private String postImageUrl;

//	@ManyToOne //fk로 저장이 되므로 이름을 바꿔줌
	@JsonIgnoreProperties({"images"})
	@ManyToOne(fetch = FetchType.EAGER) //이미지를 select하면 조인해서 User정보를 같이 들고옴
	@JoinColumn(name = "userId")
	private User user;
	
//	@JsonIgnoreProperties({"image"})
//	@OneToMany(mappedBy = "image")
//	private List<Tag> tags;
//
	@JsonIgnoreProperties({"image"}) //likes안에 image가 있어서 무한참조되니까
	@OneToMany(mappedBy = "image") //나는 연관관계의 주인공이 아니니 포린키만들지마세요
	private List<Likes> likes; // A이미지에 홍길동, 장보고, 임꺽정 좋아요.   (고소영)
//
	@OrderBy("id DESC")  // 정렬
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image")
	private List<Comment> comments;

	private LocalDateTime createDate;

	@PrePersist //디비에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	@Transient
	private boolean likeState;

	@Transient //db에 칼럼이 만들어지지 않는다.
	private int likeCount;


	//image 오브젝트를 출력하면 user를 출력하고 그안에 이미지를 출력하면서 무한 출력이 되므로 toString에서 user를 빼준다
//	@Override
//	public String toString() {
//		return "Image[" +
//				"id=" + id +
//				", caption='" + caption + '\'' +
//				", postImageUrl='" + postImageUrl + '\'' +
//				", createDate=" + createDate +
//				']';
//	}
}





