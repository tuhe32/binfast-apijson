package com.binfast.apijson;

import apijson.Log;
import apijson.NotNull;
import apijson.StringUtil;
import apijson.framework.APIJSONApplication;
import apijson.framework.APIJSONCreator;
import apijson.framework.APIJSONParser;
import apijson.orm.*;
import apijson.router.APIJSONRouterApplication;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.binfast.apijson.config.*;
import com.binfast.apijson.controller.DemoController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import unitauto.MethodUtil;
import unitauto.jar.UnitAutoApp;

import javax.naming.Context;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Demo SpringBoot Application 主应用程序启动类
 * 右键这个类 > Run As > Java Application
 *
 * @author Lemon
 */
//@Configuration
@SpringBootApplication
public class BinfastApijsonApplication {
    private static final String TAG = "BinfastApijsonApplication";

    // 全局 ApplicationContext 实例，方便 getBean 拿到 Spring/SpringBoot 注入的类实例
    private static ApplicationContext APPLICATION_CONTEXT;
    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    public static void main(String[] args) throws Exception {
        APPLICATION_CONTEXT = SpringApplication.run(BinfastApijsonApplication.class, args);

        // FIXME 不要开放给项目组后端之外的任何人使用 UnitAuto（强制登录鉴权）！！！如果不需要单元测试则移除相关代码或 unitauto.Log.DEBUG = false;
        // 上线生产环境前改为 false，可不输出 APIJSONORM 的日志 以及 SQLException 的原始(敏感)信息
        unitauto.Log.DEBUG = Log.DEBUG = true;
        APIJSONParser.IS_PRINT_BIG_LOG = true;
        APIJSONApplication.init();
        APIJSONRouterApplication.init();
        System.out.println("\n\n<<<<<<<<< 本 Demo 在 resources/static 内置了 APIAuto，Chrome/Firefox 打开 http://localhost:8080 即可调试(端口号根据项目配置而定) ^_^ >>>>>>>>>\n");
    }

    // SpringBoot 2.x 自定义端口方式
//    @Override
//    public void customize(ConfigurableServletWebServerFactory server) {
//        server.setPort(8080);
//    }

