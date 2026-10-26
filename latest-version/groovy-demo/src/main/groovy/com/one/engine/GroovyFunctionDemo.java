package com.one.engine;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class GroovyFunctionDemo {
    public static void main(String[] args) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");

        // 定义带有函数的 Groovy 脚本
        String script = """
                def calculateDiscount(double price, String userType) {
                    if (userType == "VIP") {
                        return price * 0.8
                    }
                    return price
                }
                """;

        // 编译/执行脚本以加载函数
        engine.eval(script);

        // 转换为 Invocable 接口
        Invocable invocable = (Invocable) engine;

        // 传入函数名和参数进行调用
        Object vipPrice = invocable.invokeFunction("calculateDiscount", 100.0, "VIP");
        Object normalPrice = invocable.invokeFunction("calculateDiscount", 100.0, "NORMAL");

        System.out.println("VIP 价格: " + vipPrice);       // 80.0
        System.out.println("普通价格: " + normalPrice);   // 100.0
    }
}
