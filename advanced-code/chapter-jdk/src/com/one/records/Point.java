package com.one.records;

/**
 * 也可以向Record添加额外的构造器、方法和静态成员
 * @param x
 * @param y
 */
public record Point(int x, int y) {
    // 自定义紧凑构造器
    public Point {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates cannot be negative");
        }
    }
    
    // 重载构造器
    public Point() {
        this(0, 0);
    }
    
    // 实例方法
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + 
                          Math.pow(this.y - other.y, 2));
    }
    
    // 静态成员
    public static final Point ORIGIN = new Point(0, 0);
    
    // 静态方法
    public static Point of(int x, int y) {
        return new Point(x, y);
    }
}
