package com.itso.market.mobile.service;

import com.itso.market.mobile.dao.MarketDao;
import com.itso.market.mobile.dao.MemberDao;
import com.itso.market.mobile.dao.MarketDao;
import com.itso.market.mobile.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MarketService {

    @Autowired
    MarketDao marketDao;

    @Autowired
    MemberDao memberDao;

    // 키값으로 조회
    public MARKET getMarket(MARKET market) {
        MARKET result = marketDao.getMarket(market);
        if(result == null){
            return null;
        } else {
            result.setCategories(marketDao.getCategoriesInMarket(market));
            result.setFiles(marketDao.getFilesInMarket(market));
            return result;
        }
    }

    // 저장
    public int saveMarket(MARKET market){
        int result = marketDao.insertMarket(market);

        // 카테고리 정보 저장
        for(CATEGORY category : market.getCategories()){
            category.setMarket(market.getMarket());
            category.setRegId(market.getRegId());
            category.setChnId(market.getChnId());
        }
        marketDao.insertCategoriesInMarket(market);

        if(market.getFiles() != null && market.getFiles().size() != 0){
            for(FILE file : market.getFiles()){ // 신규 파일 저장
                file.setMarket(market.getMarket());
                file.setRegId(market.getChnId());
                file.setChnId(market.getChnId());
            }

            marketDao.insertFilesInMarket(market);
        }
        return result;
    }

    // 수정
    public int modifyMarket(MARKET market){
        int result = marketDao.updateMarket(market);

        // 카테고리 정보 수정
        marketDao.deleteMCategory(market);
        for(CATEGORY category : market.getCategories()){
            category.setMarket(market.getMarket());
            category.setRegId(market.getChnId());
            category.setChnId(market.getChnId());
            if(marketDao.getMCategory(category) == null){ // 신규
                marketDao.insertMCategory(category);
            } else { // 업데이트
                marketDao.updateMCategory(category);
            }
        }

        if(market.getFiles() != null && market.getFiles().size() != 0){
            for(FILE file : market.getFiles()){ // 신규 파일 저장
                file.setMarket(market.getMarket());
                file.setRegId(market.getChnId());
                file.setChnId(market.getChnId());
            }

            marketDao.insertFilesInMarket(market);
        }

        return result;
    }

    // 가게 삭제
    public int deleteMarket(MARKET market){
        return marketDao.deleteMarket(market);
    }

    // 리스트
    public List<MARKET> getMarketList() {
        List<MARKET> list = marketDao.getMarketList();

/*
        for(MARKET market : list){
            market.setCategories(marketDao.getCategoriesInMarket(market));
        }
*/

        return list;
    }

    public int deleteFileInMarket(FILE file){
        return marketDao.deleteFileInMarket(file);
    }

    public MARKET getMarketInfoWithLatLon(SEARCH search) {
        return marketDao.getMarketInfoWithLatLon(search);
    }

    public List<MARKET> getMarketListBySearch(SEARCH search) {
        return marketDao.getMarketListBySearch(search);
    }

    public List<MARKET> getMarketListForPaging(SEARCH search) {
        return marketDao.getMarketListForPaging(search);
    }

    public int getMarketListForPagingCnt(SEARCH search) {
        return marketDao.getMarketListForPagingCnt(search);
    }
}
