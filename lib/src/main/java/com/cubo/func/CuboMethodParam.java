package com.cubo.func;

import com.cubo.request.CuboRequest;
import com.cubo.response.CuboResponse;

@FunctionalInterface
public interface CuboMethodParam<T> {
    CuboResponse func(CuboResponse res, T req);
}
