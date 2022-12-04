package com.binfast.apijson.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.nativex.hint.AotProxyHint;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.TypeHint;
import org.springframework.nativex.type.NativeConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 刘斌
 * @date 2022/12/4 3:06 下午
 */
@NativeHint(aotProxies = @AotProxyHint(interfaces = {
        HttpServletRequest.class,
        HttpServletResponse.class}
)
)
@Configuration(proxyBeanMethods=false)
public class DemoNativeConfiguration implements NativeConfiguration {
}
