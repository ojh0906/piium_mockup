package com.itso.market.mobile.dao;

import com.itso.market.mobile.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreDao {
    STORE getStore(STORE store);
    STORE getStoreByMember(MEMBER member);
    int insertStore(STORE store);
    int updateStore(STORE store);
    int deleteStore(STORE store);

    List<CATEGORY> getCategoriesInStore(STORE store);
    List<CATEGORY> getSCategoriesInStore(STORE store);
    List<WORKDAY> getWorkdaysInStore(STORE store);
    List<FILE> getFilesInStore(STORE store);

    int insertCategoriesInStore(STORE store);
    int insertWorkdaysInStore(STORE store);
    int insertFilesInStore(STORE store);
    int updateFilesInStore(STORE store);
    int updateFile(FILE file);

    CATEGORY getSCategory(CATEGORY category);
    int insertSCategory(CATEGORY category);
    int updateSCategory(CATEGORY category);
    int deleteSCategory(STORE store);

    int updateWorkday(WORKDAY workday);
    int deleteWorkday(STORE store);

    List<STORE> getRecStoreList(CATEGORY category);
    List<STORE> getPopStoreList(CATEGORY category);
    List<CATEGORY> getCategoryList();

    List<STORE> getStoreList();
    List<STORE> getStoreListByCategory(SEARCH search);
    List<STORE> getStoreSearchList(SEARCH search);

    int deleteFileInStore(FILE file);

    int updateRecStore(STORE store);
    int updatePopStore(STORE store);
    int updateRecPopStore(STORE store);
    int updateNoRecPopStore(STORE store);

    int countUpVisitor(STORE store);

    List<STORE> getStoreListForPaging(SEARCH search);
    int getStoreListForPagingCnt(SEARCH search);

}
