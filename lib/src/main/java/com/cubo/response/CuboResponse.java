package com.cubo.response;

import java.io.PrintWriter;
import com.cubo.utils.CuboBody;
import com.cubo.utils.CuboHttpStatus;

public class CuboResponse {

    private CuboHttpStatus status = CuboHttpStatus.OK;
    private CuboBody body = new CuboBody("");

    public CuboResponse ok(){
        this.status = CuboHttpStatus.OK;
        return this;
    }

    public CuboResponse status(CuboHttpStatus status){
        this.status = status;
        return this;
    }

    /*
    public CuboResponse body(CuboBody body){
        this.body = body;
        return this;
    }
     */

    public String body(CuboBody body){
        this.body = body;
        return this.body.body();
    }

    // 🔥 CLAVE
    public void send(PrintWriter out){
        String content = body.body().toString();
        out.println("HTTP/1.1 " + status.getValues());
        out.println("Content-Type: text/plain");
        out.println("Content-Length: " + content.length());
        out.println();
        out.println(content);
        out.flush();
    }
}
