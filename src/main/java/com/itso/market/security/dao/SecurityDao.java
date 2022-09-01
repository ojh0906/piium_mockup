package com.itso.market.security.dao;

import com.itso.market.mobile.model.MEMBER;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SecurityDao {
    MEMBER getMemberForLogin(String id);
}
