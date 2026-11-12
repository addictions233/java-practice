package com.one.threadlocal;

/**
 * 在使用线程池（如 ThreadPoolExecutor）执行异步任务时，
 * 主线程的上下文信息（例如用户身份、请求追踪ID等，通常存于 ThreadLocal）无法自动传递到子线程。
 * 为解决此问题，可使用装饰器模式对任务 task 进行包装，在任务执行前后自动传递和清理上下文。
 */
public class ContextAwareRunnable implements Runnable {
    private final Runnable target;
    private final String context; // 存储主线程的上下文

    public ContextAwareRunnable(Runnable target) {
        this.target = target;
        // 捕获当前线程（主线程）的上下文 capture
        this.context = ContextHolder.getContext();
    }

    /**
     * 解决方案： 自定义线程池，然后通过 装饰器模式 包装 Runnable 或 Callable，
     * 在任务提交时“快照”主线程上下文，在子线程执行前恢复，执行后清理——实现上下文的跨线程传递。
     */
    @Override
    public void run() {
        // 对当前上下文进行快照
        String originalContext = ContextHolder.getContext();
        try {
            // 子线程设置主线程的上下文
            ContextHolder.setContext(context);
            target.run(); // 执行原任务
        } finally {
            // 恢复子线程原有上下文
            ContextHolder.setContext(originalContext);
        }
    }

}