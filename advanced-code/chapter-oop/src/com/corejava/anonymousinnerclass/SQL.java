package com.corejava.anonymousinnerclass;

/**
 * 自定义简单SQL构建器，不支持复杂sql*
 */
public class SQL {

    public StringBuffer sql = new StringBuffer();

    public SQL select(String targetStr) {
        sql.append(" select ").append(targetStr);
        return this;
    }

    public SQL from(String table) {
        sql.append(" from ").append(table);
        return this;
    }

    public SQL where(String where) {
        sql.append(" where ").append(where);
        return this;
    }

    public SQL and(String and) {
        sql.append(" and ").append(and);
        return this;
    }

    public SQL orderby(String orderby) {
        sql.append(" order by ").append(orderby);
        return this;
    }

    @Override
    public String toString() {
        return sql.toString();
    }

}