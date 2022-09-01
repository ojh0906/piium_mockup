package com.itso.market.mobile.service;

import com.itso.market.mobile.dao.EventDao;
import com.itso.market.mobile.dao.MarketDao;
import com.itso.market.mobile.dao.MemberDao;
import com.itso.market.mobile.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventService {

    @Autowired
    EventDao eventDao;

    // 키값으로 조회
    public EVENT getEvent(EVENT event) {
        EVENT result = eventDao.getEvent(event);
        if(result == null){
            return null;
        } else {
            result.setFiles(eventDao.getFilesInEvent(event));
            return result;
        }
    }

    // 저장
    public int saveEvent(EVENT event){
        int result = eventDao.insertEvent(event);

        if(event.getFiles() != null && event.getFiles().size() != 0){
            for(FILE file : event.getFiles()){ // 신규 파일 저장
                file.setEvent(event.getEvent());
                file.setRegId(event.getChnId());
                file.setChnId(event.getChnId());
            }

            eventDao.insertFilesInEvent(event);
        }
        return result;
    }

    // 수정
    public int modifyEvent(EVENT event){
        int result = eventDao.updateEvent(event);

        if(event.getFiles() != null && event.getFiles().size() != 0){

            eventDao.deleteAllFileInEvent(event);
            for(FILE file : event.getFiles()){ // 신규 파일 저장
                file.setEvent(event.getEvent());
                file.setRegId(event.getChnId());
                file.setChnId(event.getChnId());
            }

            eventDao.insertFilesInEvent(event);
        }

        return result;
    }

    // 가게 삭제
    public int deleteEvent(EVENT event){
        return eventDao.deleteEvent(event);
    }

    // 리스트
    public List<EVENT> getEventList() {
        List<EVENT> list = eventDao.getEventList();
        for(EVENT event : list){
            event.setFiles(eventDao.getFilesInEvent(event));
        }
        return list;
    }

    public int deleteFileInEvent(FILE file){
        return eventDao.deleteFileInEvent(file);
    }

    public int registerEvent(EVENT event){
        return eventDao.registerEvent(event);
    }

    public List<MEMBER> getMemberListInEvent(EVENT event) {
        return eventDao.getMemberListInEvent(event);
    }

    public List<EVENT> getEventListForMain(SEARCH search) {
        List<EVENT> list = eventDao.getEventListForMain(search);

        for(EVENT event : list){
            event.setFiles(eventDao.getFilesInEvent(event));
        }
        return list;
    }

    public List<EVENT> getEventListForPaging(SEARCH search){
        return eventDao.getEventListForPaging(search);
    }
    public int getEventListForPagingCnt(SEARCH search){
        return eventDao.getEventListForPagingCnt(search);
    }
}
