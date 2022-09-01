package com.itso.market.mobile.dao;

import com.itso.market.mobile.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuDao {
    MENU getMenu(MENU menu);
    int insertMenu(MENU menu);
    int updateMenu(MENU menu);
    int deleteMenu(MENU menu);
    List<MENU> getMenuListByStore(MENU menu);
    List<FILE> getFilesInMenu(MENU menu);

    int insertFilesInMenu(MENU memu);
    int deleteFilesInMenu(MENU memu);

    int deleteFileInMenu(FILE file);

}
