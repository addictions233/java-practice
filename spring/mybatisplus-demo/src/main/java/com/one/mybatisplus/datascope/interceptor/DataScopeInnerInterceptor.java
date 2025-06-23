//package com.one.mybatisplus.datascope.interceptor;
//
//import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
//import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
//import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
//import com.one.mybatisplus.datascope.handler.DataScopeLineHandler;
//import net.sf.jsqlparser.expression.BinaryExpression;
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.expression.Parenthesis;
//import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
//import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
//import net.sf.jsqlparser.expression.operators.relational.InExpression;
//import net.sf.jsqlparser.expression.operators.relational.ItemsList;
//import net.sf.jsqlparser.schema.Column;
//import net.sf.jsqlparser.schema.Table;
//import net.sf.jsqlparser.statement.select.*;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//
//import java.sql.SQLException;
//import java.util.List;
//
///**
// * @Description 数据权限拦截处理
// */
//public class DataScopeInnerInterceptor extends JsqlParserSupport implements InnerInterceptor {
//
//    private DataScopeLineHandler dataScopeLineHandler;
//
//    public DataScopeInnerInterceptor(DataScopeLineHandler dataScopeLineHandler) {
//        this.dataScopeLineHandler = dataScopeLineHandler;
//    }
//
//    @Override
//    public void beforeQuery(Executor executor, MappedStatement ms,
//                            Object parameter, RowBounds rowBounds,
//                            ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
//        if (InterceptorIgnoreHelper.willIgnoreTenantLine(ms.getId())){
//            return;
//        }
//        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
//        mpBs.sql(parserSingle(mpBs.sql(), null));
//    }
//
//
//    @Override
//    protected void processSelect(Select select, int index,String sql, Object obj) {
//        processSelectBody(select.getSelectBody());
//        List<WithItem> withItemsList = select.getWithItemsList();
//        if (!CollectionUtils.isEmpty(withItemsList)) {
//            withItemsList.forEach(this::processSelectBody);
//        }
//    }
//
//    protected void processSelectBody(SelectBody selectBody) {
//        if (selectBody instanceof PlainSelect) {
//            processPlainSelect((PlainSelect) selectBody);
//        } else if (selectBody instanceof WithItem) {
//            WithItem withItem = (WithItem) selectBody;
//            if (withItem.getSelectBody() != null) {
//                processSelectBody(withItem.getSelectBody());
//            }
//        } else {
//            SetOperationList operationList = (SetOperationList) selectBody;
//            if (operationList.getSelects() != null && !operationList.getSelects().isEmpty()) {
//                operationList.getSelects().forEach(this::processSelectBody);
//            }
//        }
//    }
//
//    /**
//     * 追加 SelectItem
//     *
//     * @param selectItems SelectItem
//     */
//    protected void appendSelectItem(List<SelectItem> selectItems) {
//        if (CollectionUtils.isEmpty(selectItems)) {
//            return;
//        }
//        if (selectItems.size() == 1) {
//            SelectItem item = selectItems.get(0);
//            if (item instanceof AllColumns || item instanceof AllTableColumns) {
//                return;
//            }
//        }
//        selectItems.add(new SelectExpressionItem(new Column(dataScopeLineHandler.getDataSecurityColumn())));
//    }
//
//    /**
//     * 处理 PlainSelect
//     */
//    protected void processPlainSelect(PlainSelect plainSelect) {
//        FromItem fromItem = plainSelect.getFromItem();
//        if (fromItem instanceof Table) {
//            Table fromTable = (Table) fromItem;
//            if (dataScopeLineHandler.isInterceptTable(fromTable.getName())) {
//                //#1186 github
//                plainSelect.setWhere(builderExpression(plainSelect.getWhere(), fromTable));
//            }
//        } else {
//            processFromItem(fromItem);
//        }
//        List<Join> joins = plainSelect.getJoins();
//        if (joins != null && !joins.isEmpty()) {
//            joins.forEach(j -> {
//                processJoin(j);
//                processFromItem(j.getRightItem());
//            });
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
//            logger.debug("Perform a subquery, if you do not give us feedback");
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
//            if (dataScopeLineHandler.isInterceptTable(fromTable.getName())) {
//                join.setOnExpression(builderExpression(join.getOnExpression(), fromTable));
//            }
//        }
//    }
//
//    /**
//     * 处理条件
//     */
//    protected Expression builderExpression(Expression currentExpression, Table table) {
//        InExpression inExpression = new InExpression();
//        inExpression.setLeftExpression(this.getAliasColumn(table));
//        inExpression.setRightItemsList(dataScopeLineHandler.getDataSecurityList());
//        if (currentExpression == null) {
//            return inExpression;
//        }
//        if (currentExpression instanceof BinaryExpression) {
//            BinaryExpression binaryExpression = (BinaryExpression) currentExpression;
//            doExpression(binaryExpression.getLeftExpression());
//            doExpression(binaryExpression.getRightExpression());
//        } else if (currentExpression instanceof InExpression) {
//            InExpression inExp = (InExpression) currentExpression;
//            ItemsList rightItems = inExp.getRightItemsList();
//            if (rightItems instanceof SubSelect) {
//                processSelectBody(((SubSelect) rightItems).getSelectBody());
//            }
//        }
//        if (currentExpression instanceof OrExpression) {
//            return new AndExpression(new Parenthesis(currentExpression), inExpression);
//        } else {
//            return new AndExpression(currentExpression, inExpression);
//        }
//    }
//
//    protected void doExpression(Expression expression) {
//        if (expression instanceof FromItem) {
//            processFromItem((FromItem) expression);
//        } else if (expression instanceof InExpression) {
//            InExpression inExp = (InExpression) expression;
//            ItemsList rightItems = inExp.getRightItemsList();
//            if (rightItems instanceof SubSelect) {
//                processSelectBody(((SubSelect) rightItems).getSelectBody());
//            }
//        }
//    }
//
//    /**
//     * 数据权限字段别名设置
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
//        column.append(dataScopeLineHandler.getDataSecurityColumn());
//        return new Column(column.toString());
//    }
//
//
//}
//
