package com.itso.market.mobile.dao;

import com.itso.market.mobile.model.FILE;
import com.itso.market.mobile.model.MENU;
import com.itso.market.mobile.model.REVIEW;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReviewDao {
    REVIEW getReview(REVIEW review);
    int insertReview(REVIEW review);
    int updateReview(REVIEW review);
    int deleteReview(REVIEW review);
    List<REVIEW> getReviewListByStore(REVIEW review);
}
