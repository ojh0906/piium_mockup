package com.itso.market.mobile.controller;

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

@RestController
@RequestMapping("/manager/*")
public class ManagerController {

    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    StoreService storeService;

    @Autowired
    MenuService menuService;

    @Autowired
    MemberService memberService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    CouponService couponService;

    @Autowired
    MarketService marketService;

    @Autowired
    SecurityUserDetailsService securityUserDetailsService;

    @RequestMapping(value="/getStore", method= RequestMethod.POST)
    public ResponseOverlays getStore(@RequestBody @Validated STORE store) {
        try {
            logger.debug(store.toString());
            STORE result = storeService.getStore(store);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_STORE_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_STORE_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_STORE_FAIL", null);
        }
    }

    @RequestMapping(value="/saveStore", method= RequestMethod.POST)
    public ResponseOverlays saveStore(@RequestBody @Validated STORE store) {
        try {
            logger.debug(store.toString());
            String regId = securityUserDetailsService.getCurrentId();
            store.setRegId(regId);
            store.setChnId(regId);
            int result = storeService.saveStore(store);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_STORE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "SAVE_STORE_SUCCESS", store);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_STORE_FAIL", null);
        }
    }

    @RequestMapping(value="/modifyStore", method= RequestMethod.POST)
    public ResponseOverlays modifyStore(@RequestBody @Validated STORE store) {
        try {
            logger.debug(store.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            store.setChnId(chnId);
            int result = storeService.modifyStore(store);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_STORE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "MODIFY_STORE_SUCCESS", store);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_STORE_FAIL", null);
        }
    }

    @RequestMapping(value="/deleteStore", method= RequestMethod.POST)
    public ResponseOverlays deleteStore(@RequestBody @Validated STORE store) {
        try {
            logger.debug(store.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            store.setChnId(chnId);
            int result = storeService.deleteStore(store);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_STORE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "DELETE_STORE_SUCCESS", store);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_STORE_FAIL", null);
        }
    }

    @RequestMapping(value="/getManager", method= RequestMethod.POST)
    public ResponseOverlays getManager(@RequestBody @Validated MEMBER member) {
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

    @RequestMapping(value="/modifyManager", method= RequestMethod.POST)
    public ResponseOverlays modifyManager(@RequestBody @Validated MEMBER member) {
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

    @RequestMapping(value="/getProductList", method= RequestMethod.POST)
    public ResponseOverlays getProductList(@RequestBody @Validated MENU menu) {
        try {
            logger.debug(menu.toString());
            List<MENU> result = menuService.getMenuListByStore(menu);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_LIST_MENU_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_MENU_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_MENU_FAIL", null);
        }
    }

    @RequestMapping(value="/getProduct", method= RequestMethod.POST)
    public ResponseOverlays getProduct(@RequestBody @Validated MENU menu) {
        try {
            logger.debug(menu.toString());
            MENU result = menuService.getMenu(menu);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_MENU_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MENU_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MENU_FAIL", null);
        }
    }

    @RequestMapping(value="/saveProduct", method= RequestMethod.POST)
    public ResponseOverlays saveProduct(@RequestBody @Validated MENU menu) {
        try {
            logger.debug(menu.toString());
            String regId = securityUserDetailsService.getCurrentId();
            menu.setRegId(regId);
            menu.setChnId(regId);
            int result = menuService.saveMenu(menu);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_MENU_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "SAVE_MENU_SUCCESS", menu);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_MENU_FAIL", null);
        }
    }

    @RequestMapping(value="/modifyProduct", method= RequestMethod.POST)
    public ResponseOverlays modifyProduct(@RequestBody @Validated MENU menu) {
        try {
            logger.debug(menu.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            menu.setChnId(chnId);
            int result = menuService.modifyMenu(menu);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_MENU_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "MODIFY_MENU_SUCCESS", menu);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_MENU_FAIL", null);
        }
    }

    @RequestMapping(value="/deleteProduct", method= RequestMethod.POST)
    public ResponseOverlays deleteProduct(@RequestBody @Validated MENU menu) {
        try {
            logger.debug(menu.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            menu.setChnId(chnId);
            int result = menuService.deleteMenu(menu);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_MENU_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "DELETE_MENU_SUCCESS", menu);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_MENU_FAIL", null);
        }
    }

    @RequestMapping(value="/deleteFile", method= RequestMethod.POST)
    public ResponseOverlays deleteFile(@RequestBody @Validated FILE file) {
        try {
            logger.debug(file.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            file.setChnId(chnId);
            int result = storeService.deleteFileInStore(file);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_FILE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "DELETE_FILE_SUCCESS", file);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_FILE_FAIL", null);
        }
    }

    @RequestMapping(value="/getReviewList", method= RequestMethod.POST)
    public ResponseOverlays getReviewList(@RequestBody @Validated REVIEW review) {
        try {
            logger.debug(review.toString());
            List<REVIEW> result = reviewService.getReviewListByStore(review);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_LIST_MENU_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_MENU_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_MENU_FAIL", null);
        }
    }

    @RequestMapping(value="/getCoupon", method= RequestMethod.POST)
    public ResponseOverlays getCoupon(@RequestBody @Validated COUPON coupon) {
        try {
            logger.debug(coupon.toString());
            COUPON result = couponService.getCoupon(coupon);
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

    @RequestMapping(value="/saveCoupon", method= RequestMethod.POST)
    public ResponseOverlays saveCoupon(@RequestBody @Validated COUPON coupon) {
        try {
            logger.debug(coupon.toString());
            String regId = securityUserDetailsService.getCurrentId();
            coupon.setRegId(regId);
            coupon.setChnId(regId);
            int result = couponService.saveCoupon(coupon);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_COUPON_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "SAVE_COUPON_SUCCESS", coupon);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_COUPON_FAIL", null);
        }
    }

    @RequestMapping(value="/modifyCoupon", method= RequestMethod.POST)
    public ResponseOverlays modifyCoupon(@RequestBody @Validated COUPON coupon) {
        try {
            logger.debug(coupon.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            coupon.setChnId(chnId);
            int result = couponService.modifyCoupon(coupon);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_COUPON_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "MODIFY_COUPON_SUCCESS", coupon);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_COUPON_FAIL", null);
        }
    }

    @RequestMapping(value="/deleteCoupon", method= RequestMethod.POST)
    public ResponseOverlays deleteCoupon(@RequestBody @Validated COUPON coupon) {
        try {
            logger.debug(coupon.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            coupon.setChnId(chnId);
            int result = couponService.deleteCoupon(coupon);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_COUPON_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "DELETE_COUPON_SUCCESS", coupon);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_COUPON_FAIL", null);
        }
    }

    @RequestMapping(value="/getCouponList", method= RequestMethod.POST)
    public ResponseOverlays getCouponList(@RequestBody @Validated COUPON coupon) {
        try {
            logger.debug(coupon.toString());
            List<COUPON> result = couponService.getCouponListInStore(coupon);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_LIST_COUPON_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_COUPON_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_COUPON_FAIL", null);
        }
    }

    @RequestMapping(value="/getMarketList", method= RequestMethod.POST)
    public ResponseOverlays getMarketList() {
        try {
            List<MARKET> result = marketService.getMarketList();
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_MARKET_LIST_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MARKET_LIST_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MARKET_LIST_FAIL", null);
        }
    }

    @RequestMapping(value="/getMarket", method= RequestMethod.POST)
    public ResponseOverlays getMarket(@RequestBody @Validated MARKET market) {
        try {
            MARKET result = marketService.getMarket(market);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_MARKET_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MARKET_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MARKET_FAIL", null);
        }
    }

    @RequestMapping(value="/deleteProductFile", method= RequestMethod.POST)
    public ResponseOverlays deleteProductFile(@RequestBody @Validated FILE file) {
        try {
            logger.debug(file.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            file.setChnId(chnId);
            int result = menuService.deleteFileInMenu(file);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_FILE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "DELETE_FILE_SUCCESS", file);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_FILE_FAIL", null);
        }
    }
}