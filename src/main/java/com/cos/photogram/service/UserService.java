package com.cos.photogram.service;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import com.cos.photogram.handler.ex.CustomException;
import com.cos.photogram.handler.ex.CustomValidationApiException;
import com.cos.photogram.handler.ex.CustomValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User 회원프로필(int userId){
        User userEntity = userRepository.findById(userId).orElseThrow(()->{
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다");
        });
        return userEntity;
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
