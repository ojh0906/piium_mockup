package com.itso.market.mobile.service;

import com.itso.market.mobile.dao.CategoryDao;
import com.itso.market.mobile.dao.NoticeDao;
import com.itso.market.mobile.model.CATEGORY;
import com.itso.market.mobile.model.NOTICE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    // 회원 키값으로 조회
    public CATEGORY getCategory(CATEGORY category) {
        return categoryDao.getCategory(category);
    }

    // 회원 저장
    public int saveCategory(CATEGORY category){
        return categoryDao.insertCategory(category);
    }

    // 회원 수정
    public int modifyCategory(CATEGORY category){
        return categoryDao.updateCategory(category);
    }

    // 회원 삭제
    public int deleteCategory(CATEGORY category){
        int result = categoryDao.deleteCategory(category);

        if(result > 0){ // 해당카테고리를 가진 시장과 가게 수정
            categoryDao.deleteMCategory(category);
            categoryDao.deleteSCategory(category);
        }
        return result;
    }

    public List<CATEGORY> getCategoryList() {
        return categoryDao.getCategoryList();
    }
}
