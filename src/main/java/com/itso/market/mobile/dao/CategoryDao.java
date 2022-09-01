package com.itso.market.mobile.dao;

import com.itso.market.mobile.model.CATEGORY;
import com.itso.market.mobile.model.NOTICE;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryDao {
    CATEGORY getCategory(CATEGORY category);
    int insertCategory(CATEGORY category);
    int updateCategory(CATEGORY category);
    int deleteCategory(CATEGORY category);
    List<CATEGORY> getCategoryList();
    int deleteMCategory(CATEGORY category);
    int deleteSCategory(CATEGORY category);
}
