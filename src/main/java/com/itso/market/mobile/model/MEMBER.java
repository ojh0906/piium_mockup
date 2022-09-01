package com.itso.market.mobile.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Getter
@Setter
@ToString
public class MEMBER {
    private Integer member;
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String email;
    private String address;
    @Enumerated(EnumType.STRING)
    private ROLE role;
    private Boolean enabled;
    private String delYn;
    private String policyYn;
    private Date regDate;
    private String regId;
    private Date chnDate;
    private String chnId;
}
