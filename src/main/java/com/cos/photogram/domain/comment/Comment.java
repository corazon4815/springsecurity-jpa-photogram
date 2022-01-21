package com.cos.photogram.domain.comment;

import java.sql.Timestamp;
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
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 100, nullable = false)
	private String content;
	
	@JoinColumn(name = "imageId")
	@ManyToOne(fetch= FetchType.EAGER) //comment 테이블을 select 할때 image는 보통 한개만 가져오므로 EAGER 많이가져오는경우는 lazy
	private Image image; //사진 하나에 댓글이 여러개 있을수 있으므로 여긴 Comment엔티티니까 many가 앞에붙어서 ManyToOne
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name = "userId")
	@ManyToOne(fetch= FetchType.EAGER)
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
}





