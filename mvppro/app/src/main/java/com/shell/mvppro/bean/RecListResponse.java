package com.shell.mvppro.bean;

import com.shell.mvppro.network.common.BasicResponse;

import java.util.List;

/**
 */

public  class RecListResponse<T> {

    private int code;

    private List<T> data;

    private String message;

    private int ttl;

    private String ver;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

}
