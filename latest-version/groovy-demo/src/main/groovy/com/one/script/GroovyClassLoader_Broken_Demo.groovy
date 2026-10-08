package com.one.script

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.MultipleCompilationErrorsException

// 反例：模拟 Spring Boot 中错误的类加载器选择

def tempDir = new File(System.getProperty("java.io.tmpdir"), "groovy_demo_${System.currentTimeMillis()}")
tempDir.mkdirs()

def pkgDir = new File(tempDir, "com/example")
pkgDir.mkdirs()
def srcFile = new File(pkgDir, "SecretService.java")
srcFile.text = """package com.example;
public class SecretService {
    public String sayHello() {
        return "Hello from SecretService!";
    }
}
"""

def javac = "javac -d ${tempDir.absolutePath} ${srcFile.absolutePath}".execute()
javac.waitFor()
if (javac.exitValue() != 0) {
    println "编译失败: ${javac.err.text}"
    System.exit(1)
}
println "已编译 SecretService 到: ${tempDir.absolutePath}"

def secretClassLoader = new URLClassLoader(
    [tempDir.toURI().toURL()] as URL[],
    this.class.classLoader
)

def secretClass = secretClassLoader.loadClass("com.example.SecretService")
println "secretClassLoader 能加载: ${secretClass.name}"

def wrongParent = ClassLoader.getSystemClassLoader()
println "\n--- 错误示范：parent = ${wrongParent.class.name} ---"

def brokenConfig = new CompilerConfiguration()
def brokenLoader = new GroovyClassLoader(wrongParent, brokenConfig)
def brokenShell = new GroovyShell(brokenLoader)

def scriptText = """import com.example.SecretService
def s = new SecretService()
return s.sayHello()
"""

try {
    def result = brokenShell.evaluate(scriptText)
    println "结果: $result"
} catch (MultipleCompilationErrorsException e) {
    println "编译失败（符合预期）！错误信息："
    println e.message
}

def correctLoader = new GroovyClassLoader(secretClassLoader, brokenConfig)
def correctShell = new GroovyShell(correctLoader)

println "\n--- 正确示范：parent = ${secretClassLoader.class.name} ---"
try {
    def result = correctShell.evaluate(scriptText)
    println "结果: $result"
} catch (Exception e) {
    println "失败: ${e.message}"
}

tempDir.deleteDir()