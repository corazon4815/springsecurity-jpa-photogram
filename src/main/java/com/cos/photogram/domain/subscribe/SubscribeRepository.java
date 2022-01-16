package com.cos.photogram.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer>{

	// write (@Modifying)
	@Modifying //데이터베이스 변경을 주는 네이티브 쿼리에는 붙여줘야함 (인서트,딜리트,업데이트)
	@Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
	void mSubscribe(int fromUserId, int toUserId); // 변경된 행의 갯수만큼 리턴  못햇으면 -1

	@Modifying
	@Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
	void mUnSubscribe(int fromUserId, int toUserId); // prepareStatement updateQuery() => -1 0 1
	
	@Query(value = "select count(*) from subscribe where fromUserId = :principalId AND toUserId = :pageUserId", nativeQuery = true)
	int mSubscribeState(int principalId, int pageUserId);

	@Query(value = "select count(*) from subscribe where fromUserId = :pageUserId", nativeQuery = true)
	int mSubscribeCount(int pageUserId);
}
