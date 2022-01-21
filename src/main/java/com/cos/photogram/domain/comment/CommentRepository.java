package com.cos.photogram.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
//
//    @Query(value="insert into comment(content, imageId, userId, createDate) values(:content,:imageId,:userId,now())", nativeQuery = true)
//    int mSave(String Content, int imageId, int userId);
//
      //insert 네이티브쿼리를 쓰면 int를 되돌려 받기때문에 insert한 이후 pk를 알수없음 comment를 return받으려면 jpa save함수로해야함
}
