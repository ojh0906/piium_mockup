package com.itso.market.mobile.controller;

import com.itso.market.mobile.model.MEMBER;
import com.itso.market.mobile.model.STORE;
import com.itso.market.mobile.service.StoreService;
import com.itso.market.security.service.SecurityUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    SecurityUserDetailsService securityService;

    @Autowired
    StoreService storeService;

    /**********************************
     * 로그인하지 않은 사용자
     * *********************************/
    @GetMapping("/")
    public String main()
    {
        MEMBER member = securityService.getCurrentMember();

        if(member == null){
            return "index";
        } else if(member.getRole().toString().equals("ROLE_USER")){
            return "redirect:/index?mid="+member.getMember();
        } else if(member.getRole().toString().equals("ROLE_STORE")){
            STORE store = storeService.getStoreByMember(member);

                if(store != null){
                    return "redirect:/manager/home?sid="+store.getStore()+"&mid="+member.getMember();
                }
                else{
                    return "redirect:/manager/editStore?cmd=1&mid="+member.getMember();
                }

        } else if(member.getRole().toString().equals("ROLE_ADMIN")){
            return "admin/member_management";
        } else {
            return "index";
        }
    }

    @GetMapping("/index")
    public String index() {
        logger.debug("index");

        return "index";
    }

    @GetMapping("/loginTest")
    public String loginTest() {
        return "login/loginTest";
    }

    @GetMapping("/login")
    public String login() {

        MEMBER member = securityService.getCurrentMember();

        if (securityService.getCurrentId() != null) {
            return "redirect:/?mid="+member.getMember();
        } else {
            return "login/login";
        }
    }

    @GetMapping("/policy")
    public String policy(@RequestParam(value = "role", required = false) String role, Model model) {
        model.addAttribute("role", role);
        return "login/policy";
    }

    @GetMapping("/registerType")
    public String registerType() {
        return "login/register_type";
    }

    @GetMapping("/register")
    public String register(@RequestParam(value = "event", required = false) String event, @RequestParam(value = "role", required = false) String role, Model model) {
        model.addAttribute("event", event);
        model.addAttribute("role", role);
        return "login/register";
    }

    @GetMapping("/inquiry")
    public String inquiry() {
        return "login/inquiry";
    }
    @GetMapping("/notice/noticeList")
    public String noticeList() {
        return "notice/notice-list";
    }
    @GetMapping("/notice/noticeDetail")
    public String noticeDetail() {
        return "notice/notice-detail";
    }

    @GetMapping("/main/info")
    public String mainStoreInfo() {
        return "market/market-info";
    }

    @GetMapping("/main/review")
    public String mainReview() {
        return "store/write-review";
    }

    @GetMapping("/main/policy")
    public String mainPolicy() {
        return "policy";
    }

    @GetMapping("/mypage/modifyUser")
    public String modifyUser() {
        return "mypage/edit_mypage";
    }

    /**********************************
     * 사용자 로그인
     * *********************************/
    @GetMapping("/user/mypage")
    public String mypage() {
        if (securityService.getCurrentId() != null) {
            return "mypage/mypage";
        } else {
            return "login/login";
        }
    }

    @GetMapping("/user/couponList")
    public String couponList() {
        if (securityService.getCurrentId() != null) {
            return "coupon/coupon-list";
        } else {
            return "login/login";
        }
    }

    @GetMapping("/user/couponDetail")
    public String couponDetail() {
        return "coupon/coupon-detail";
    }

    @GetMapping("/market/marketDetail")
    public String marketDetail() {
        return "market/market-detail";
    }

    @GetMapping("/event/eventList")
    public String eventList() {
        return "event/event-list";
    }

    @GetMapping("/event/eventDetail")
    public String eventDetail() {
        return "event/event-detail";
    }

    @GetMapping("/market/searchCategory")
    public String categorySearch() {
        return "market/category-search";
    }

    @GetMapping("/market/searchMarket")
    public String marketSearch() {
        return "market/market-search";
    }

    /**********************************
     * 가게관리자
     * *********************************/
    @GetMapping("/manager/home")
    public String managerHome() {
        return "manager/home";
    }
    @GetMapping("/manager/myStore")
    public String managerStore() {
        return "manager/my-store";
    }
    @GetMapping("/manager/myPage")
    public String managerMypage() {
        return "manager/mypage";
    }
    @GetMapping("/manager/modifyManager")
    public String edtiManager() {
        return "manager/edit_mypage";
    }
    @GetMapping("/manager/editStore")
    public String managerEditStore() {
        return "manager/edit-store";
    }
    @GetMapping("/manager/couponList")
    public String managerCouponList() {
        return "manager/coupon-list";
    }
    @GetMapping("/manager/editCoupon")
    public String managerEditCoupon() {
        return "manager/edit-coupon";
    }
    @GetMapping("/manager/couponDetail")
    public String managerCouponDetail() {
        return "manager/coupon-detail";
    }
    @GetMapping("/manager/couponUsed")
    public String managerCouponUsed() {
        return "manager/coupon-used";
    }
    @GetMapping("/manager/productList")
    public String managerProductList() {
        return "manager/product-list";
    }
    @GetMapping("/manager/productDetail")
    public String managerProductdetail() {
        return "manager/product-detail";
    }
    @GetMapping("/manager/editProduct")
    public String managerEditProduct() {
        return "manager/edit-product";
    }
    @GetMapping("/manager/reviewList")
    public String managerReviewList() {
        return "manager/review-list";
    }



    /**********************************
     * 가게관리자 로그인
     * *********************************/
    @GetMapping("/store/info")
    public String storeInfo() {
        return "store/market-info";
    }

    @GetMapping("/store/edit")
    public String storeEdit() {
        return "store/edit_market";
    }



    /**********************************
     * 사이트관리자 로그인
     * *********************************/
    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin/login";
    }

    @GetMapping("/admin/categoryManage")
    public String adminCategoryManage() {
        return "admin/category_management";
    }

    @GetMapping("/admin/categoryDetail")
    public String adminCategoryDetail() {
        return "admin/category_detail";
    }

    @GetMapping("/admin/noticeManage")
    public String adminNoticeManage() {
        return "admin/notice_management";
    }

    @GetMapping("/admin/noticeDetail")
    public String adminNoticeDetail() {
        return "admin/notice_detail";
    }

    @GetMapping("/admin/noticeEdit")
    public String adminNoticeEdit() {
        return "admin/edit_notice";
    }

    @GetMapping("/admin/marketManage")
    public String adminMarketManage() {
        return "admin/market_management";
    }

    @GetMapping("/admin/marketDetail")
    public String adminMarketDetail() {
        return "admin/market_detail";
    }

    @GetMapping("/admin/storeManage")
    public String adminStoreManage() {
        return "admin/store_management";
    }

    @GetMapping("/admin/storeDetail")
    public String adminStoreDetail() {
        return "admin/store_detail";
    }

    @GetMapping("/admin/memberManage")
    public String adminMemberManage() {
        return "admin/member_management";
    }

    @GetMapping("/admin/memberDetail")
    public String adminMemberDetail() {
        return "admin/member_detail";
    }

    @GetMapping("/admin/siteManage")
    public String adminSiteManage() {
        return "admin/site_management";
    }

    @GetMapping("/admin/eventManage")
    public String adminEventManage() {
        return "admin/event_management";
    }

    @GetMapping("/admin/eventDetail")
    public String adminEventDetail() {
        return "admin/event_detail";
    }

    @GetMapping("/admin/eventParticipants")
    public String adminEventParticipants() {
        return "admin/event_participants";
    }


    /**********************************
     * TEST
     * *********************************/
    @GetMapping("/testForLocation")
    public String testForLocation() {
        return "testForLocation";
    }
}
