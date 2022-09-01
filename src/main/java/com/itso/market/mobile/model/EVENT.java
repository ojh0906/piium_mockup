package com.itso.market.mobile.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class EVENT {
    private Integer event;
    private Integer market;
    private Integer member;
    private String bgndt;
    private String nddt;
    private String title;
    private String contents;
    private String delYn;
    private Date regDate;
    private String regId;
    private Date chnDate;
    private String chnId;

    private Integer tot;

    List<FILE> files;

    private String mname;
}
