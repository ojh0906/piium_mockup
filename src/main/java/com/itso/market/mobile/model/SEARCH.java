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
public class SEARCH {
    private Integer category;
    private Integer market;
    @Enumerated(EnumType.STRING)
    private ROLE role;
    private String searchType;
    private String orderType;
    private String keyword;
    private Double lat;
    private Double lon;

    private Integer page;
    private Integer startNum;
    private Integer num;
}
