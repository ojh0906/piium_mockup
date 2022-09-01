package com.itso.market.mobile.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseOverlays<T> {

    @SuppressWarnings({"unused", "WeakerAccess"})
    @JsonProperty("CODE")
    public int CODE;
    @SuppressWarnings({"unused", "WeakerAccess"})
    @JsonProperty("IDEA")
    public String IDEA;
    @SuppressWarnings({"unused", "WeakerAccess"})
    @JsonProperty("BODY")
    public T BODY;
    @SuppressWarnings({"unused", "WeakerAccess"})
    @JsonProperty("CNT")
    public int CNT;

    public ResponseOverlays(int CODE, String IDEA, T BODY) {
        this.CODE = CODE;
        this.IDEA = IDEA;
        this.BODY = BODY;
    }

    public ResponseOverlays(int CODE, String IDEA, T BODY, int CNT) {
        this.CODE = CODE;
        this.IDEA = IDEA;
        this.BODY = BODY;
        this.CNT = CNT;
    }
}