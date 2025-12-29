package com.cubo.utils;

public enum CuboHttpStatus {
    OK("OK", 200), CREATED("CREATED", 201), FOUND("FOUND", 302),
    NOT_FOUND("NOT FOUND", 404), BAD_REQUEST("BAD REQUEST", 400);

    private final String ss;
    private final Integer sn;

    private CuboHttpStatus(String ss, Integer sn){
        this.ss = ss;
        this.sn = sn;
    }

    public String getValues(){
        return this.sn + " " + this.ss;
    }
}
