package com.corejava.interfacedemo.inter2;

/**
 * @author one
 *  测试类
 */
public class Test {
    public static void main(String[] args) {
        execute(new BaomaCar("宝马", 500000));
        System.out.println("---------------------");
        execute(new BaomaMotorcycle("宝马", 150000));


    }

    public static void execute(MotorVehicles mv) {
        mv.run();
        if (mv instanceof Baoma) {
            Baoma baoma = (Baoma) mv;
            baoma.GPS();
        }
    }
}
