package com.cubo.func;

import com.cubo.response.CuboResponse;

@FunctionalInterface
public interface CuboMethodParam {
    CuboResponse func(CuboResponse res, String req);
}
