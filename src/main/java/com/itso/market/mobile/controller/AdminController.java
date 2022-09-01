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
@RequestMapping("/admin/*")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    StoreService storeService;

    @Autowired
    MemberService memberService;

    @Autowired
    NoticeService noticeService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    FileService fileService;

    @Autowired
    MarketService marketService;

    @Autowired
    EventService eventService;

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

    @RequestMapping(value="/getStoreList", method= RequestMethod.POST)
    public ResponseOverlays getStoreList() {
        try {
            List<STORE> result = storeService.getStoreList();
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_STORE_LIST_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_STORE_LIST_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_STORE_LIST_FAIL", null);
        }
    }

    @RequestMapping(value="/getMember", method= RequestMethod.POST)
    public ResponseOverlays getMember(@RequestBody @Validated MEMBER member) {
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

    @RequestMapping(value="/saveMember", method= RequestMethod.POST)
    public ResponseOverlays saveMember(@RequestBody @Validated MEMBER member) {
        try {
            logger.debug(member.toString());
            String regId = securityUserDetailsService.getCurrentId();
            member.setRegId(regId);
            member.setChnId(regId);
            int result = memberService.saveMember(member);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_MEMBER_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "SAVE_MEMBER_SUCCESS", member);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_MEMBER_FAIL", null);
        }
    }

    @RequestMapping(value="/modifyMember", method= RequestMethod.POST)
    public ResponseOverlays modifyMember(@RequestBody @Validated MEMBER member) {
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

    @RequestMapping(value="/deleteMember", method= RequestMethod.POST)
    public ResponseOverlays deleteMember(@RequestBody @Validated MEMBER member) {
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

    @RequestMapping(value="/getMemberList", method= RequestMethod.POST)
    public ResponseOverlays getMemberList() {
        try {
            List<MEMBER> result = memberService.getMemberList();
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_MEMBER_LIST_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MEMBER_LIST_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MEMBER_LIST_FAIL", null);
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

    @RequestMapping(value="/saveNotice", method= RequestMethod.POST)
    public ResponseOverlays saveNotice(@RequestBody @Validated NOTICE notice) {
        try {
            logger.debug(notice.toString());
            String regId = securityUserDetailsService.getCurrentId();
            notice.setRegId(regId);
            notice.setChnId(regId);
            int result = noticeService.saveNotice(notice);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_NOTICE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "SAVE_NOTICE_SUCCESS", notice);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_NOTICE_FAIL", null);
        }
    }

    @RequestMapping(value="/modifyNotice", method= RequestMethod.POST)
    public ResponseOverlays modifyNotice(@RequestBody @Validated NOTICE notice) {
        try {
            logger.debug(notice.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            notice.setChnId(chnId);
            int result = noticeService.modifyNotice(notice);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_NOTICE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "MODIFY_NOTICE_SUCCESS", notice);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_NOTICE_FAIL", null);
        }
    }

    @RequestMapping(value="/deleteNotice", method= RequestMethod.POST)
    public ResponseOverlays deleteNotice(@RequestBody @Validated NOTICE notice) {
        try {
            logger.debug(notice.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            notice.setChnId(chnId);
            int result = noticeService.deleteNotice(notice);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_NOTICE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "DELETE_NOTICEE_SUCCESS", notice);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_NOTICE_FAIL", null);
        }
    }

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

    @RequestMapping(value="/getStoreSearchList", method= RequestMethod.POST)
    public ResponseOverlays getStoreSearchList(@RequestBody @Validated SEARCH search) {
        try {
            List<STORE> result = storeService.getStoreSearchList(search);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_SEARCH_LIST_STORE_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_SEARCH_LIST_STORE_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_SEARCH_LIST_STORE_FAIL", null);
        }
    }

    @RequestMapping(value="/getMemberSearchList", method= RequestMethod.POST)
    public ResponseOverlays getMemberSearchList(@RequestBody @Validated SEARCH search) {
        try {
            List<MEMBER> result = memberService.getMemberSearchList(search);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_SEARCH_LIST_STORE_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_SEARCH_LIST_STORE_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_SEARCH_LIST_STORE_FAIL", null);
        }
    }

    @RequestMapping(value="/getCategory", method= RequestMethod.POST)
    public ResponseOverlays getCategory(@RequestBody @Validated CATEGORY category) {
        try {
            logger.debug(category.toString());
            CATEGORY result = categoryService.getCategory(category);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_CATEGORY_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_CATEGORY_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_CATEGORY_FAIL", null);
        }
    }

    @RequestMapping(value="/saveCategory", method= RequestMethod.POST)
    public ResponseOverlays saveCategory(@RequestBody @Validated CATEGORY category) {
        try {
            logger.debug(category.toString());
            String regId = securityUserDetailsService.getCurrentId();
            category.setRegId(regId);
            category.setChnId(regId);
            int result = categoryService.saveCategory(category);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_CATEGORY_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "SAVE_CATEGORY_SUCCESS", category);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_CATEGORY_FAIL", null);
        }
    }

    @RequestMapping(value="/modifyCategory", method= RequestMethod.POST)
    public ResponseOverlays modifyCategory(@RequestBody @Validated CATEGORY category) {
        try {
            logger.debug(category.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            category.setChnId(chnId);
            int result = categoryService.modifyCategory(category);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_CATEGORY_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "MODIFY_CATEGORY_SUCCESS", category);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_CATEGORY_FAIL", null);
        }
    }

    @RequestMapping(value="/deleteCategory", method= RequestMethod.POST)
    public ResponseOverlays deleteCategory(@RequestBody @Validated CATEGORY category) {
        try {
            logger.debug(category.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            category.setChnId(chnId);
            int result = categoryService.deleteCategory(category);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_CATEGORY_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "DELETE_CATEGORY_SUCCESS", category);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_CATEGORY_FAIL", null);
        }
    }

    @RequestMapping(value="/getCategoryList", method= RequestMethod.POST)
    public ResponseOverlays getCategoryList() {
        try {
            List<CATEGORY> result = categoryService.getCategoryList();
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_LIST_CATEGORY_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_CATEGORY_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_CATEGORY_FAIL", null);
        }
    }

    @RequestMapping(value="/updateRecStore", method= RequestMethod.POST)
    public ResponseOverlays updateRecStore(@RequestBody @Validated STORE store) {
        try {
            logger.debug(store.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            store.setChnId(chnId);
            int result = storeService.updateRecStore(store);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "UPDATE_REC_STORE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "UPDATE_REC_STORE_SUCCESS", store);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "UPDATE_REC_STORE_FAIL", null);
        }
    }

    @RequestMapping(value="/updatePopStore", method= RequestMethod.POST)
    public ResponseOverlays updatePopStore(@RequestBody @Validated STORE store) {
        try {
            logger.debug(store.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            store.setChnId(chnId);
            int result = storeService.updatePopStore(store);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "UPDATE_POP_STORE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "UPDATE_POP_STORE_SUCCESS", store);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "UPDATE_POP_STORE_FAIL", null);
        }
    }

    @RequestMapping(value="/updateRecPopStore", method= RequestMethod.POST)
    public ResponseOverlays updateRecPopStore(@RequestBody @Validated STORE store) {
        try {
            logger.debug(store.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            store.setChnId(chnId);
            int result = storeService.updateRecPopStore(store);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "UPDATE_REC_POP_STORE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "UPDATE_REC_POP_STORE_SUCCESS", store);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "UPDATE_REC_POP_STORE_FAIL", null);
        }
    }

    @RequestMapping(value="/updateNoRecPopStore", method= RequestMethod.POST)
    public ResponseOverlays updateNoRecPopStore(@RequestBody @Validated STORE store) {
        try {
            logger.debug(store.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            store.setChnId(chnId);
            int result = storeService.updateNoRecPopStore(store);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "UPDATE_NO_REC_POP_STORE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "UPDATE_NO_REC_POP_STORE_SUCCESS", store);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "UPDATE_NO_REC_POP_STORE_FAIL", null);
        }
    }

    @RequestMapping(value="/deleteBanner", method= RequestMethod.POST)
    public ResponseOverlays deleteBanner(@RequestBody @Validated FILE file) {
        try {
            logger.debug(file.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            file.setChnId(chnId);
            int result = fileService.deleteBanner(file);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_BANNER_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "DELETE_BANNER_SUCCESS", file);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_BANNER_FAIL", null);
        }
    }

    @RequestMapping(value="/modifyBanner", method= RequestMethod.POST)
    public ResponseOverlays modifyBanner(@RequestBody @Validated STORE store) {
        try {
            logger.debug(store.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            store.setChnId(chnId);
            int result = fileService.modifyBanner(store);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_BANNER_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "MODIFY_BANNER_SUCCESS", store);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_BANNER_FAIL", null);
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

    @RequestMapping(value="/saveMarket", method= RequestMethod.POST)
    public ResponseOverlays saveMarket(@RequestBody @Validated MARKET market) {
        try {
            logger.debug(market.toString());
            String regId = securityUserDetailsService.getCurrentId();
            market.setRegId(regId);
            market.setChnId(regId);
            int result = marketService.saveMarket(market);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_MARKET_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "SAVE_MARKET_SUCCESS", market);
            }
        } catch (Exception e){
            logger.error(e.getLocalizedMessage());
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_MARKET_FAIL", null);
        }
    }

    @RequestMapping(value="/modifyMarket", method= RequestMethod.POST)
    public ResponseOverlays modifyMarket(@RequestBody @Validated MARKET market) {
        try {
            logger.debug(market.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            market.setChnId(chnId);
            int result = marketService.modifyMarket(market);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_MARKET_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "MODIFY_MARKET_SUCCESS", market);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_MARKET_FAIL", null);
        }
    }

    @RequestMapping(value="/deleteMarket", method= RequestMethod.POST)
    public ResponseOverlays deleteMarket(@RequestBody @Validated MARKET market) {
        try {
            logger.debug(market.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            market.setChnId(chnId);
            int result = marketService.deleteMarket(market);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_MARKET_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "DELETE_MARKET_SUCCESS", market);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_MARKET_FAIL", null);
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

    @RequestMapping(value="/deleteMarketFile", method= RequestMethod.POST)
    public ResponseOverlays deleteMarketFile(@RequestBody @Validated FILE file) {
        try {
            logger.debug(file.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            file.setChnId(chnId);
            int result = marketService.deleteFileInMarket(file);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_FILE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "DELETE_FILE_SUCCESS", file);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_FILE_FAIL", null);
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

    @RequestMapping(value="/saveEvent", method= RequestMethod.POST)
    public ResponseOverlays saveEvent(@RequestBody @Validated EVENT event) {
        try {
            logger.debug(event.toString());
            String regId = securityUserDetailsService.getCurrentId();
            event.setRegId(regId);
            event.setChnId(regId);
            int result = eventService.saveEvent(event);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_EVENT_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "SAVE_EVENT_SUCCESS", event);
            }
        } catch (Exception e){
            logger.error(e.getLocalizedMessage());
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_EVENT_FAIL", null);
        }
    }

    @RequestMapping(value="/modifyEvent", method= RequestMethod.POST)
    public ResponseOverlays modifyEvent(@RequestBody @Validated EVENT event) {
        try {
            logger.debug(event.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            event.setChnId(chnId);
            int result = eventService.modifyEvent(event);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_EVENT_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "MODIFY_EVENT_SUCCESS", event);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "MODIFY_EVENT_FAIL", null);
        }
    }

    @RequestMapping(value="/deleteEvent", method= RequestMethod.POST)
    public ResponseOverlays deleteEvent(@RequestBody @Validated EVENT event) {
        try {
            logger.debug(event.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            event.setChnId(chnId);
            int result = eventService.deleteEvent(event);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_EVENT_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "DELETE_EVENT_SUCCESS", event);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_EVENT_FAIL", null);
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

    @RequestMapping(value="/deleteEventFile", method= RequestMethod.POST)
    public ResponseOverlays deleteEventFile(@RequestBody @Validated FILE file) {
        try {
            logger.debug(file.toString());
            String chnId = securityUserDetailsService.getCurrentId();
            file.setChnId(chnId);
            int result = eventService.deleteFileInEvent(file);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_FILE_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "DELETE_FILE_SUCCESS", file);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DELETE_FILE_FAIL", null);
        }
    }

    @RequestMapping(value="/getMemberListInEvent", method= RequestMethod.POST)
    public ResponseOverlays getMemberListInEvent(@RequestBody @Validated EVENT event) {
        try {
            List<MEMBER> result = eventService.getMemberListInEvent(event);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_MEMBER_LIST_SUCCESS", result);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MEMBER_LIST_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MEMBER_LIST_FAIL", null);
        }
    }

    @RequestMapping(value="/getMarketListForPaging", method= RequestMethod.POST)
    public ResponseOverlays getMarketListForPaging(@RequestBody @Validated SEARCH search) {
        try {
            search.setStartNum((search.getPage().intValue()-1)*search.getNum().intValue());
            List<MARKET> result = marketService.getMarketListForPaging(search);
            int cnt = marketService.getMarketListForPagingCnt(search);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_MARKET_LIST_SUCCESS", result, cnt);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MARKET_LIST_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MARKET_LIST_FAIL", null);
        }
    }

    @RequestMapping(value="/getStoreListForPaging", method= RequestMethod.POST)
    public ResponseOverlays getStoreListForPaging(@RequestBody @Validated SEARCH search) {
        try {
            search.setStartNum((search.getPage().intValue()-1)*search.getNum().intValue());
            List<STORE> result = storeService.getStoreListForPaging(search);
            int cnt = storeService.getStoreListForPagingCnt(search);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_STORE_LIST_SUCCESS", result, cnt);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_STORE_LIST_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_STORE_LIST_FAIL", null);
        }
    }

    @RequestMapping(value="/getMemberListForPaging", method= RequestMethod.POST)
    public ResponseOverlays getMemberListForPaging(@RequestBody @Validated SEARCH search) {
        try {
            search.setStartNum((search.getPage().intValue()-1)*search.getNum().intValue());
            List<MEMBER> result = memberService.getMemberListForPaging(search);
            int cnt = memberService.getMemberListForPagingCnt(search);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_MEMBER_LIST_SUCCESS", result, cnt);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MEMBER_LIST_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_MEMBER_LIST_FAIL", null);
        }
    }

    @RequestMapping(value="/getEventListForPaging", method= RequestMethod.POST)
    public ResponseOverlays getEventListForPaging(@RequestBody @Validated SEARCH search) {
        try {
            search.setStartNum((search.getPage().intValue()-1)*search.getNum().intValue());
            List<EVENT> result = eventService.getEventListForPaging(search);
            int cnt = eventService.getEventListForPagingCnt(search);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_EVENT_LIST_SUCCESS", result, cnt);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_EVENT_LIST_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_EVENT_LIST_FAIL", null);
        }
    }

    @RequestMapping(value="/getNoticeListForPaging", method= RequestMethod.POST)
    public ResponseOverlays getNoticeListForPaging(@RequestBody @Validated SEARCH search) {
        try {
            search.setStartNum((search.getPage().intValue()-1)*search.getNum().intValue());
            List<NOTICE> result = noticeService.getNoticeListForPaging(search);
            int cnt = noticeService.getNoticeListForPagingCnt(search);
            if(result != null){
                return new ResponseOverlays(HttpServletResponse.SC_OK, "GET_LIST_NOTICE_SUCCESS", result, cnt);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_NOTICE_NULL", null);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "GET_LIST_NOTICE_FAIL", null);
        }
    }
}