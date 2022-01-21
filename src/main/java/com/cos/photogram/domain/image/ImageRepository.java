package com.cos.photogram.domain.image;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer>{

    @Query(value="select * from image where userId In (select toUserId from subscribe where fromUserId=:principalId) order by id desc", nativeQuery = true)
    Page<Image> mStory(int principalId, Pageable pageable);

}
