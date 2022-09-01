package com.itso.market.mobile.dao;

import com.itso.market.mobile.model.CATEGORY;
import com.itso.market.mobile.model.FILE;
import com.itso.market.mobile.model.NOTICE;
import com.itso.market.mobile.model.STORE;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileDao {
    List<FILE> getBannerList(FILE file);
    int deleteBanner(FILE file);
    int insertBanner(STORE store);
}
