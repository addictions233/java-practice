package com.one.engine;

import javax.script.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GroovyCacheDemo {
    // 用于缓存编译后的脚本，Key 可以是脚本的 MD5 签名
    private static final Map<String, CompiledScript> scriptCache = new ConcurrentHashMap<>();
    private static final ScriptEngine engine;

    static {
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("groovy");
    }

    public static Object executeCachedScript(String scriptId, String scriptText, Map<String, Object> params) throws ScriptException {
        // 1. 检查缓存，若无则编译并缓存
        CompiledScript compiledScript = scriptCache.computeIfAbsent(scriptId, id -> {
            try {
                return ((Compilable) engine).compile(scriptText);
            } catch (ScriptException e) {
                throw new RuntimeException("Groovy 脚本编译失败: " + id, e);
            }
        });

        // 2. 绑定参数（注意：Bindings 是线程不安全的，每次执行必须新建）
        Bindings bindings = engine.createBindings();
        bindings.putAll(params);

        // 3. 执行编译后的脚本
        return compiledScript.eval(bindings);
    }

    public static void main(String[] args) throws ScriptException {
        String scriptId = "user_risk_check";
        String scriptText = "return age > 60 && amount > 10000";

        // 模拟多次高效调用
        System.out.println(executeCachedScript(scriptId, scriptText, Map.of("age", 65, "amount", 12000))); // true
        System.out.println(executeCachedScript(scriptId, scriptText, Map.of("age", 25, "amount", 5000)));  // false
    }
}
