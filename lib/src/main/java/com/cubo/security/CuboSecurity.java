package com.cubo.security;

import java.util.ArrayList;
import java.util.List;

public class CuboSecurity {
  private List<String> cors = new ArrayList<>();
  private int number = 100;

  public void setCors(String... cors){
    for (String c : cors){
      this.cors.add(c);
    }
  }

  public void setNumber(int num){
    this.number = num;
  }

  public CuboSecurity getSecurity(){
    return this;
  }
  
}
