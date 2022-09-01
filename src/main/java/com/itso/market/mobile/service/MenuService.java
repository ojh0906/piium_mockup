package com.itso.market.mobile.service;

import com.itso.market.mobile.dao.MemberDao;
import com.itso.market.mobile.dao.MenuDao;
import com.itso.market.mobile.dao.StoreDao;
import com.itso.market.mobile.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MenuService {

    @Autowired
    MenuDao menuDao;

    // 키값으로 조회
    public MENU getMenu(MENU menu) {
        MENU result = menuDao.getMenu(menu);
        if(result == null){
            return null;
        } else {
            result.setFiles(menuDao.getFilesInMenu(menu));
            return result;
        }
    }

    // 저장
    public int saveMenu(MENU menu){
        int result = menuDao.insertMenu(menu);
        if(menu.getFiles() != null && menu.getFiles().size() != 0) {
            for (FILE file : menu.getFiles()) { // 신규 파일 저장
                file.setMenu(menu.getMenu());
                file.setRegId(menu.getChnId());
                file.setChnId(menu.getChnId());
            }

            menuDao.insertFilesInMenu(menu);
        }
        return result;
    }

    // 수정
    public int modifyMenu(MENU menu){
        int result = menuDao.updateMenu(menu);

        if(menu.getFiles() != null && menu.getFiles().size() != 0) {
            menuDao.deleteFilesInMenu(menu);
            for (FILE file : menu.getFiles()) { // 신규 파일 저장
                file.setMenu(menu.getMenu());
                file.setRegId(menu.getChnId());
                file.setChnId(menu.getChnId());
            }
            menuDao.insertFilesInMenu(menu);
        }
        return result;
    }

    // 삭제
    public int deleteMenu(MENU menu){
        return menuDao.deleteMenu(menu);
    }

    // 가게 정보 리스트
    public List<MENU> getMenuListByStore(MENU menu) {
        List<MENU> resultList = menuDao.getMenuListByStore(menu);

        for (MENU result : resultList) { // 신규 파일 저장
            result.setFiles(menuDao.getFilesInMenu(result));
        }
        return resultList;
    }

    public int deleteFileInMenu(FILE file){
        return menuDao.deleteFileInMenu(file);
    }

}
