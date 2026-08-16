package com.one.records;

/**
 * 使用records
 *   record关键字修饰的类自动生成构造器, 访问器, equals和hashCode方法, 以及toString 方法
 * @param name
 * @param id
 * @param department
 */
public record Employee2(String name, int id, Department department) {
}
