package com.cubo.routes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cubo.func.CuboMethodParam;
import com.cubo.func.CuboMethodSingle;
import com.cubo.response.CuboResponse;
import com.cubo.utils.CuboHttpMethods;
import com.cubo.utils.CuboPathMethods;

public class CuboRoute {

    private HashMap<CuboHttpMethods, List<CuboPathMethods>> methods;

    public CuboRoute(){
        methods = new HashMap<>();
        for (CuboHttpMethods c : CuboHttpMethods.values()){
            methods.put(c, new ArrayList<>());
        }
    }

    public void get(String path, CuboMethodParam func){
        methods.get(CuboHttpMethods.GET).add(new CuboPathMethods(path, func));
    }

    public void get(String path, CuboMethodSingle func){
        if (func.func() instanceof CuboResponse cr){
            get(path, (res, req) -> cr);
        }
        get(path, (res, req) -> res.ok().body(func.func()));
    }

    public void post(String path, CuboMethodSingle func){
        post(path, (res, req) -> res.ok().body(func.func()));
    }

    public void post(String path, CuboMethodParam func){
        methods.get(CuboHttpMethods.POST).add(new CuboPathMethods(path, func));
    }

    public HashMap<CuboHttpMethods, List<CuboPathMethods>> getMethods(){
        return methods;
    }
}
