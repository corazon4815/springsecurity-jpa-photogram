package com.cos.photogram.web.dto.comment;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//NotEmpty : 빈값이거나 널체크
@Data
public class CommentDto {
	@NotBlank //빈값이거나 NULL 체크, 공백(스페이스)까지 체크
	private String content;
	@NotNull //널 체크
	private Integer imageId;

	//toEntity가 필요없다

}





