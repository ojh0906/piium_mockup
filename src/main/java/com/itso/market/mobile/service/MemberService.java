package com.itso.market.mobile.service;

import com.itso.market.mobile.dao.MemberDao;
import com.itso.market.mobile.model.MEMBER;
import com.itso.market.mobile.model.SEARCH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {

    @Autowired
    MemberDao memberDao;

    // 회원 키값으로 조회
    public MEMBER getMember(MEMBER member) {
        return memberDao.getMember(member);
    }

    // 회원 저장
    public int saveMember(MEMBER member){
        return memberDao.insertMember(member);
    }

    // 회원 수정
    public int modifyMember(MEMBER member){
        return memberDao.updateMember(member);
    }

    // 회원 삭제
    public int deleteMember(MEMBER member){
        return memberDao.deleteMember(member);
    }

    // 중복 아이디 체크
    public MEMBER getIdCheck(MEMBER member) {
        return memberDao.getIdCheck(member);
    }

    // 회원 리스트 조회
    public List<MEMBER> getMemberList(){
        return memberDao.getMemberList();
    }

    // 회원 리스트 조회 검색용
    public List<MEMBER> getMemberSearchList(SEARCH search){
        return memberDao.getMemberSearchList(search);
    }

    public List<MEMBER> getMemberListForPaging(SEARCH search){
        return memberDao.getMemberListForPaging(search);
    }
    public int getMemberListForPagingCnt(SEARCH search){
        return memberDao.getMemberListForPagingCnt(search);
    }
}
