package com.itso.market.mobile.dao;

import com.itso.market.mobile.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EventDao {
    EVENT getEvent(EVENT event);
    int insertEvent(EVENT event);
    int updateEvent(EVENT event);
    int deleteEvent(EVENT event);

    List<FILE> getFilesInEvent(EVENT event);

    int insertFilesInEvent(EVENT event);

    List<EVENT> getEventList();

    int deleteFileInEvent(FILE file);
    int deleteAllFileInEvent(EVENT event);

    int registerEvent(EVENT event);

    List<MEMBER> getMemberListInEvent(EVENT event);

    List<EVENT> getEventListForMain(SEARCH search);

    List<EVENT> getEventListForPaging(SEARCH search);
    int getEventListForPagingCnt(SEARCH search);

}
