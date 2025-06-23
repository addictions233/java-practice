package com.corejava.interfacedemo.inter2;

/**
 * @author one
 */
public class BaomaCar extends Car implements Baoma {
    private String brand;
    private double price;

    public BaomaCar(String brand, double price) {
        this.brand = brand;
        this.price = price;
    }

    @Override
    public void run() {
        System.out.println(brand+"牌子"+price+"元的汽车跑起来了");
    }

    @Override
    public void GPS() {
        System.out.println("已为宝马汽车安装GPS");
    }
}
