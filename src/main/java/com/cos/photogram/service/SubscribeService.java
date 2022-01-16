package com.cos.photogram.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.cos.photogram.handler.ex.CustomApiException;
import com.cos.photogram.web.dto.subscribe.SubscribeDto;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogram.domain.subscribe.SubscribeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em; //모든 레퍼지토리는 EntityManager를 구현해서 만들어져있는 구현체

	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId){

		StringBuffer sb = new StringBuffer();
		sb.append("select u.id, u.username, u.profileImageUrl, ");
		sb.append("if((select 1 from subscribe where fromUserId = ? and toUserId = u.id), true, false) subscribeState, ");  // principalDetails.user.id
		sb.append("if(? = u.id , 1, 0) equalUserState "); // principalDetails.user.id
		sb.append("from user u inner join subscribe s ");
		sb.append("on u.id = s.toUserId "); // pageUserId
        sb.append("where s.fromUserId = ?"); // 마지막에 세미콜론쓰면안됨

        //쿼리완성
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);

		System.out.println("쿼리 : "+query.getResultList().get(0));

		//쿼리실행 (qlrm 라이브러리 필요 - dto에 db결과를 매핑하기위해)
		JpaResultMapper result  = new JpaResultMapper();
		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
		return subscribeDtos;
	}

    @Transactional
    public void 구독하기(int fromUserId, int toUserId) {
        try {
            subscribeRepository.mSubscribe(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 구독을 하였습니다.");
        }
    }

    @Transactional
    public void 구독취소(int fromUserId, int toUserId) { //삭제니까 오류가 날일이없음 //실제오류가날때 catch하기
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }
}
