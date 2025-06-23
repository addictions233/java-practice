package com.corejava.interfacedemo.inter;

/**
 * @author one
 */
public class Fu implements InterA,InterC{

    @Override
    public  void say() {
        System.out.println("this is method say in class Fu");
    }

    private void print() {
        System.out.println(" this is method print in class Fu");
    }
}
