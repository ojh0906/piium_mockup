package com.itso.market.mobile.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class MENU {
    private Integer menu;
    private Integer store;
    private String mname;
    private String minfo;
    private String pricedt;
    private String price;
    private String delYn;
    private Date regDate;
    private String regId;
    private Date chnDate;
    private String chnId;

    List<FILE> files;
}
