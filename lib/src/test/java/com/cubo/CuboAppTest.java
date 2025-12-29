package com.cubo;

import org.junit.jupiter.api.Test;

public class CuboAppTest {
    @Test
    public void appTest(){
        CuboApp app = new CuboApp();
        var routes = app.routes();
        routes.get("/hola", () -> "Hola, mundo");
        app.setPort(9000);
        app.setRoutes(routes);
        app.run();
    }
}
