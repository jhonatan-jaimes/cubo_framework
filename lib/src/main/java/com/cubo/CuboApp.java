package com.cubo;


import java.util.HashMap;
import java.util.List;

import com.cubo.routes.CuboRoutes;
import com.cubo.server.CuboServer;
import com.cubo.utils.CuboHttpMethods;
import com.cubo.utils.CuboPathMethods;
import com.cubo.utils.CuboRegistry;
import com.cubo.utils.CuboVariable;

public class CuboApp {

    private static final CuboServer cs = new CuboServer();
    private int port = CuboVariable.PORT;
    private CuboRegistry cr;

    public void setPort(int port){
      this.port = port;
    }

    private static CuboRegistry initCr(Class<? extends CuboRegistry> cr){
      try {
        return cr.getConstructor().newInstance();
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    public void setRegistry(Class<? extends CuboRegistry> cr){
      this.cr = initCr(cr);
    }

    public static void run(int port, CuboRegistry cr){
      cs.setRoutes(getRoutes(cr));
      cs.start(port);
    }

    public static void run(int port, Class<? extends CuboRegistry> cr){
      run(port, initCr(cr));
    }

    public void run(){
      run(this.port, this.cr);
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
