package com.itso.market.mobile.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class COUPON {
    private Integer coupon;
    private Integer store;
    private Integer member;
    private Integer mcoupon;
    private String ctype;
    private String cname;
    private String stanprice;
    private String disprice;
    private String cinfo;
    private String bgndt;
    private String nddt;
    private String delYn;
    private Date regDate;
    private String regId;
    private Date chnDate;
    private String chnId;

    private String cno;
    private String useYn;

    List<FILE> files;
}
