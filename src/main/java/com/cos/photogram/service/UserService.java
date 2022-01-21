package com.cos.photogram.service;

import com.cos.photogram.domain.subscribe.SubscribeRepository;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import com.cos.photogram.handler.ex.CustomApiException;
import com.cos.photogram.handler.ex.CustomException;
import com.cos.photogram.handler.ex.CustomValidationApiException;
import com.cos.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${file.path}")
    private String uploadFolder;

    public UserProfileDto 회원프로필(int pageUserId, int principalId){
        UserProfileDto dto = new UserProfileDto();
        User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다");
        });

        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId == principalId); //1은 페이지주인. -1은 주인이아님
        dto.setImageCount(userEntity.getImages().size());

        int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        dto.setSubscribeState(subscribeState == 1);
        dto.setSubscribeCount(subscribeCount);

        userEntity.getImages().forEach((image)->{
            image.setLikeCount(image.getLikes().size());
        });
        return dto;
    }

    @Transactional
    public User 회원수정(int id, User user) {
        //1. 영속화시켜서 userEntitiy를 변경시키면 db가 수정됨
        User userEntity = userRepository.findById(id).orElseThrow(()->{ return new CustomValidationApiException("찾을수 없는 아이디입니다");});


        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setName(user.getName());
        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
        //더티체킹이 일어나서 업데이트가 완료됨
    }

    @Transactional
    public User 회원사진변경(int principalId, MultipartFile profileImageFile) {

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid+"_"+profileImageFile.getOriginalFilename();

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);
        //통신, IO 예외가 발생할수 있다.
        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(()->{
            throw new CustomApiException("유저를 찾을 수 없습니다.");
        });
        userEntity.setProfileImageUrl(imageFileName);
        return userEntity;
    } // 더티체킹

//    @Transactional
//    public User 회원수정(int id, User user) {
//        // username, email 수정 불가
//        User userEntity = userRepository.findById(id).get();
//
//        userEntity.setName(user.getName());
//        userEntity.setBio(user.getBio());
//        userEntity.setWebsite(user.getWebsite());
//        userEntity.setGender(user.getGender());
//        String rawPassword = user.getPassword();
//        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
//
//        // 비밀번호를 수정시 안넘기면 기존 비번 유지
//        if(!user.getPassword().equals("")) {
//            userEntity.setPassword(encPassword);
//        }
//
//
//        return userEntity;
//    } // 더티체킹
}
