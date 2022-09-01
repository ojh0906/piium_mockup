package com.itso.market.mobile.dao;

import com.itso.market.mobile.model.MARKET;
import com.itso.market.mobile.model.MEMBER;
import com.itso.market.mobile.model.SEARCH;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberDao {
    MEMBER getMember(MEMBER member);
    int insertMember(MEMBER member);
    int updateMember(MEMBER member);
    int deleteMember(MEMBER member);
    MEMBER getIdCheck(MEMBER member);
    List<MEMBER> getMemberList();
    List<MEMBER> getMemberSearchList(SEARCH search);
    List<MEMBER> getMemberListForPaging(SEARCH search);
    int getMemberListForPagingCnt(SEARCH search);
}
