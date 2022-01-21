package com.cos.photogram.service;

import com.cos.photogram.domain.user.UserRepository;
import com.cos.photogram.handler.ex.CustomApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogram.domain.comment.Comment;
import com.cos.photogram.domain.comment.CommentRepository;
import com.cos.photogram.domain.image.Image;
import com.cos.photogram.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;


	@Transactional
	public Comment 댓글쓰기(String content, int imageId, int userId) {
		Image image = Image.builder()
				.id(imageId)
				.build();

		User userEntity = userRepository.findById(userId).orElseThrow(() ->
				new CustomApiException("유저 아이디를 찾을 수 없습니다."));

		// Save할 때 연관관계가 있으면 오브젝트로 만들어서 id값만 넣어주면 된다.
		Comment comment = Comment.builder()
				.content(content)
				.image(image)
				.user(userEntity)
				.build();

		return commentRepository.save(comment);
	}

	@Transactional
	public void 댓글삭제(int id) {
		try{
			commentRepository.deleteById(id);
		} catch (Exception e){
			throw new CustomApiException(e.getMessage());
		}
	}
}




