package com.one.eastrules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Rule;

@Rule(name = "my awesome rule" )
public class MyRule {

    @Condition
    public boolean when() {
        return true;
    }
    
    @Action
    public void then() {
        System.out.println("Easy Rules rocks!");
    }
}