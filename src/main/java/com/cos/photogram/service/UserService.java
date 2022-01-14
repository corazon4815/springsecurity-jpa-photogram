package com.cos.photogram.service;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User 회원수정(int id, User user) {
        //1. 영속화시켜서 userEntitiy를 변경시키면 db가 수정됨
        User userEntity = userRepository.findById(id).get();

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
