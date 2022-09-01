package com.itso.market.mobile.controller;

import com.itso.market.mobile.model.*;
import com.itso.market.mobile.service.*;
import com.itso.market.security.service.SecurityUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/main/*")
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    NoticeService noticeService;

    @Autowired
    MemberService memberService;

    @Autowired
    StoreService storeService;

    @Autowired
    MenuService menuService;

    @Autowired
    FileService fileService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    CouponService couponService;

    @Autowired
    EventService eventService;

    @Autowired
    MarketService marketService;

    @Autowired
    SecurityUserDetailsService securityUserDetailsService;

    /**
     * 화면 기능에 추가적으로 필요한 기능들
     * */
    @RequestMapping(value="/getIdCheck", method= RequestMethod.POST)
    public ResponseOverlays getIdCheck(@RequestBody @Validated MEMBER member) {
        try {
            logger.debug(member.toString());
            MEMBER result = memberService.getIdCheck(member);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_ID_MEMBER_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_ID_MEMBER_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_ID_MEMBER_FAIL", null);
        }
    }

    @RequestMapping(value="/getLoginInfo", method= RequestMethod.POST)
    public ResponseOverlays getLoginInfo() {
        try {
            MEMBER result = securityUserDetailsService.getCurrentMember();
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_CURRENT_MEMEBER_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_CURRENT_MEMEBER_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_CURRENT_MEMEBER_FAIL", null);
        }
    }

    @RequestMapping(value="/getBannerList", method= RequestMethod.POST)
    public ResponseOverlays getBannerList(@RequestBody @Validated FILE file) {
        try {
            logger.debug(file.toString());
            List<FILE> result = fileService.getBannerList(file);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_LIST_BANNER_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_BANNER_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_BANNER_FAIL", null);
        }
    }

    /**
     * ********************************************************************************************************************************
     * */

    /**
     * 오준호가 요청한 API 목록
     * */
    @RequestMapping(value="/getNoticeList", method= RequestMethod.POST)
    public ResponseOverlays getNoticeList() {
        try {
            List<NOTICE> result = noticeService.getNoticeList();
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_LIST_NOTICE_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_NOTICE_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_NOTICE_FAIL", null);
        }
    }

    @RequestMapping(value="/getNotice", method= RequestMethod.POST)
    public ResponseOverlays getNotice(@RequestBody @Validated NOTICE notice) {
        try {
            logger.debug(notice.toString());
            NOTICE result = noticeService.getNotice(notice);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_NOTICE_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_NOTICE_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_NOTICE_FAIL", null);
        }
    }

    @RequestMapping(value="/getRecStoreList", method= RequestMethod.POST)
    public ResponseOverlays getRecStoreList(@RequestBody CATEGORY category) {
        try {
            List<STORE> result = storeService.getRecStoreList(category);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_LIST_REC_STORE_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_REC_STORE_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_REC_STORE_FAIL", null);
        }
    }

    @RequestMapping(value="/getPopStoreList", method= RequestMethod.POST)
    public ResponseOverlays getPopStoreList(@RequestBody CATEGORY category) {
        try {
            List<STORE> result = storeService.getPopStoreList(category);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_LIST_POP_STORE_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_POP_STORE_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_POP_STORE_FAIL", null);
        }
    }

    @RequestMapping(value="/getCategoryList", method= RequestMethod.POST)
    public ResponseOverlays getCategoryList() {
        try {
            List<CATEGORY> result = storeService.getCategoryList();
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_LIST_CATEGORY_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_CATEGORY_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_CATEGORY_FAIL", null);
        }
    }

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

    @RequestMapping(value="/getStoreListByCategory", method= RequestMethod.POST)
    public ResponseOverlays getStoreListByCategory(@RequestBody SEARCH search) {
        try {
            List<STORE> result = storeService.getStoreListByCategory(search);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_LIST_STORE_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_STORE_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_STORE_FAIL", null);
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

    @RequestMapping(value="/getCouponList", method= RequestMethod.POST)
    public ResponseOverlays getCouponList(@RequestBody @Validated COUPON coupon) {
        try {
            logger.debug(coupon.toString());
            List<COUPON> result = couponService.getCouponListAtStore(coupon);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_LIST_COUPON_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_COUPON_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_COUPON_FAIL", null);
        }
    }

    @RequestMapping(value="/countUpVisitor", method= RequestMethod.POST)
    public ResponseOverlays countUpVisitor(@RequestBody @Validated STORE store) {
        try {
            logger.debug(store.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            store.setChnId(chnId);
            int result = storeService.countUpVisitor(store);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_STORE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "MODIFY_STORE_SUCCESS", store);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_STORE_FAIL", null);
        }
    }

    @RequestMapping(value="/getEventListForMain", method= RequestMethod.POST)
    public ResponseOverlays getEventListForMain(@RequestBody @Validated SEARCH search) {
        try {
            List<EVENT> result = eventService.getEventListForMain(search);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_EVENT_LIST_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_EVENT_LIST_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_EVENT_LIST_FAIL", null);
        }
    }

    @RequestMapping(value="/getMarket", method= RequestMethod.POST)
    public ResponseOverlays getMarket(@RequestBody @Validated MARKET market) {
        try {
            logger.debug(market.toString());
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

    @RequestMapping(value="/getMarketInfoWithLatLon", method= RequestMethod.POST)
    public ResponseOverlays getMarketInfoWithLatLon(@RequestBody @Validated SEARCH search) {
        try {
            logger.debug(search.toString());
            MARKET result = marketService.getMarketInfoWithLatLon(search);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_MARKET_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MARKET_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MARKET_FAIL", null);
        }
    }

    @RequestMapping(value="/getMarketListBySearch", method= RequestMethod.POST)
    public ResponseOverlays getMarketListBySearch(@RequestBody @Validated SEARCH search) {
        try {
            List<MARKET> result = marketService.getMarketListBySearch(search);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_MARKET_LIST_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MARKET_LIST_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MARKET_LIST_FAIL", null);
        }
    }

    @RequestMapping(value="/getEvent", method= RequestMethod.POST)
    public ResponseOverlays getEvent(@RequestBody @Validated EVENT event) {
        try {
            logger.debug(event.toString());
            EVENT result = eventService.getEvent(event);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_EVENT_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_EVENT_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_EVENT_FAIL", null);
        }
    }

    @RequestMapping(value="/getEventList", method= RequestMethod.POST)
    public ResponseOverlays getEventList() {
        try {
            List<EVENT> result = eventService.getEventList();
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_EVENT_LIST_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_EVENT_LIST_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_EVENT_LIST_FAIL", null);
        }
    }
}