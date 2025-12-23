package com.cubo.func;

import com.cubo.response.CuboResponse;

@FunctionalInterface
public interface CuboMethodParam {
    Object func(CuboResponse res, String req);
}
