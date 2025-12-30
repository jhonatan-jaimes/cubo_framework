package com.cubo;

import org.junit.jupiter.api.Test;

public class CuboAppTest {
    @Test
    public void appTest(){
        CuboApp app = new CuboApp();
        app.setPort(9000);
        app.setRoutes((routes) -> {
            routes.mapGet("/adios", () -> "adios");
            routes.mapPost("/guardar", () -> "Guardado");
            return routes;
        });
        app.setSecurity((security) -> {
            security.setCors("www.google.com", "www.edsw.com");
            security.setNumber(9000);
            return security;
        });
        app.run();
    }
}
