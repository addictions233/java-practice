package com.corejava.interfacedemo.inter2;

/**
 * @author one
 */
public class BaomaMotorcycle extends Motorcycle implements Baoma {
    private String brand;
    private double price;

    public BaomaMotorcycle(String brand, double price) {
        this.brand = brand;
        this.price = price;
    }

    @Override
    public void run() {
        System.out.println(brand+"牌子"+price+"元的摩托车跑起了");
    }

    @Override
    public void GPS() {
        System.out.println("已为宝马摩托车安装GPS");
    }
}