    // 支持 APIAuto 中 JavaScript 代码跨域请求
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("*")
                        .allowCredentials(true)
                        .exposedHeaders(DemoController.APIJSON_DELEGATE_ID)  // Cookie 和 Set-Cookie 怎么设置都没用 ,Cookie,Set-Cookie")   // .exposedHeaders("*")
                        .maxAge(3600);
            }
        };
    }

    static {
        // 把以下需要用到的数据库驱动取消注释即可，如果这里没有可以自己新增
        //		try { //加载驱动程序
        //			Log.d(TAG, "尝试加载 SQLServer 驱动 <<<<<<<<<<<<<<<<<<<<< ");
        //			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //			Log.d(TAG, "成功加载 SQLServer 驱动！>>>>>>>>>>>>>>>>>>>>> ");
        //		}
        //		catch (ClassNotFoundException e) {
        //			e.printStackTrace();
        //			Log.e(TAG, "加载 SQLServer 驱动失败，请检查 pom.xml 中 net.sourceforge.jtds 版本是否存在以及可用 ！！！");
        //		}
        //
        //		try { //加载驱动程序
        //			Log.d(TAG, "尝试加载 Oracle 驱动 <<<<<<<<<<<<<<<<<<<<< ");
        //			Class.forName("oracle.jdbc.driver.OracleDriver");
        //			Log.d(TAG, "成功加载 Oracle 驱动！>>>>>>>>>>>>>>>>>>>>> ");
        //		}
        //		catch (ClassNotFoundException e) {
        //			e.printStackTrace();
        //			Log.e(TAG, "加载 Oracle 驱动失败，请检查 pom.xml 中 com.oracle.jdbc 版本是否存在以及可用 ！！！");
        //		}
        //
        //		try { //加载驱动程序
        //			Log.d(TAG, "尝试加载 DB2 驱动 <<<<<<<<<<<<<<<<<<<<< ");
        //			Class.forName("com.ibm.db2.jcc.DB2Driver");
        //			Log.d(TAG, "成功加载 DB2 驱动！>>>>>>>>>>>>>>>>>>>>> ");
        //		}
        //		catch (ClassNotFoundException e) {
        //			e.printStackTrace();
        //			Log.e(TAG, "加载 DB2 驱动失败，请检查 pom.xml 中 com.ibm.db2 版本是否存在以及可用 ！！！");
        //		}

//        try { //加载驱动程序
//            Log.d(TAG, "尝试加载 SQLite 驱动 <<<<<<<<<<<<<<<<<<<<< ");
//            Class.forName("org.sqlite.SQLiteJDBCLoader");
//            Log.d(TAG, "成功加载 SQLite 驱动！>>>>>>>>>>>>>>>>>>>>> ");
//        }
//        catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            Log.e(TAG, "加载 SQLite 驱动失败，请检查 pom.xml 中 sqlite-jdbc 版本是否存在以及可用 ！！！");
//        }
//
//        try { //加载驱动程序
//            Log.d(TAG, "尝试加载 Dameng 驱动 <<<<<<<<<<<<<<<<<<<<< ");
//            Class.forName("dm.jdbc.driver.DmDriver");
//            Log.d(TAG, "成功加载 Dameng 驱动！>>>>>>>>>>>>>>>>>>>>> ");
//        }
//        catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            Log.e(TAG, "加载 Dameng 驱动失败，请检查 pom.xml 中 com.dameng 版本是否存在以及可用 ！！！");
//        }

        //    try { //加载驱动程序
        //      Log.d(TAG, "尝试加载 TDengine 驱动 <<<<<<<<<<<<<<<<<<<<< ");
        //      Class.forName("com.taosdata.jdbc.TSDBDriver");
        //      Log.d(TAG, "成功加载 TDengine 驱动！>>>>>>>>>>>>>>>>>>>>> ");
        //    } catch (ClassNotFoundException e) {
        //      e.printStackTrace();
        //      Log.e(TAG, "加载 TDengine 驱动失败，请检查 pom.xml 中 com.taosdata.jdbc 版本是否存在以及可用 ！！！");
        //    }

        // APIJSON 配置 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

        Map<String, Pattern> COMPILE_MAP = AbstractVerifier.COMPILE_MAP;
        COMPILE_MAP.put("PHONE", StringUtil.PATTERN_PHONE);
        COMPILE_MAP.put("EMAIL", StringUtil.PATTERN_EMAIL);
        COMPILE_MAP.put("ID_CARD", StringUtil.PATTERN_ID_CARD);

        // 使用本项目的自定义处理类
        APIJSONApplication.DEFAULT_APIJSON_CREATOR = new APIJSONCreator<Long>() {

            @Override
            public Parser<Long> createParser() {
                return new DemoParser();
            }

            @Override
            public FunctionParser createFunctionParser() {
                return new DemoFunctionParser();
            }

            @Override
            public Verifier<Long> createVerifier() {
                return new DemoVerifier();
            }

            @Override
            public SQLConfig createSQLConfig() {
                return new DemoSQLConfig();
            }

            @Override
            public SQLExecutor createSQLExecutor() {
                return new DemoSQLExecutor();
            }

        };

        // APIJSON 配置 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


        // UnitAuto 单元测试配置  https://github.com/TommyLemon/UnitAuto  <<<<<<<<<<<<<<<<<<<<<<<<<<<
        // FIXME 不要开放给项目组后端之外的任何人使用 UnitAuto（强制登录鉴权）！！！如果不需要单元测试则移除相关代码或 unitauto.Log.DEBUG = false;
        UnitAutoApp.init();

        // 适配 Spring 注入的类及 Context 等环境相关的类
        final MethodUtil.InstanceGetter ig = MethodUtil.INSTANCE_GETTER;
        MethodUtil.INSTANCE_GETTER = new MethodUtil.InstanceGetter() {

            @Override
            public Object getInstance(@NotNull Class<?> clazz, List<MethodUtil.Argument> classArgs, Boolean reuse) throws Exception {
                if (APPLICATION_CONTEXT != null && ApplicationContext.class.isAssignableFrom(clazz) && clazz.isAssignableFrom(APPLICATION_CONTEXT.getClass())) {
                    return APPLICATION_CONTEXT;
                }

                if (reuse != null && reuse && (classArgs == null || classArgs.isEmpty())) {
                    return APPLICATION_CONTEXT.getBean(clazz);
                }

                return ig.getInstance(clazz, classArgs, reuse);
            }
        };

        // 排除转换 JSON 异常的类，一般是 Context 等环境相关的类
        final MethodUtil.JSONCallback jc = MethodUtil.JSON_CALLBACK;
        MethodUtil.JSON_CALLBACK = new MethodUtil.JSONCallback() {

            @Override
            public JSONObject newSuccessResult() {
                return jc.newSuccessResult();
            }

            @Override
            public JSONObject newErrorResult(Throwable e) {
                return jc.newErrorResult(e);
            }

            @Override
            public JSONObject parseJSON(String type, Object value) {
                if (value == null || unitauto.JSON.isBooleanOrNumberOrString(value) || value instanceof JSON || value instanceof Enum) {
                    return jc.parseJSON(type, value);
                }

                if (value instanceof ApplicationContext
                        || value instanceof Context
                        || value instanceof org.apache.catalina.Context
                    // SpringBoot 2.6.7 已移除  || value instanceof ch.qos.logback.core.Context
                ) {
                    value = value.toString();
                } else {
                    try {
                        value = JSON.parse(JSON.toJSONString(value, new PropertyFilter() {
                            @Override
                            public boolean apply(Object object, String name, Object value) {
                                if (value == null) {
                                    return true;
                                }

                                if (value instanceof ApplicationContext
                                        || value instanceof Context
                                        || value instanceof org.apache.catalina.Context
                                    // SpringBoot 2.6.7 已移除  || value instanceof ch.qos.logback.core.Context
                                ) {
                                    return false;
                                }

                                // 防止通过 UnitAuto 远程执行 getDBPassword 等方法来查到敏感信息，但如果直接调用 public String getDBUri 这里没法拦截，仍然会返回敏感信息
                                //	if (object instanceof SQLConfig) {
                                //		// 这个类部分方法不序列化返回
                                //		if ("dBUri".equalsIgnoreCase(name) || "dBPassword".equalsIgnoreCase(name) || "dBAccount".equalsIgnoreCase(name)) {
                                //			return false;
                                //		}
                                //		return false;  // 这个类所有方法都不序列化返回
                                //	}

                                // 所有类中的方法只要包含关键词就不序列化返回
                                String n = StringUtil.toLowerCase(name);
                                if (n.contains("database") || n.contains("schema") || n.contains("dburi") || n.contains("password") || n.contains("account")) {
                                    return false;
                                }

                                return Modifier.isPublic(value.getClass().getModifiers());
                            }
                        }));
                    } catch (Exception e) {
                        Log.e(TAG, "toJSONString  catch \n" + e.getMessage());
                    }
                }

                return jc.parseJSON(type, value);
            }

        };

        // UnitAuto 单元测试配置  https://github.com/TommyLemon/UnitAuto  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    }

}
