package com.itso.market.security.service;

import com.itso.market.mobile.model.MEMBER;
import com.itso.market.security.dao.SecurityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUserDetailsService.class);

    @Autowired
    private SecurityDao securityDao;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        logger.debug("Try login.....::::" + id);
        MEMBER member = securityDao.getMemberForLogin(id);

        if(member == null){
            throw new UsernameNotFoundException(id+" ::: 사용자 없음");
        } else {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(member.getRole().toString()));

            return new User(member.getId(),member.getPw(),authorities);
        }
    }

    public String getCurrentId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            return user.getUsername();
        } catch (Exception e){
            return null;
        }
    }

    public MEMBER getCurrentMember() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            MEMBER member = securityDao.getMemberForLogin(user.getUsername());

            return member;
        } catch (Exception e){
            return null;
        }
    }

/*
    @Transactional
    @Override
    public Integer save(MemberTO memberTO) {
        Member member = memberTO.toEntity();
        member.setLastAccessDt(LocalDateTime.now());
        member.setRegDt(LocalDateTime.now());

        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberDao.save(member).getId();
    }
*/

}
