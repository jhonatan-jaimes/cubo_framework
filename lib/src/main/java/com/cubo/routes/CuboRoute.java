package com.cubo.routes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.cubo.func.CuboMethodParam;
import com.cubo.func.CuboMethodSingle;
import com.cubo.request.CuboHttpReq;
import com.cubo.request.CuboRequest;
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

    public <T> void mapPost(String path, CuboMethodParam<T> cmp, Class<T> cls){
        methods.get(CuboHttpMethods.GET).add(new CuboPathMethods(path, cmp, new CuboRequest(cls)));
    }

    public void mapGet(String path, CuboMethodParam cmp){
        methods.get(CuboHttpMethods.GET).add(new CuboPathMethods(path, cmp, new CuboRequest()));
    }

    public void mapGet(String path, CuboMethodSingle cms){
        if (cms.func() instanceof CuboResponse cr){
            mapGet(path, (res, req) -> cr);
        }
        mapGet(path, (res, req) -> res.ok().body(cms.func()));
    }

    public void mapPost(String path, CuboMethodSingle cms){
        mapPost(path, (res, req) -> res.ok().body(cms.func()));
    }

    public void mapPost(String path, CuboMethodParam cmp){
        methods.get(CuboHttpMethods.POST).add(new CuboPathMethods(path, cmp, new CuboRequest()));
    }

    public HashMap<CuboHttpMethods, List<CuboPathMethods>> getMethods(){
        return methods;
    }
}
