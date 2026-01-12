package com.cubo.request;

public class CuboHttpReq {

  private Object t;

  public CuboHttpReq() {
  }

  public CuboHttpReq(Object t) {
    this.t = t;
  }
  
  public Object getClassReq(){
    return this.t;
  }

}
