///*
// * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
// * <p>
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not
// * use this file except in compliance with the License. You may obtain a copy of
// * the License at
// * <p>
// * https://www.apache.org/licenses/LICENSE-2.0
// * <p>
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// * License for the specific language governing permissions and limitations under
// * the License.
// */
//package com.one.mybatisplus.datascope.interceptor;
//
//import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
//import com.baomidou.mybatisplus.core.config.GlobalConfig;
//import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
//import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
//import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
//import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
//import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
//import com.baomidou.mybatisplus.extension.toolkit.PropertyMapper;
//import com.one.mybatisplus.datascope.handler.impl.DataPermissionHandlerImpl;
//import lombok.Data;
//import net.sf.jsqlparser.expression.*;
//import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
//import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
//import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
//import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
//import net.sf.jsqlparser.expression.operators.relational.InExpression;
//import net.sf.jsqlparser.expression.operators.relational.ItemsList;
//import net.sf.jsqlparser.schema.Column;
//import net.sf.jsqlparser.schema.Table;
//import net.sf.jsqlparser.statement.delete.Delete;
//import net.sf.jsqlparser.statement.insert.Insert;
//import net.sf.jsqlparser.statement.select.*;
//import net.sf.jsqlparser.statement.update.Update;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Optional;
//import java.util.Properties;
//
///**
// * @author mqp
// * @since 3.4.0
// */
//@Data
//@EnableConfigurationProperties(MybatisPlusProperties.class)
//public class DelFlagLineInnerInterceptor extends JsqlParserSupport implements InnerInterceptor {
//
//    private MybatisPlusProperties mybatisPlusProperties;
//
//    private DataPermissionHandlerImpl.DelLineHandler delLineHandler;
//
//    public DelFlagLineInnerInterceptor(MybatisPlusProperties mybatisPlusProperties, DataPermissionHandlerImpl.DelLineHandler delLineHandler) {
//        this.mybatisPlusProperties = mybatisPlusProperties;
//        this.delLineHandler = delLineHandler;
//    }
//
//    @Override
//    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
//        if (InterceptorIgnoreHelper.willIgnoreTenantLine(ms.getId())) {
//            return;
//        }
//        if (SqlParserHelper.getSqlParserInfo(ms)) {
//            return;
//        }
//        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
//        mpBs.sql(parserSingle(mpBs.sql(), null));
//    }
//
//    @Override
//    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
//        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
//
//    }
//
//    @Override
//    protected void processSelect(Select select, int index, String sql, Object obj) {
//        processSelectBody(select.getSelectBody());
//        List<WithItem> withItemsList = select.getWithItemsList();
//        if (!CollectionUtils.isEmpty(withItemsList)) {
//            withItemsList.forEach(this::processSelectBody);
//        }
//    }
//
//    protected void processSelectBody(SelectBody selectBody) {
//        if (selectBody == null) {
//            return;
//        }
//        if (selectBody instanceof PlainSelect) {
//            processPlainSelect((PlainSelect) selectBody);
//        } else if (selectBody instanceof WithItem) {
//            WithItem withItem = (WithItem) selectBody;
//            processSelectBody(withItem.getSelectBody());
//        } else {
//            SetOperationList operationList = (SetOperationList) selectBody;
//            if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
//                operationList.getSelects().forEach(this::processSelectBody);
//            }
//        }
//    }
//
//    @Override
//    protected void processInsert(Insert insert, int index, String sql, Object obj) {
//
//    }
//
//    /**
//     * update 语句处理
//     */
//    @Override
//    protected void processUpdate(Update update, int index, String sql, Object obj) {
//
//    }
//
//    /**
//     * delete 语句处理
//     */
//    @Override
//    protected void processDelete(Delete delete, int index, String sql, Object obj) {
//
//    }
//
//    /**
//     * 处理 PlainSelect
//     */
//    protected void processPlainSelect(PlainSelect plainSelect) {
//        FromItem fromItem = plainSelect.getFromItem();
//        Expression where = plainSelect.getWhere();
//        processWhereSubSelect(where);
//        if (fromItem instanceof Table) {
//            Table fromTable = (Table) fromItem;
//            if (!delLineHandler.ignoreTable(fromTable.getName())) {
//                plainSelect.setWhere(builderExpression(where, fromTable));
//            }
//        } else {
//            processFromItem(fromItem);
//        }
//        List<Join> joins = plainSelect.getJoins();
//        if (joins != null && joins.size() > 0) {
//            joins.forEach(j -> {
//                processJoin(j);
//                processFromItem(j.getRightItem());
//            });
//        }
//    }
//
//    /**
//     * 处理where条件内的子查询
//     * <p>
//     * 支持如下:
//     * 1. in
//     * 2. =
//     * 3. >
//     * 4. <
//     * 5. >=
//     * 6. <=
//     * 7. <>
//     * 8. EXISTS
//     * 9. NOT EXISTS
//     * <p>
//     * 前提条件:
//     * 1. 子查询必须放在小括号中
//     * 2. 子查询一般放在比较操作符的右边
//     *
//     * @param where where 条件
//     */
//    protected void processWhereSubSelect(Expression where) {
//        if (where == null) {
//            return;
//        }
//        if (where instanceof FromItem) {
//            processFromItem((FromItem) where);
//            return;
//        }
//        if (where.toString().indexOf("SELECT") > 0) {
//            // 有子查询
//            if (where instanceof BinaryExpression) {
//                // 比较符号 , and , or , 等等
//                BinaryExpression expression = (BinaryExpression) where;
//                processWhereSubSelect(expression.getLeftExpression());
//                processWhereSubSelect(expression.getRightExpression());
//            } else if (where instanceof InExpression) {
//                // in
//                InExpression expression = (InExpression) where;
//                ItemsList itemsList = expression.getRightItemsList();
//                if (itemsList instanceof SubSelect) {
//                    processSelectBody(((SubSelect) itemsList).getSelectBody());
//                }
//            } else if (where instanceof ExistsExpression) {
//                // exists
//                ExistsExpression expression = (ExistsExpression) where;
//                processWhereSubSelect(expression.getRightExpression());
//            } else if (where instanceof NotExpression) {
//                // not exists
//                NotExpression expression = (NotExpression) where;
//                processWhereSubSelect(expression.getExpression());
//            } else if (where instanceof Parenthesis) {
//                Parenthesis expression = (Parenthesis) where;
//                processWhereSubSelect(expression.getExpression());
//            }
//        }
//    }
//
//    /**
//     * 处理子查询等
//     */
//    protected void processFromItem(FromItem fromItem) {
//        if (fromItem instanceof SubJoin) {
//            SubJoin subJoin = (SubJoin) fromItem;
//            if (subJoin.getJoinList() != null) {
//                subJoin.getJoinList().forEach(this::processJoin);
//            }
//            if (subJoin.getLeft() != null) {
//                processFromItem(subJoin.getLeft());
//            }
//        } else if (fromItem instanceof SubSelect) {
//            SubSelect subSelect = (SubSelect) fromItem;
//            if (subSelect.getSelectBody() != null) {
//                processSelectBody(subSelect.getSelectBody());
//            }
//        } else if (fromItem instanceof ValuesList) {
//        } else if (fromItem instanceof LateralSubSelect) {
//            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
//            if (lateralSubSelect.getSubSelect() != null) {
//                SubSelect subSelect = lateralSubSelect.getSubSelect();
//                if (subSelect.getSelectBody() != null) {
//                    processSelectBody(subSelect.getSelectBody());
//                }
//            }
//        }
//    }
//
//    /**
//     * 处理联接语句
//     */
//    protected void processJoin(Join join) {
//        if (join.getRightItem() instanceof Table) {
//            Table fromTable = (Table) join.getRightItem();
//            if (delLineHandler.ignoreTable(fromTable.getName())) {
//                // 过滤退出执行
//                return;
//            }
//            join.setOnExpression(builderExpression(join.getOnExpression(), fromTable));
//        }
//    }
//
//    /**
//     * 处理条件
//     */
//    protected Expression builderExpression(Expression currentExpression, Table table) {
//        EqualsTo equalsTo = new EqualsTo();
//        equalsTo.setLeftExpression(this.getAliasColumn(table));
//        String notDel = Optional.ofNullable(mybatisPlusProperties).map(MybatisPlusProperties::getGlobalConfig).
//                map(GlobalConfig::getDbConfig).map(GlobalConfig.DbConfig::getLogicNotDeleteValue).orElse("0");
//        equalsTo.setRightExpression(new StringValue(notDel));
//        if (currentExpression == null) {
//            return equalsTo;
//        }
//        if (currentExpression instanceof OrExpression) {
//            return new AndExpression(new Parenthesis(currentExpression), equalsTo);
//        } else {
//            return new AndExpression(currentExpression, equalsTo);
//        }
//    }
//
//    /**
//     * 租户字段别名设置
//     * <p>tenantId 或 tableAlias.tenantId</p>
//     *
//     * @param table 表对象
//     * @return 字段
//     */
//    protected Column getAliasColumn(Table table) {
//        StringBuilder column = new StringBuilder();
//        if (table.getAlias() != null) {
//            column.append(table.getAlias().getName()).append(StringPool.DOT);
//        }
//        String delFiled = Optional.ofNullable(mybatisPlusProperties).map(MybatisPlusProperties::getGlobalConfig).
//                map(GlobalConfig::getDbConfig).map(GlobalConfig.DbConfig::getLogicDeleteField).orElse("del_flag");
//        column.append(delFiled);
//        return new Column(column.toString());
//    }
//
//
//    /**
//     * 首字母小写
//     *
//     * @param str
//     * @return
//     */
//    private String lowerFirstCase(String str) {
//        char[] chars = str.toCharArray();
//        //首字母小写方法，大写会变成小写，如果小写首字母会消失
//        chars[0] += 32;
//        return String.valueOf(chars);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        PropertyMapper.newInstance(properties)
//                .whenNotBlack("mybatisPlusProperties", ClassUtils::newInstance, this::setMybatisPlusProperties);
//    }
//}
//
//
