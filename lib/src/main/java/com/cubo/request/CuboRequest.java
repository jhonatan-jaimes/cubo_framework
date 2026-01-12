package com.cubo.request;

public class CuboRequest {

  private Object req;

  public CuboRequest() {
    this.req = new CuboRequest();
  }

  public CuboRequest(Object obj) {
    this.req = obj;
  }

  public Object getReq() {
    return req;
  }

  public void setObj(Object obj) {
    this.req = obj;
  }
    
}
