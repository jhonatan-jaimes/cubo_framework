package com.cubo.routes;

public abstract class CuboRoutes {
    protected CuboRoute route = new CuboRoute();
    
    public abstract void routes();

    public CuboRoute getRoutes(){
        return route;
    }
}
