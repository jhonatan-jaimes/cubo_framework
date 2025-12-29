package com.cubo.response;

import com.cubo.utils.CuboHttpStatus;

public class CuboResponse {

    private CuboHttpStatus status = CuboHttpStatus.OK;
    private Object body = new String("");

    public CuboResponse ok(){
        this.status = CuboHttpStatus.OK;
        return this;
    }

    public CuboResponse created(){
        this.status = CuboHttpStatus.CREATED;
        return this;
    }

    public CuboResponse status(CuboHttpStatus status){
        this.status = status;
        return this;
    }

    public CuboResponse body(Object body){
        this.body = body;
        return this;
    }

    public Object getBody(){
        return this.body;
    }

    // 🔥 CLAVE
    public CuboResponse send(){
        return this;
    }
}
