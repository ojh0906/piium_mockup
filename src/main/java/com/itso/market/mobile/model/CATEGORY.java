package com.itso.market.mobile.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class CATEGORY {
    private Integer category;
    private Integer store;
    private Integer market;
    private String name;
    private String delYn;
    private Date regDate;
    private String regId;
    private Date chnDate;
    private String chnId;
}
