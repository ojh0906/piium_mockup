package com.itso.market.mobile.dao;

import com.itso.market.mobile.model.COUPON;
import com.itso.market.mobile.model.REVIEW;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CouponDao {
    COUPON getCoupon(COUPON coupon);
    int insertCoupon(COUPON coupon);
    int updateCoupon(COUPON coupon);
    int deleteCoupon(COUPON coupon);
    List<COUPON> getCouponListInStore(COUPON coupon);
    List<COUPON> getCouponListAtStore(COUPON coupon);

    int issuedCoupon(COUPON coupon);

    List<COUPON> getCouponListInMember(COUPON coupon);

    COUPON getCouponForUser(COUPON coupon);
    int useCoupon(COUPON coupon);
    int deleteMCoupon(COUPON coupon);
}
