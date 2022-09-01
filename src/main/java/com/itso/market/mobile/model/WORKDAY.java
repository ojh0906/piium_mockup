package com.itso.market.mobile.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class WORKDAY {
    private Integer workday;
    private Integer store;
    private String type;
    private String delYn;
    private Date regDate;
    private String regId;
    private Date chnDate;
    private String chnId;
}
