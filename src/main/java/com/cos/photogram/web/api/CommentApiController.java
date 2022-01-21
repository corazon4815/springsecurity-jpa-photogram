package com.cos.photogram.web.api;

import com.cos.photogram.domain.comment.Comment;
import com.cos.photogram.handler.ex.CustomApiException;
import com.cos.photogram.handler.ex.CustomValidationApiException;
import com.cos.photogram.web.dto.comment.CommentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.service.CommentService;
import com.cos.photogram.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

	private final CommentService commentService;

	@PostMapping("/api/comment")
	public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails){
		System.out.println(commentDto);
		if(bindingResult.hasErrors()){
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()){
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new CustomValidationApiException("유효성검사 실패함", errorMap);
		}

		Comment comment = commentService.댓글쓰기(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "댓글쓰기 성공", comment), HttpStatus.CREATED);
	}

	@DeleteMapping("/api/comment/{id}")
	public ResponseEntity<?> commentDelete(@PathVariable int id){
		commentService.댓글삭제(id);
		return new ResponseEntity<>(new CMRespDto<>(1, "댓글삭제 성공", null), HttpStatus.CREATED);
	}
}