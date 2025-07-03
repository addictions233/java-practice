package com.one.mybatis.plugin;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

public class SqlStatementHelper {

    private final DbType dbType;

    public SqlStatementHelper(String dbType) {
        this.dbType = DbType.of(dbType);
    }

    public String addCondition(String sql, String fieldName, Object fieldValue) {
        if (StringUtils.isBlank(sql)) {
            return sql;
        }

        List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, dbType);
        if (CollectionUtils.isEmpty(sqlStatements)) {
            return sql;
        }

        for (SQLStatement sqlStatement : sqlStatements) {
            addStatementCondition(sqlStatement, fieldName, fieldValue);
        }

        return SQLUtils.toSQLString(sqlStatements, dbType);
    }

    private void addStatementCondition(SQLStatement sqlStatement, String fieldName, Object fieldValue) {
        if (sqlStatement instanceof SQLSelectStatement) {
            SQLSelectQueryBlock queryObject = (SQLSelectQueryBlock) ((SQLSelectStatement) sqlStatement).getSelect().getQuery();
            addSelectStatementCondition(queryObject, queryObject.getFrom(), fieldName, fieldValue);
        } else if (sqlStatement instanceof SQLUpdateStatement) {
            SQLUpdateStatement updateStatement = (SQLUpdateStatement) sqlStatement;
            addUpdateStatementCondition(updateStatement, fieldName, fieldValue);
        } else if (sqlStatement instanceof SQLDeleteStatement) {
            SQLDeleteStatement deleteStatement = (SQLDeleteStatement) sqlStatement;
            addDeleteStatementCondition(deleteStatement, fieldName, fieldValue);
        } else if (sqlStatement instanceof SQLInsertStatement) {
            SQLInsertStatement insertStatement = (SQLInsertStatement) sqlStatement;
            addInsertStatementCondition(insertStatement, fieldName, fieldValue);
        }
    }

    private void addInsertStatementCondition(SQLInsertStatement insertStatement, String fieldName, Object fieldValue) {
        if (insertStatement != null) {
            SQLInsertInto sqlInsertInto = insertStatement;
            SQLSelect sqlSelect = sqlInsertInto.getQuery();
            if (sqlSelect != null) {
                SQLSelectQueryBlock selectQueryBlock = (SQLSelectQueryBlock) sqlSelect.getQuery();
                addSelectStatementCondition(selectQueryBlock, selectQueryBlock.getFrom(), fieldName, fieldValue);
            }
        }
    }

    private void addDeleteStatementCondition(SQLDeleteStatement deleteStatement, String fieldName, Object fieldValue) {
        SQLExpr where = deleteStatement.getWhere();
        //添加子查询中的where条件
        addSQLExprCondition(where, fieldName, fieldValue);

        SQLExpr newCondition = newEqualityCondition(deleteStatement.getTableName().getSimpleName(),
                deleteStatement.getTableSource().getAlias(), fieldName, fieldValue, where);
        deleteStatement.setWhere(newCondition);

    }

    private void addSQLExprCondition(SQLExpr where, String fieldName, Object fieldValue) {
        if (where instanceof SQLInSubQueryExpr) {
            SQLInSubQueryExpr inWhere = (SQLInSubQueryExpr) where;
            SQLSelect subSelectObject = inWhere.getSubQuery();
            SQLSelectQueryBlock subQueryObject = (SQLSelectQueryBlock) subSelectObject.getQuery();
            addSelectStatementCondition(subQueryObject, subQueryObject.getFrom(), fieldName, fieldValue);
        } else if (where instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr opExpr = (SQLBinaryOpExpr) where;
            SQLExpr left = opExpr.getLeft();
            SQLExpr right = opExpr.getRight();
            addSQLExprCondition(left, fieldName, fieldValue);
            addSQLExprCondition(right, fieldName, fieldValue);
        } else if (where instanceof SQLQueryExpr) {
            SQLSelectQueryBlock selectQueryBlock = (SQLSelectQueryBlock) (((SQLQueryExpr) where).getSubQuery()).getQuery();
            addSelectStatementCondition(selectQueryBlock, selectQueryBlock.getFrom(), fieldName, fieldValue);
        }
    }

    private void addUpdateStatementCondition(SQLUpdateStatement updateStatement, String fieldName, Object fieldValue) {
        SQLExpr where = updateStatement.getWhere();
        //添加子查询中的where条件
        addSQLExprCondition(where, fieldName, fieldValue);
        SQLExpr newCondition = newEqualityCondition(updateStatement.getTableName().getSimpleName(),
                updateStatement.getTableSource().getAlias(), fieldName, fieldValue, where);
        updateStatement.setWhere(newCondition);
    }

    private void addSelectStatementCondition(SQLSelectQueryBlock queryObject, SQLTableSource from, String fieldName, Object fieldValue) {
        if (StringUtils.isBlank(fieldName) || Objects.isNull(from) || Objects.isNull(queryObject)) return;

        SQLExpr originCondition = queryObject.getWhere();
        if (from instanceof SQLExprTableSource) {
            String tableName = ((SQLIdentifierExpr) ((SQLExprTableSource) from).getExpr()).getName();
            String alias = from.getAlias();
            SQLExpr newCondition = newEqualityCondition(tableName, alias, fieldName, fieldValue, originCondition);
            queryObject.setWhere(newCondition);
        } else if (from instanceof SQLJoinTableSource) {
            SQLJoinTableSource joinObject = (SQLJoinTableSource) from;
            SQLTableSource left = joinObject.getLeft();
            SQLTableSource right = joinObject.getRight();

            addSelectStatementCondition(queryObject, left, fieldName, fieldValue);
            addSelectStatementCondition(queryObject, right, fieldName, fieldValue);

        } else if (from instanceof SQLSubqueryTableSource) {
            SQLSelect subSelectObject = ((SQLSubqueryTableSource) from).getSelect();
            SQLSelectQueryBlock subQueryObject = (SQLSelectQueryBlock) subSelectObject.getQuery();
            addSelectStatementCondition(subQueryObject, subQueryObject.getFrom(), fieldName, fieldValue);
        } else {
        }
    }

    private SQLExpr newEqualityCondition(String tableName, String tableAlias, String fieldName, Object fieldValue, SQLExpr originCondition) {
        String fullFiledName = StringUtils.isBlank(tableAlias) ? fieldName : tableAlias + "." + fieldName;

        SQLExpr value;
        if (fieldValue instanceof Integer) {
            value = new SQLIntegerExpr((Integer) fieldValue);
        } else if (fieldValue instanceof Long) {
            value = new SQLBigIntExpr((Long) fieldValue);
        } else {
            value = new SQLCharExpr(String.valueOf(fieldValue));
        }
        SQLExpr condition = new SQLBinaryOpExpr(new SQLIdentifierExpr(fullFiledName), value, SQLBinaryOperator.Equality);
        return SQLUtils.buildCondition(SQLBinaryOperator.BooleanAnd, condition, false, originCondition);
    }
}
