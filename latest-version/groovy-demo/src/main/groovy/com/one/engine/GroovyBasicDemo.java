package com.one.engine;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class GroovyBasicDemo {
    public static void main(String[] args) throws ScriptException {
        // 1. 初始化脚本引擎管理器
        ScriptEngineManager manager = new ScriptEngineManager();
        // 2. 获取 Groovy 脚本引擎
        ScriptEngine engine = manager.getEngineByName("groovy");

        // 3. 设置变量
        Bindings bindings = engine.createBindings();
        bindings.put("name", "JDK 21");
        bindings.put("version", 21);

        // 4. 执行脚本字符串
        String script = "println \"Hello from ${name}!\"; return \"Groovy with Java \" + version";
        Object result = engine.eval(script, bindings);

        // 5. 输出结果
        System.out.println("脚本返回值: " + result);
    }
}
