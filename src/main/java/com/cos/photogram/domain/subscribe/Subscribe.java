package com.cos.photogram.domain.subscribe;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

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
		name="subscribe",
		uniqueConstraints={
			@UniqueConstraint(
				name = "subscribe_uk",
				columnNames={"fromUserId","toUserId"} //두개를 복합적으로 유니크로걸때는 이렇게 걺
			)
		}
	)
public class Subscribe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name = "fromUserId") //_없이 컬럼이 이 이름으로
	@ManyToOne //구독하기테이블이 N이므로
	private User fromUser;  // ~~로부터  (1) //구독하는애
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name = "toUserId")
	@ManyToOne //구독하기테이블이 N이므로
	private User toUser; // ~~를  (3) //구독받는애

	private LocalDateTime createDate;

	@PrePersist //디비에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}





