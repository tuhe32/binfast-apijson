package com.binfast.apijson.config;

import org.springframework.nativex.hint.JdkProxyHint;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.type.NativeConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 刘斌
 * @date 2022/12/4 3:06 下午
 */
@NativeHint(jdkProxies = @JdkProxyHint(types = {
        HttpServletRequest.class,
        HttpServletResponse.class}
)
)
public class DemoNativeConfiguration implements NativeConfiguration {
}
