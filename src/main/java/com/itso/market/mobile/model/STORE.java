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
public class STORE {
    private Integer store;
    private Integer member;
    private Integer market;
    private String sname;
    private String mname;
    private String sthm;
    private String ndhm;
    private String holidayInfo;
    private String sinfo;
    private String sphone;
    private String address;
    private String kakao;
    private String recYn;
    private String popYn;
    private Integer visit;
    private String delYn;
    private Date regDate;
    private String regId;
    private Date chnDate;
    private String chnId;

    List<CATEGORY> categories;
    List<WORKDAY> workdays;
    List<FILE> files;
    List<FILE> updateFiles;

    // 리뷰관련
    private Double tot;
    private Double avg;
}
