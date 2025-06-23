package com.one.nettyadv.server.asyncpro;

import com.one.nettyadv.vo.MyMessage;

/**
 * @author ：Mark老师
 * @description ：消息转任务处理器
 */
public interface ITaskProcessor {

    Runnable execAsyncTask(MyMessage msg);

}
