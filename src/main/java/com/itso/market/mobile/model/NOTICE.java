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
public class NOTICE {
    private Integer notice;
    private Integer pre_notice;
    private Integer next_notice;
    private String title;
    private String contents;
    private String showYn;
    private String delYn;
    private Date regDate;
    private String regId;
    private Date chnDate;
    private String chnId;
}
