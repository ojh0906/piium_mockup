package com.itso.market.mobile.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class FILE {
    private Integer sfile;
    private Integer mfile;
    private Integer mrfile;
    private Integer efile;
    private Integer banner;
    private Integer market;
    private Integer store;
    private Integer menu;
    private Integer event;
    private String name;
    private String uuid;
    private String path;
    private String type;
    private String delYn;
    private Date regDate;
    private String regId;
    private Date chnDate;
    private String chnId;
}
