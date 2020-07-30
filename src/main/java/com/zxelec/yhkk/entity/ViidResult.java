package com.zxelec.yhkk.entity;

public class ViidResult {
    private String code;
    private String result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ViidResult{" +
                "code='" + code + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
