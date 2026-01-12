package com.cubo;


import java.util.HashMap;
import java.util.List;

import com.cubo.routes.CuboFuncRoutes;
import com.cubo.routes.CuboRoute;
import com.cubo.routes.CuboRoutes;
import com.cubo.security.CuboFuncSecurity;
import com.cubo.security.CuboSecurity;
import com.cubo.server.CuboServer;
import com.cubo.utils.CuboHttpMethods;
import com.cubo.utils.CuboPathMethods;
import com.cubo.utils.CuboRegistry;
import com.cubo.utils.CuboVariable;

public class CuboApp {

    private static final CuboServer cs = new CuboServer();
    private int port = CuboVariable.PORT;
    private CuboRegistry cr;
    private CuboRoute routes;
    private CuboSecurity security;

    // Run static para apps grandes
    private static CuboRegistry initCr(Class<? extends CuboRegistry> cr){
      try {
        return cr.getConstructor().newInstance();
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    public static void run(int port, CuboRegistry cr){
      cs.setRoutes(getRoutes(cr));
      cs.start(port);
    }

    public static void run(int port, CuboRoute cr){
      cs.setRoutes(cr.getMethods());
      cs.start(port);
    }

    public static void run(int port, Class<? extends CuboRegistry> cr){
      run(port, initCr(cr));
    }

    public static void run(int port, Class<? extends CuboRoutes>... cr){
      run(port, new CuboRegistry() {
        @Override
        public void initRoutes() {
            addListRoutes(cr);
        }
      });
    }

    public void setRegistry(Class<? extends CuboRegistry> cr){
      this.cr = initCr(cr);
    }

    //========================================================================================================//

    // Run para scripts pequeños

    public void setRoutes(CuboFuncRoutes cfr){
      this.routes = cfr.routes(new CuboRoute());
    }

    public void setPort(int port){
      this.port = port;
    }

    public void setSecurity(CuboFuncSecurity cfs){
      this.security = cfs.security(new CuboSecurity());
    }

    public void run(){
      if (this.routes != null && this.cr == null){
        run(this.port, this.routes);
      } else if (this.routes == null && this.cr == null){
        throw new RuntimeException("No se pueden iniciar sin rutas registradas");
      }
      run(this.port, this.cr);
    }

    //========================================================================================================//

    // Mapeo de registros en la variable statica
    private static HashMap<CuboHttpMethods, List<CuboPathMethods>> getRoutes(CuboRegistry cr){
      cr.initRoutes();
      return cr.getListRoutes();
    }

}
