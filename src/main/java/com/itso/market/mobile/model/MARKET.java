package com.itso.market.mobile.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class MARKET {
    private Integer market;
    private String name;
    private String manager;
    private String phone;
    private String email;
    private String address;
    private String holiday;
    private String homepage;
    private String open;
    private String park;
    private String detail;
    private String cnote;
    private String delYn;
    private Double lat;
    private Double lon;
    private Boolean hide;
    private Date regDate;
    private String regId;
    private Date chnDate;
    private String chnId;

    List<CATEGORY> categories;
    List<FILE> files;
    List<FILE> updateFiles;

    private Integer ecnt;
    private String thumb; // 썸네일 이미
}
