package com.itso.market.mobile.controller;

import com.itso.market.mobile.model.MEMBER;
import com.itso.market.mobile.model.ResponseOverlays;
import com.itso.market.mobile.service.MemberService;
import com.itso.market.security.service.SecurityUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/member/*")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    MemberService memberService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    SecurityUserDetailsService securityUserDetailsService;

    @RequestMapping(value="/saveMember", method= RequestMethod.POST)
    public ResponseOverlays saveMember(@RequestBody @Validated MEMBER member) {
        try {
            logger.debug(member.toString());
            String regId = securityUserDetailsService.getCurrentId();
            member.setRegId(regId);
            member.setChnId(regId);

            // 비밀번호 암호화
            member.setPw(encoder.encode(member.getPw()));

            int result = memberService.saveMember(member);
            if(result == 0){
                return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_MEMBER_NOT_SAVE", null);
            } else {
                return new ResponseOverlays(HttpServletResponse.SC_OK, "SAVE_MEMBER_SUCCESS", member);
            }
        } catch (Exception e){
            return new ResponseOverlays(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SAVE_MEMBER_FAIL", null);
        }
    }
}