package com.itso.market.mobile.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class REVIEW {
    private Integer store;
    private Integer member;
    private Float rate;
    private String contents;
    private String delYn;
    private Date regDate;
    private String regId;
    private Date chnDate;
    private String chnId;

    private String id;
    private String name;
}
