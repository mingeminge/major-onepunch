package com.yzm.majorserver.common.properties;

/**
 * @Author : yizuomin
 * @Description :  三流码农写UI 二流码农写架构 一流码农写算法 顶级码农穿女装
 * @Date : Created in 17:47 2019/4/11
 * @Modified By :
 */
public interface TokenProperties {

    String TOKEN_HEADER = "token";

    Long EXPIRE = 3600L;

    String TOKEN_PREFIX = "token_";
}
