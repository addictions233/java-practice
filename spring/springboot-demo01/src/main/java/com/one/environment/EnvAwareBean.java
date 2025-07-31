package com.one.environment;


import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EnvAwareBean implements EnvironmentAware {

    private static String ENV_KEY;

    @Override
    public void setEnvironment(Environment environment) {
        ENV_KEY = environment.getProperty("env.key");
    }
}
