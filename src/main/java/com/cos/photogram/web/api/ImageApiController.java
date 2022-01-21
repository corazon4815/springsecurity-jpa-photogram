package com.cos.photogram.web.api;

import com.cos.photogram.service.LikesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cos.photogram.config.auth.PrincipalDetails;
//import com.cos.photogram.domain.comment.Comment;
import com.cos.photogram.domain.image.Image;
//import com.cos.photogram.service.CommentService;
import com.cos.photogram.service.ImageService;
//import com.cos.photogram.service.LikesService;
import com.cos.photogram.web.dto.CMRespDto;
//import com.cos.photogram.web.dto.image.ImageReqDto;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

	private final ImageService imageService;
	private final LikesService likesService;
//	private final CommentService commentService;

//	@GetMapping({"/", "/image/story"})
//	public String feed() {
//		return "image/story";
//	}
//
//
//
//
//	@GetMapping("/image/popular")
//	public String explore(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
//
//		model.addAttribute("images", imageService.인기사진(principalDetails.getUser().getId()));
//
//		return "image/popular";
//	}
//
//	@GetMapping("/image/upload")
//	public String upload() {
//		return "image/upload";
//	}

	@GetMapping("/api/image") //이미지 전부를 들고옴
	public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
										@PageableDefault(size=3) Pageable pageable) {
		Page<Image> images = imageService.이미지스토리(principalDetails.getUser().getId(), pageable);
		return new ResponseEntity<>(new CMRespDto<>(1, "성공", images), HttpStatus.OK);
	}

	@PostMapping("/api/image/{imageId}/likes")
	public ResponseEntity<?> like(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int imageId){
		likesService.좋아요(imageId, principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "좋아요성공", null), HttpStatus.OK);
	}

	@DeleteMapping("/api/image/{imageId}/likes")
	public ResponseEntity<?> unLike(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int imageId){
		likesService.싫어요(imageId, principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "싫어요성공", null), HttpStatus.OK);
	}
//
//	@PostMapping("/image/{imageId}/comment")
//	public @ResponseBody CMRespDto<?> save(@PathVariable int imageId, @RequestBody String content, @AuthenticationPrincipal PrincipalDetails principalDetails){   // content, imageId, userId(세션)
//		Comment commentEntity = commentService.댓글쓰기(principalDetails.getUser(), content, imageId);
//
//		return new CMRespDto<>(1, commentEntity);
//	}
}













