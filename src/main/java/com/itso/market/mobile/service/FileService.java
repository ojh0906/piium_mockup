package com.itso.market.mobile.service;

import com.itso.market.mobile.dao.FileDao;
import com.itso.market.mobile.dao.NoticeDao;
import com.itso.market.mobile.model.CATEGORY;
import com.itso.market.mobile.model.FILE;
import com.itso.market.mobile.model.NOTICE;
import com.itso.market.mobile.model.STORE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FileService {

    @Autowired
    FileDao fileDao;

    public List<FILE> getBannerList(FILE file) {
        return fileDao.getBannerList(file);
    }

    public int deleteBanner(FILE file){
        return fileDao.deleteBanner(file);
    }

    public int modifyBanner(STORE store){
        if(store.getFiles() != null && store.getFiles().size() != 0){
            for(FILE file : store.getFiles()){ // 신규 파일 저장
                file.setRegId(store.getChnId());
                file.setChnId(store.getChnId());
            }

            fileDao.insertBanner(store);
        }
        return store.getFiles().size();
    }

}
