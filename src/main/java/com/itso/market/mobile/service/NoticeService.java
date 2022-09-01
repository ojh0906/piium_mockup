package com.itso.market.mobile.service;

import com.itso.market.mobile.dao.NoticeDao;
import com.itso.market.mobile.model.MEMBER;
import com.itso.market.mobile.model.NOTICE;
import com.itso.market.mobile.model.SEARCH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NoticeService {

    @Autowired
    NoticeDao noticeDao;

    // 회원 키값으로 조회
    public NOTICE getNotice(NOTICE notice) {
        NOTICE result = noticeDao.getNotice(notice);
        NOTICE pre_result = noticeDao.getPreNotice(notice);
        NOTICE next_result = noticeDao.getNextNotice(notice);
        if(pre_result != null){
            result.setPre_notice(pre_result.getNotice());
        }
        if(next_result != null){
            result.setNext_notice(next_result.getNotice());
        }
        return result;
    }

    // 회원 저장
    public int saveNotice(NOTICE notice){
        return noticeDao.insertNotice(notice);
    }

    // 회원 수정
    public int modifyNotice(NOTICE notice){
        return noticeDao.updateNotice(notice);
    }

    // 회원 삭제
    public int deleteNotice(NOTICE notice){
        return noticeDao.deleteNotice(notice);
    }

    public List<NOTICE> getNoticeList() {
        return noticeDao.getNoticeList();
    }

    public List<NOTICE> getNoticeListForPaging(SEARCH search){
        return noticeDao.getNoticeListForPaging(search);
    }
    public int getNoticeListForPagingCnt(SEARCH search){
        return noticeDao.getNoticeListForPagingCnt(search);
    }
}
