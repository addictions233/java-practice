package com.one.resulthandler;

import com.one.bean.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

@Slf4j
public class MyResultHandler implements ResultHandler<SysUser> {

    /**
     * 对取出的结果进行统一处理
     * @param resultContext 结果上下文
     */
    @Override
    public void handleResult(ResultContext<? extends SysUser> resultContext) {
        int resultCount = resultContext.getResultCount();
        log.info("count:" + resultCount);
        SysUser resultObject = resultContext.getResultObject();
        log.info("sysUser:{}", resultObject);
    }
}
