package com.cubo;


import com.cubo.routes.CuboRoutes;
import com.cubo.server.CuboServer;
import com.cubo.utils.CuboRegistry;

public class CuboApp {

    private static final CuboServer cs = new CuboServer();

    public static void run(int port, Class<? extends CuboRegistry> cr){
        addRoutes(cr);
        cs.start(port);
    }

    public static void run(int port, Class<? extends CuboRoutes>... cr){
        addRoutes(new CuboRegistry() {
          @Override
          public void initRoutes() {
              addListRoutes(cr);
          }
        });
        cs.start(port);
    }

    private static void addRoutes(Class<? extends CuboRegistry> cr){
        try {
          CuboRegistry registry = cr.getConstructor().newInstance();
          registry.initRoutes();
          cs.setRoutes(registry.getListRoutes());
        } catch (Exception e) {
          System.out.println(e);
        }
    }

    private static void addRoutes(CuboRegistry cr){
        cr.initRoutes();
        cs.setRoutes(cr.getListRoutes());
    }

}
