package com.corejava.capturetongpeifu;

import com.corejava.tongpeifu.Employee;
import com.corejava.tongpeifu.Manager;

/**
 *  Pair p 无泛型
 *  Pair<?> p  无泛型限定的通配符
 *  Pair<? extends Employee> p 用通配符定义泛型上限
 *  Pair<? super Manager> p 用通配符定义泛型下限
 */
public class PairTest {
    public static void main(String[] args) {
        Manager ceo = new Manager("Tom",100000,2005,10,1,10000);
        Manager cfo = new Manager("Jack",90000,2007,8,8,8000);
        Manager cto = new Manager("Marray",70000,2010,1,1,15000);

        Manager[] managers = {ceo,cfo,cto};
        Pair<Manager> pair = new Pair<Manager>(ceo, cfo);
        printBuddies(pair); // 输出: first: Tom, second: Jack

        System.out.println("-------------");
        Pair<Employee> pair2 = new Pair<Employee>();
        minMax(managers,pair2);
        printBuddies(pair2);  // 输出: first: Jack, second: Marray

        System.out.println("----------");
        Pair<Employee> pair3 = new Pair<>();
        maxMin(managers,pair3);
        printBuddies(pair3);  //输出: first: Marray, second: Jack

    }
    /*
        Pair<? extends Employee> 泛型上限
            为什么要确定泛型上限?
                1,可以限定传入的对象变量的类型范围
                2,可以用 Employee() employee1来接收传入的Employee派生类对象
     */
    public static void printBuddies(Pair<? extends Employee> pair){
        Employee employee1 = pair.getFirst();
        Employee employee2 = pair.getSecond();
        System.out.println("first: "+employee1.getName()+", second: "+employee2.getName());
    }

    public static void minMax(Manager[] managers,Pair<? super Manager> pair){
        Manager min = managers[0];
        Manager max = managers[0];
        for (Manager manager : managers) {
            if( min.getBonus()>manager.getBonus()) min = manager;
            if(max.getBonus()<manager.getBonus()) max = manager;
        }
        pair.setFirst(min);
        pair.setSecond(max);
    }

    public static void maxMin(Manager[] managers,Pair<? super Manager> pair ){
        minMax(managers,pair);
        PairAlg.swap(pair);

    }
}
