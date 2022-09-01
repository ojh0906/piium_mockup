package com.itso.market.mobile.service;

import com.itso.market.mobile.dao.CouponDao;
import com.itso.market.mobile.dao.MemberDao;
import com.itso.market.mobile.dao.StoreDao;
import com.itso.market.mobile.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CouponService {

    @Autowired
    CouponDao couponDao;

    @Autowired
    StoreDao storeDao;

    // 키값으로 조회
    public COUPON getCoupon(COUPON coupon) {
        return couponDao.getCoupon(coupon);
    }

    // 저장
    public int saveCoupon(COUPON coupon){
        return couponDao.insertCoupon(coupon);
    }

    // 수정
    public int modifyCoupon(COUPON coupon){
        return couponDao.updateCoupon(coupon);
    }

    // 삭제
    public int deleteCoupon(COUPON coupon){
        return couponDao.deleteCoupon(coupon);
    }

    public List<COUPON> getCouponListInStore(COUPON coupon) {
        return couponDao.getCouponListInStore(coupon);
    }

    public List<COUPON> getCouponListAtStore(COUPON coupon) {
        return couponDao.getCouponListAtStore(coupon);
    }

    public int issuedCoupon(COUPON coupon){
        String uuid = UUID.randomUUID().toString();
        String cno = uuid.substring(uuid.length()-12,uuid.length()).toUpperCase();
        //coupon.setCno(String.format("%08d%08d%s", coupon.getCoupon().intValue(), coupon.getMember().intValue(), cno));
        coupon.setCno(cno);
        return couponDao.issuedCoupon(coupon);
    }

    public List<COUPON> getCouponListInMember(COUPON coupon) {
        List<COUPON> list = couponDao.getCouponListInMember(coupon);
        for(COUPON c : list){
            STORE store = new STORE();
            store.setStore(c.getStore());
            c.setFiles(storeDao.getFilesInStore(store));
        }
        return list;
    }

    public COUPON getCouponForUser(COUPON coupon) {
        COUPON result = couponDao.getCouponForUser(coupon);
        STORE store = new STORE();
        store.setStore(result.getStore());
        result.setFiles(storeDao.getFilesInStore(store));
        return result;
    }

    public int useCoupon(COUPON coupon){
        return couponDao.useCoupon(coupon);
    }

    public int deleteMCoupon(COUPON coupon){
        return couponDao.deleteMCoupon(coupon);
    }
}