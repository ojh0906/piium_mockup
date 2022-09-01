package com.itso.market.mobile.service;

import com.itso.market.mobile.dao.MenuDao;
import com.itso.market.mobile.dao.ReviewDao;
import com.itso.market.mobile.model.FILE;
import com.itso.market.mobile.model.MENU;
import com.itso.market.mobile.model.REVIEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewService {

    @Autowired
    ReviewDao reviewDao;

    // 키값으로 조회
    public REVIEW getReview(REVIEW review) {
        return reviewDao.getReview(review);
    }

    // 저장
    public int saveReview(REVIEW review){
        return reviewDao.insertReview(review);
    }

    // 수정
    public int modifyReview(REVIEW review){
        return reviewDao.updateReview(review);
    }

    // 삭제
    public int deleteReview(REVIEW review){
        return reviewDao.deleteReview(review);
    }

    // 가게 정보 리스트
    public List<REVIEW> getReviewListByStore(REVIEW review) {
        return reviewDao.getReviewListByStore(review);
    }
}
