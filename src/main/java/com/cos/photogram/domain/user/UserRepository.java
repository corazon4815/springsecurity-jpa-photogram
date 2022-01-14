package com.cos.photogram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository를 상속하면 어노테이션 없이도 IOC에 등록이됨
public interface UserRepository extends JpaRepository<User, Integer>{
													//<오브젝트키, pk의 타입>
	User findByUsername(String username);
}
