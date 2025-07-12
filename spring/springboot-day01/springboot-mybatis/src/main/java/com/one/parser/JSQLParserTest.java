package com.one.parser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * JSqlParser是一个用于解析SQL语句的Java库，它可以将SQL语句转换为Java 对象树，并提供了一些方法来操作和修改SQL语句。
 * 允许你以编程的方式对SQL语句进行分析, 提取其中的各个部分, 并进行修改或生成新的SQL语句。
 * 它支持多种SQL语句类型,如SELECT、INSERT、UPDATE、DELETE等。
 *
 * @description: JSQLParser的解析功能
 * @author: wanjunjie
 * @date: 2025/03/03
 */
public class JSQLParserTest {

    public static void main(String[] args) {
        // 示例 SQL
        String sql = "SELECT * FROM user WHERE status = 'active'";
        Expression expression;

        try {
            expression = CCJSqlParserUtil.parseCondExpression("status = 'inactive'");
            PlainSelect select = (PlainSelect) ((Select) CCJSqlParserUtil.parse(sql)).getSelectBody();
            select.setWhere(expression);

            System.out.println(select); // 输出：SELECT * FROM user WHERE status = 'inactive'
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }

    public static final String SQL = "SELECT DISTINCT u.id, r.role_name, u.user_name, u.sex, u.email " +
            "FROM t_user u " +
            "LEFT JOIN t_role r ON u.role_id = r.id " +
            "WHERE r.role_name = '管理员' " +
            "ORDER BY u.age DESC " +
            "LIMIT 0,10";

    /**
     * 测试 查询SQL 解析
     */
    @Test
    public void sqlParseTest() {
        try {
            Select select = (Select) CCJSqlParserUtil.parse(SQL);
            PlainSelect plainSelect = select.getPlainSelect();
            System.out.println("【DISTINCT 子句】：" + plainSelect.getDistinct());
            System.out.println("【查询字段】：" + plainSelect.getSelectItems());
            System.out.println("【FROM 表】：" + plainSelect.getFromItem());
            System.out.println("【WHERE 子句】：" + plainSelect.getWhere());
            System.out.println("【JOIN 子句】：" + plainSelect.getJoins());
            System.out.println("【LIMIT 子句】：" + plainSelect.getLimit());
            System.out.println("【OFFSET 子句】：" + plainSelect.getOffset());
            System.out.println("【ORDER BY 子句】：" + plainSelect.getOrderByElements());
            System.out.println("--------------------------------------------------------");
            // 取消去重
            plainSelect.setDistinct(null);
            // 修改查询字段为 *
            List<SelectItem<?>> selectItems = new ArrayList<>();
            selectItems.add(new SelectItem<>(new AllColumns()));
            plainSelect.setSelectItems(selectItems);
            // 修改 WHERE 子句
            EqualsTo equalsTo = new EqualsTo();
            equalsTo.setLeftExpression(new Column("u.id"));
            equalsTo.setRightExpression(new LongValue(1));
            plainSelect.setWhere(equalsTo);
            // 修改 LIMIT 子句
            Limit limit = new Limit();
            limit.setRowCount(new LongValue(5));
            limit.setOffset(new LongValue(0));
            plainSelect.setLimit(limit);
            // 修改排序为 u.age ASC
            OrderByElement orderByElement = new OrderByElement();
            orderByElement.setExpression(new Column("u.age"));
            orderByElement.setAsc(true); // 升序
            plainSelect.setOrderByElements(Collections.singletonList(orderByElement));
            System.out.println("【处理后 SQL】" + plainSelect);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }


    public static final String SQL2 = "INSERT INTO t_user (role_id, user_name, email, age, sex, register_time ) VALUES ( 1, 'xw', 'isxuwei@qq.com', 25, '男', '2024-04-12 17:37:18' );";

    /**
     * 测试 插入SQL 解析
     */
    @Test
    public void sqlParseTest2() {
        try {
            Insert insert = (Insert) CCJSqlParserUtil.parse(SQL2);
            System.out.println("【插入目标表】：" + insert.getTable());
            System.out.println("【插入字段】：" + insert.getColumns());
            System.out.println("【插入值】：" + insert.getValues());
            System.out.println("--------------------------------------------------------");
            ExpressionList<Column> columns = insert.getColumns();
            ExpressionList<Expression> values = (ExpressionList<Expression>) insert.getValues().getExpressions();
            // 字段和值是一一对应的，把性别删除掉
            columns.remove(4);
            values.remove(4);
            // 新增一列状态，默认为 create
            columns.add(new Column("status"));
            values.add(new StringValue("create"));
            // 更新年龄字段 +1
            Expression expression = values.get(3);
            LongValue longValue = (LongValue) expression;
            longValue.setValue(longValue.getValue() + 1);
            System.out.println("【处理后 SQL】" + insert);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }

    public static final String SQL3 = "UPDATE t_user SET email = '373675032@qq.com', phone = '10086' WHERE id = 1";

    /**
     * 测试 更新SQL 解析
     */
    @Test
    public void sqlParseTest3() {
        try {
            Update update = (Update) CCJSqlParserUtil.parse(SQL3);
            System.out.println("【更新目标表】：" + update.getTable());
            List<UpdateSet> updateSets = update.getUpdateSets();
            for (UpdateSet updateSet : updateSets) {
                System.out.println("【更新字段】：" + updateSet.getColumns());
                System.out.println("【更新字】：" + updateSet.getValues());
            }
            System.out.println("【更新条件】：" + update.getWhere());
            System.out.println("--------------------------------------------------------");
            // 去掉更新手机号
            updateSets.remove(1);
            // 添加更新字段
            UpdateSet updateSet = new UpdateSet();
            updateSet.add(new Column("update_time"), new LongValue(System.currentTimeMillis()));
            updateSets.add(updateSet);
            // 更新 Where 条件
            AndExpression expression = new AndExpression();
            expression.withLeftExpression(update.getWhere());
            EqualsTo equalsTo = new EqualsTo();
            equalsTo.setLeftExpression(new Column("deleted"));
            equalsTo.setRightExpression(new LongValue(0));
            expression.withRightExpression(equalsTo);
            update.setWhere(expression);
            System.out.println("【处理后 SQL】" + update);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }

}
