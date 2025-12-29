package com.cubo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cubo.routes.CuboRoutes;

public abstract class CuboRegistry {
    
    private static HashMap<CuboHttpMethods, List<CuboPathMethods>> listRoutes = new HashMap<>();

    static {
      for (CuboHttpMethods m : CuboHttpMethods.values()){
        listRoutes.put(m, new ArrayList<>());
      }
    }

    protected static void addListRoutes(Class<? extends CuboRoutes>... cr){
      for (Class<? extends CuboRoutes> c : cr){
        try {
          CuboRoutes r = c.getConstructor().newInstance();
          r.routes();
          for (CuboHttpMethods m : CuboHttpMethods.values()){
            listRoutes.get(m).addAll(r.getRoutes().getMethods().get(m));
          }
        } catch (Exception e) {
          System.out.println(e);
        }
      }
    }

    public abstract void initRoutes();

    public HashMap<CuboHttpMethods, List<CuboPathMethods>> getListRoutes(){
      return listRoutes;
    }

    public static HashMap<CuboHttpMethods, List<CuboPathMethods>> initRoutes(Class<? extends CuboRoutes> ... cr){
      addListRoutes(cr);
      return listRoutes;
    }
}
