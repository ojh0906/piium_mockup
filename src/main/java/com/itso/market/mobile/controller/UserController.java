package com.itso.market.mobile.controller;

import com.itso.market.mobile.dao.EventDao;
import com.itso.market.mobile.model.*;
import com.itso.market.mobile.service.*;
import com.itso.market.security.service.SecurityUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user/*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    MemberService memberService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    CouponService couponService;

    @Autowired
    EventService eventService;

    @Autowired
    SecurityUserDetailsService securityUserDetailsService;

    @RequestMapping(value="/getUser", method= RequestMethod.POST)
    public ResponseOverlays getUser(@RequestBody @Validated MEMBER member) {
        try {
            logger.debug(member.toString());
            MEMBER result = memberService.getMember(member);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_MEMBER_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MEMBER_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MEMBER_FAIL", null);
        }
    }

    @RequestMapping(value="/modifyUser", method= RequestMethod.POST)
    public ResponseOverlays modifyUser(@RequestBody @Validated MEMBER member) {
        try {
            logger.debug(member.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            member.setChnId(chnId);
            int result = memberService.modifyMember(member);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_MEMBER_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "MODIFY_MEMBER_SUCCESS", member);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_MEMBER_FAIL", null);
        }
    }

    @RequestMapping(value="/deleteUser", method= RequestMethod.POST)
    public ResponseOverlays deleteUser(@RequestBody @Validated MEMBER member) {
        try {
            logger.debug(member.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            member.setChnId(chnId);
            int result = memberService.deleteMember(member);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_MEMBER_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "DELETE_MEMBER_SUCCESS", member);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_MEMBER_FAIL", null);
        }
    }

    @RequestMapping(value="/saveReview", method= RequestMethod.POST)
    public ResponseOverlays saveReview(@RequestBody @Validated REVIEW review) {
        try {
            logger.debug(review.toString());
            String regId = securityUserDetailsService.getCurrentId();
            review.setRegId(regId);
            review.setChnId(regId);
            int result = reviewService.saveReview(review);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_REVIEW_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "SAVE_REVIEW_SUCCESS", review);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_REVIEW_FAIL", null);
        }
    }

    @RequestMapping(value="/issuedCoupon", method= RequestMethod.POST)
    public ResponseOverlays issuedCoupon(@RequestBody @Validated COUPON coupon) {
        try {
            logger.debug(coupon.toString());
            String regId = securityUserDetailsService.getCurrentId();
            coupon.setRegId(regId);
            coupon.setChnId(regId);
            int result = couponService.issuedCoupon(coupon);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_COUPON_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "SAVE_COUPON_SUCCESS", coupon);
            }
        } catch (Exception e){
            logger.error(e.getLocalizedMessage());
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_COUPON_FAIL", null);
        }
    }

    @RequestMapping(value="/getCouponList", method= RequestMethod.POST)
    public ResponseOverlays getCouponList(@RequestBody @Validated COUPON coupon) {
        try {
            logger.debug(coupon.toString());
            List<COUPON> result = couponService.getCouponListInMember(coupon);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_LIST_COUPON_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_COUPON_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_COUPON_FAIL", null);
        }
    }

    @RequestMapping(value="/getCoupon", method= RequestMethod.POST)
    public ResponseOverlays getCoupon(@RequestBody @Validated COUPON coupon) {
        try {
            logger.debug(coupon.toString());
            COUPON result = couponService.getCouponForUser(coupon);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_COUPON_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_COUPON_NULL", null);
            }
        } catch (Exception e){
            logger.error(e.getLocalizedMessage());
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_COUPON_FAIL", null);
        }
    }

    @RequestMapping(value="/useCoupon", method= RequestMethod.POST)
    public ResponseOverlays useCoupon(@RequestBody @Validated COUPON coupon) {
        try {
            logger.debug(coupon.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            coupon.setChnId(chnId);
            int result = couponService.useCoupon(coupon);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_COUPON_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "MODIFY_COUPON_SUCCESS", coupon);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_COUPON_FAIL", null);
        }
    }

    @RequestMapping(value="/registerEvent", method= RequestMethod.POST)
    public ResponseOverlays registerEvent(@RequestBody @Validated EVENT event) {
        try {
            logger.debug(event.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            event.setChnId(chnId);
            int result = eventService.registerEvent(event);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_EVENT_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "MODIFY_EVENT_SUCCESS", event);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_EVENT_FAIL", null);
        }
    }

    @RequestMapping(value="/deleteMCoupon", method= RequestMethod.POST)
    public ResponseOverlays deleteMCoupon(@RequestBody @Validated COUPON coupon) {
        try {
            logger.debug(coupon.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            coupon.setChnId(chnId);
            int result = couponService.deleteMCoupon(coupon);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_COUPON_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "MODIFY_COUPON_SUCCESS", coupon);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_COUPON_FAIL", null);
        }
    }
}