//package com.one.mybatisplus.datascope.interceptor;
//
//import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
//import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
//import com.baomidou.mybatisplus.core.toolkit.*;
//import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
//import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
//import com.baomidou.mybatisplus.extension.toolkit.PropertyMapper;
//import com.one.mybatisplus.datascope.handler.ChannelLineHandler;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import net.sf.jsqlparser.expression.BinaryExpression;
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.expression.NotExpression;
//import net.sf.jsqlparser.expression.Parenthesis;
//import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
//import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
//import net.sf.jsqlparser.expression.operators.relational.*;
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
//import org.apache.ibatis.mapping.SqlCommandType;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Properties;
//
///**
// * 区分系统来源,目前查询条件统一是 租户+系统渠道 来隔离数据
// */
//@Data
//@NoArgsConstructor
//@ToString(callSuper = true)
//@EqualsAndHashCode(callSuper = true)
//public class ChannelInnerInterceptor extends JsqlParserSupport implements InnerInterceptor {
//
//    private ChannelLineHandler channelLineHandler;
//
//    private MappedStatement mappedStatement;
//
//    public ChannelInnerInterceptor(ChannelLineHandler channelLineHandler) {
//        this.channelLineHandler = channelLineHandler;
//    }
//
//    @Override
//    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
//        mappedStatement = ms;
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
//        this.mappedStatement = mpSh.mappedStatement();
//        SqlCommandType sct = mappedStatement.getSqlCommandType();
//        if (sct == SqlCommandType.INSERT || sct == SqlCommandType.UPDATE || sct == SqlCommandType.DELETE) {
//            if (InterceptorIgnoreHelper.willIgnoreTenantLine(mappedStatement.getId())) {
//                return;
//            }
//            if (SqlParserHelper.getSqlParserInfo(mappedStatement)) {
//                return;
//            }
//            PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
//            mpBs.sql(parserMulti(mpBs.sql(), null));
//        }
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
//        String msId = mappedStatement.getId();
//        if (org.apache.commons.lang3.StringUtils.contains(msId, "UhrSalaryGroupManagerMapper")) {
//            return;
//        }
//        if (channelLineHandler.ignoreTable(insert.getTable().getName())) {
//            // 过滤退出执行
//            return;
//        }
//        List<Column> columns = insert.getColumns();
//        if (CollectionUtils.isEmpty(columns)) {
//            // 针对不给列名的insert 不处理
//            return;
//        }
//        String sysSourceColumn = channelLineHandler.getSysSourceColumn();
//        if (columns.stream().map(Column::getColumnName).anyMatch(i -> i.equals(sysSourceColumn))) {
//            // 针对已给出租户列的insert 不处理
//            return;
//        }
//        columns.add(new Column(channelLineHandler.getSysSourceColumn()));
//        Select select = insert.getSelect();
//        if (select != null) {
//            this.processInsertSelect(select.getSelectBody());
//        } else if (insert.getItemsList() != null) {
//            // fixed github pull/295
//            ItemsList itemsList = insert.getItemsList();
//            if (itemsList instanceof MultiExpressionList) {
//                ((MultiExpressionList) itemsList).getExprList().forEach(el -> el.getExpressions().add(channelLineHandler.getSysSource()));
//            } else {
//                ((ExpressionList) itemsList).getExpressions().add(channelLineHandler.getSysSource());
//            }
//        } else {
//            throw ExceptionUtils.mpe("Failed to process multiple-table update, please exclude the tableName or statementId");
//        }
//    }
//
//    /**
//     * update 语句处理
//     */
//    @Override
//    protected void processUpdate(Update update, int index, String sql, Object obj) {
//        final Table table = update.getTables().get(0);
//        String msId = mappedStatement.getId();
//        if (org.apache.commons.lang3.StringUtils.contains(msId, "UhrSalaryGroupManagerMapper")) {
//            return;
//        }
//        if (channelLineHandler.ignoreTable(table.getName())) {
//            // 过滤退出执行
//            return;
//        }
//        update.setWhere(this.andExpression(table, update.getWhere()));
//    }
//
//    /**
//     * delete 语句处理
//     */
//    @Override
//    protected void processDelete(Delete delete, int index, String sql, Object obj) {
//        String msId = mappedStatement.getId();
//        if (org.apache.commons.lang3.StringUtils.contains(msId, "UhrSalaryGroupManagerMapper")) {
//            return;
//        }
//        if (channelLineHandler.ignoreTable(delete.getTable().getName())) {
//            // 过滤退出执行
//            return;
//        }
//        delete.setWhere(this.andExpression(delete.getTable(), delete.getWhere()));
//    }
//
//    /**
//     * delete update 语句 where 处理
//     */
//    protected BinaryExpression andExpression(Table table, Expression where) {
//        //获得where条件表达式
//        EqualsTo equalsTo = new EqualsTo();
//        equalsTo.setLeftExpression(this.getAliasColumn(table));
//        equalsTo.setRightExpression(channelLineHandler.getSysSource());
//        if (null != where) {
//            if (where instanceof OrExpression) {
//                return new AndExpression(equalsTo, new Parenthesis(where));
//            } else {
//                return new AndExpression(equalsTo, where);
//            }
//        }
//        return equalsTo;
//    }
//
//
//    /**
//     * 处理 insert into select
//     * <p>
//     * 进入这里表示需要 insert 的表启用了多租户,则 select 的表都启动了
//     *
//     * @param selectBody SelectBody
//     */
//    protected void processInsertSelect(SelectBody selectBody) {
//        PlainSelect plainSelect = (PlainSelect) selectBody;
//        FromItem fromItem = plainSelect.getFromItem();
//        if (fromItem instanceof Table) {
//            Table fromTable = (Table) fromItem;
//            plainSelect.setWhere(builderExpression(plainSelect.getWhere(), fromTable));
//            appendSelectItem(plainSelect.getSelectItems());
//        } else if (fromItem instanceof SubSelect) {
//            SubSelect subSelect = (SubSelect) fromItem;
//            appendSelectItem(plainSelect.getSelectItems());
//            processInsertSelect(subSelect.getSelectBody());
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
//        selectItems.add(new SelectExpressionItem(new Column(channelLineHandler.getSysSourceColumn())));
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
//            if (!channelLineHandler.ignoreTable(fromTable.getName())) {
//                //#1186 github
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
//            //
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
//            String msId = mappedStatement.getId();
//            if (org.apache.commons.lang3.StringUtils.contains(msId, "UhrSalaryGroupManagerMapper")) {
//                return;
//            }
//            if (channelLineHandler.ignoreTable(fromTable.getName())) {
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
//        equalsTo.setRightExpression(channelLineHandler.getSysSource());
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
//        column.append(channelLineHandler.getSysSourceColumn());
//        return new Column(column.toString());
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        PropertyMapper.newInstance(properties)
//                .whenNotBlack("channelLineHandle", ClassUtils::newInstance, this::setChannelLineHandler);
//    }
//}
