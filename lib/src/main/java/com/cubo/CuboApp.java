package com.cubo;


import java.util.HashMap;
import java.util.List;

import com.cubo.routes.CuboRoutes;
import com.cubo.server.CuboServer;
import com.cubo.utils.CuboHttpMethods;
import com.cubo.utils.CuboPathMethods;
import com.cubo.utils.CuboRegistry;

public class CuboApp {

    private static final CuboServer cs = new CuboServer();

    public static void run(int port, CuboRegistry cr){
      cs.setRoutes(getRoutes(cr));
      cs.start(port);
    }

    public static void run(int port, Class<? extends CuboRegistry> cr){
      try {
        CuboRegistry registry = cr.getConstructor().newInstance();
        run(port, registry);
      } catch (Exception e) {
        e.getMessage();
      } 
    }

    public static void run(int port, Class<? extends CuboRoutes>... cr){
      run(port, new CuboRegistry() {
        @Override
        public void initRoutes() {
            addListRoutes(cr);
        }
      });
    }

    private static HashMap<CuboHttpMethods, List<CuboPathMethods>> getRoutes(CuboRegistry cr){
      cr.initRoutes();
      return cr.getListRoutes();
    }

}
