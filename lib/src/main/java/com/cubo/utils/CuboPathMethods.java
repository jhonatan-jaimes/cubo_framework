package com.cubo.utils;

import com.cubo.func.CuboMethodParam;
import com.cubo.request.CuboHttpReq;
import com.cubo.request.CuboRequest;

public record CuboPathMethods(String path, CuboMethodParam func, CuboRequest req) {
    
}
