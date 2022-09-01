package com.itso.market.mobile.service;

import com.itso.market.mobile.dao.MarketDao;
import com.itso.market.mobile.dao.MemberDao;
import com.itso.market.mobile.dao.StoreDao;
import com.itso.market.mobile.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StoreService {

    @Autowired
    StoreDao storeDao;

    @Autowired
    MemberDao memberDao;

    @Autowired
    MarketDao marketDao;

    // 가게 키값으로 조회
    public STORE getStore(STORE store) {
        STORE result = storeDao.getStore(store);
        if(result == null){
            return null;
        } else {
            // category, workday, file info
            result.setCategories(storeDao.getCategoriesInStore(store));
            result.setWorkdays(storeDao.getWorkdaysInStore(store));
            result.setFiles(storeDao.getFilesInStore(store));
            return result;
        }
    }

    // 가게 키값으로 조회
    public STORE getStoreByMember(MEMBER member) {
        STORE result = storeDao.getStoreByMember(member);
        if(result == null){
            return null;
        } else {
            // category, workday info
            result.setCategories(storeDao.getCategoriesInStore(result));
            result.setWorkdays(storeDao.getWorkdaysInStore(result));
            return result;
        }
    }

    // 가게 저장
    public int saveStore(STORE store){
        // 로그인한 회원 정보
        MEMBER param = new MEMBER();
        param.setId(store.getRegId());
        MEMBER member = memberDao.getIdCheck(param);

        store.setMember(member.getMember());

        int result = storeDao.insertStore(store);

        // 카테고리 정보 저장
        for(CATEGORY category : store.getCategories()){
            category.setStore(store.getStore());
            category.setRegId(store.getRegId());
            category.setChnId(store.getChnId());
        }

        // 영업일 정보 저장
        List<WORKDAY> workdays = new ArrayList<>();
        for(int i=1; i<=7; i++){ //i=1 월
            WORKDAY workday = new WORKDAY();
            workday.setType(i+"");
            workday.setDelYn("Y");
            workday.setStore(store.getStore());
            workday.setRegId(store.getRegId());
            workday.setChnId(store.getChnId());
            workdays.add(workday);
        }

        for(WORKDAY workday : store.getWorkdays()){
            switch (workday.getType()){
                case "1": workdays.get(0).setDelYn("N"); break;
                case "2": workdays.get(1).setDelYn("N"); break;
                case "3": workdays.get(2).setDelYn("N"); break;
                case "4": workdays.get(3).setDelYn("N"); break;
                case "5": workdays.get(4).setDelYn("N"); break;
                case "6": workdays.get(5).setDelYn("N"); break;
                case "7": workdays.get(6).setDelYn("N"); break;
            }
        }
        store.setWorkdays(workdays);
        storeDao.insertCategoriesInStore(store);
        storeDao.insertWorkdaysInStore(store);

        if(store.getFiles() != null && store.getFiles().size() != 0){
            for(FILE file : store.getFiles()){ // 신규 파일 저장
                file.setStore(store.getStore());
                file.setRegId(store.getChnId());
                file.setChnId(store.getChnId());
            }

            storeDao.insertFilesInStore(store);
        }
        return result;
    }

    // 가게 수정
    public int modifyStore(STORE store){
        int result = storeDao.updateStore(store);

        // 카테고리 정보 수정
        storeDao.deleteSCategory(store);
        for(CATEGORY category : store.getCategories()){
            category.setStore(store.getStore());
            category.setRegId(store.getChnId());
            category.setChnId(store.getChnId());
            if(storeDao.getSCategory(category) == null){ // 신규
                storeDao.insertSCategory(category);
            } else { // 업데이트
                storeDao.updateSCategory(category);
            }
        }

        // 영업일 정보 MERGE
        storeDao.deleteWorkday(store);
        for(WORKDAY workday : store.getWorkdays()){ // 영업일은 기존에 이미 다 생성했기때문에 업데이트만
            workday.setStore(store.getStore());
            workday.setChnId(store.getChnId());
            storeDao.updateWorkday(workday);
        }

        if(store.getFiles() != null && store.getFiles().size() != 0){
            for(FILE file : store.getFiles()){ // 신규 파일 저장
                file.setStore(store.getStore());
                file.setRegId(store.getChnId());
                file.setChnId(store.getChnId());
            }

            storeDao.insertFilesInStore(store);
        }

        if(store.getUpdateFiles() != null && store.getUpdateFiles().size() != 0){
            storeDao.updateFilesInStore(store);
            for(FILE file : store.getUpdateFiles()){ // 신규 파일 저장
                file.setChnId(store.getChnId());
                storeDao.updateFile(file);
            }
        }

        return result;
    }

    // 가게 삭제
    public int deleteStore(STORE store){
        return storeDao.deleteStore(store);
    }

    // 추천 가게 리스트
    public List<STORE> getRecStoreList(CATEGORY category) {
        List<STORE> storeList = storeDao.getRecStoreList(category);
        for(STORE store : storeList){ // 신규 파일 저장
            store.setFiles(storeDao.getFilesInStore(store));
        }
        return storeList;
    }

    // 인기 가게 리스트
    public List<STORE> getPopStoreList(CATEGORY category) {
        List<STORE> storeList = storeDao.getPopStoreList(category);
        for(STORE store : storeList){ // 신규 파일 저장
            store.setFiles(storeDao.getFilesInStore(store));
        }
        return storeList;
    }

    // 카테고리 리스트
    public List<CATEGORY> getCategoryList() {
        return storeDao.getCategoryList();
    }

    // 가게 정보 리스트
    public List<STORE> getStoreList() {
        return storeDao.getStoreList();
    }

    // 가게 정보 리스트
    public List<STORE> getStoreListByCategory(SEARCH search) {
        // 카테고리별 시장 조회
        List<STORE> storeList = storeDao.getStoreListByCategory(search);
        for(STORE store : storeList){
            store.setFiles(storeDao.getFilesInStore(store));
        }
        return  storeList;
    }

    // 가게 정보 리스트 검색용
    public List<STORE> getStoreSearchList(SEARCH search) {
        List<STORE> storeList = storeDao.getStoreSearchList(search);
        return  storeList;
    }

    // 파일 삭제
    public int deleteFileInStore(FILE file){
        return storeDao.deleteFileInStore(file);
    }

    public int updateRecStore(STORE store){
        return storeDao.updateRecStore(store);
    }

    public int updatePopStore(STORE store){
        return storeDao.updatePopStore(store);
    }

    public int updateRecPopStore(STORE store){
        return storeDao.updateRecPopStore(store);
    }

    public int updateNoRecPopStore(STORE store){
        return storeDao.updateNoRecPopStore(store);
    }

    public int countUpVisitor(STORE store){
        return storeDao.countUpVisitor(store);
    }

    public List<STORE> getStoreListForPaging(SEARCH search){
        return storeDao.getStoreListForPaging(search);
    }
    public int getStoreListForPagingCnt(SEARCH search){
        return storeDao.getStoreListForPagingCnt(search);
    }
}
