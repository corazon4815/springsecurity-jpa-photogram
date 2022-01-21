package com.cos.photogram.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import com.cos.photogram.domain.image.ImageRepository;
import com.cos.photogram.web.dto.image.ImageUploadDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.image.Image;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
//	private final TagRepository tagRepository;

	@Value("${file.path}")
	private String uploadFolder;

	@Transactional(readOnly = true) //true라고 안하면 영속성 컨텍스트 변경감지를 해서 더티체킹, flush(반영)함 ->안하니까성능이좋아짐
	public Page<Image> 이미지스토리(int principalId, Pageable pageable){
		Page<Image> images = imageRepository.mStory(principalId, pageable);
		// 좋아요 하트 색깔 로직 + 좋아요 카운트 로직
		images.forEach((image)-> {

			int likeCount = image.getLikes().size();
			image.setLikeCount(likeCount);

			image.getLikes().forEach((like)->{
				if(like.getUser().getId() == principalId) { //같으면 좋아요를 한것
					image.setLikeState(true);
				}
			});
		});
		return images;
	}
	
//	public Page<Image> 피드이미지(int principalId, Pageable pageable){
//
//		// 1. principalId 로 내가 팔로우하고 있는 사용자를 찾아야 됨. (한개이거나 컬렉션이거나)
//		// select * from image where userId in (select toUserId from follow where fromUserId = 1);
//
//		Page<Image> images = imageRepository.mFeed(principalId, pageable);
//
//		// 좋아요 하트 색깔 로직 + 좋아요 카운트 로직
//		images.forEach((image)-> {
//
//			int likeCount = image.getLikes().size();
//			image.setLikeCount(likeCount);
//
//			image.getLikes().forEach((like)->{
//				if(like.getUser().getId() == principalId) {
//					image.setLikeState(true);
//				}
//			});
//		});
//
//		return images;
//	}
//
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename();
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		//통신, IO 예외가 발생할수 있다.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 참고 :  Image 엔티티에 Tag는 주인이 아니다. Image 엔티티로 통해서 Tag를 save할 수 없다.
		
		// 1. Image 저장
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		imageRepository.save(image);
//		System.out.println(imageEntity);
//
//		// 2. Tag 저장
//		List<Tag> tags = TagUtils.parsingToTagObject(imageReDto.getTags(), imageEntity);
//		tagRepository.saveAll(tags);
		
	}
}







