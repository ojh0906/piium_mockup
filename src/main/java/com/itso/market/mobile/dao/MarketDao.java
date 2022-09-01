package com.itso.market.mobile.dao;

import com.itso.market.mobile.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MarketDao {
    MARKET getMarket(MARKET Market);
    int insertMarket(MARKET Market);
    int updateMarket(MARKET Market);
    int deleteMarket(MARKET Market);

    List<CATEGORY> getCategoriesInMarket(MARKET Market);
    List<FILE> getFilesInMarket(MARKET Market);

    int insertCategoriesInMarket(MARKET Market);
    int insertFilesInMarket(MARKET Market);

    CATEGORY getMCategory(CATEGORY category);
    int insertMCategory(CATEGORY category);
    int updateMCategory(CATEGORY category);
    int deleteMCategory(MARKET Market);

    List<MARKET> getMarketList();

    int deleteFileInMarket(FILE file);

    MARKET getMarketInfoWithLatLon(SEARCH search);

    List<MARKET> getMarketListBySearch(SEARCH search);

    List<MARKET> getMarketListForPaging(SEARCH search);
    int getMarketListForPagingCnt(SEARCH search);
}
