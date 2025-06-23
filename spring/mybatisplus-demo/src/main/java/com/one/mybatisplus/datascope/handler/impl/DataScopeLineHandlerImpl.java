package com.one.mybatisplus.datascope.handler.impl;


import com.one.mybatisplus.datascope.DataScopeConstant;
import com.one.mybatisplus.datascope.handler.DataScopeLineHandler;
import com.one.mybatisplus.entity.TbUser;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;

/**
 * @author one
 */
@AllArgsConstructor
public class DataScopeLineHandlerImpl implements DataScopeLineHandler {


    @Override
    public ExpressionList getDataSecurityList() {
        //当前登录的用户
        TbUser user = new TbUser();
        user.setId(100L);
        //仅本人
        return new ExpressionList( new StringValue(user.getId()+"") );
    }

    @Override
    public String getDataSecurityColumn() {
        // 获取数据权限:仅本人
        return DataScopeConstant.USER_FIELD;
    }

    @Override
    public boolean isInterceptTable(String tableName) {
        return true;
    }
}
