package org.javacoo.crawler.core.thread;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.javacoo.crawler.core.data.Task;

import com.rekoe.crawler.core.constants.Constants;

/**
 * 线程池服务类
 * @author javacoo
 * @since 2011-11-17
 */
public class ThreadPoolService{
	/** 默认线程池大小 */  
    public static final int  DEFAULT_POOL_SIZE  = Constants.THREAD_NUM;  
    /** 默认一个任务的超时时间，单位为毫秒  */  
    public static final long DEFAULT_TASK_TIMEOUT = Constants.TASK_TIMEOUT; 
    /** ExecutorService对象 */
    private ExecutorService  executorService; 
    /** 线程池大小 */ 
    private int poolSize = DEFAULT_POOL_SIZE;  
	
    /**
     * 根据给定大小创建线程池
     */
    public ThreadPoolService(int poolSize) {
        setPoolSize(poolSize);
    }
    /**
     * 使用线程池中的线程来执行任务
     */
    public void execute(Runnable task) {
        executorService.execute(task);
    }
    /**
     * 使用线程池中的线程来执行任务
     */
    public Task submit(Callable<Task> task) {
    	try {
			return executorService.submit(task).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
    }
    /**
     * 在线程池中执行所有给定的任务并取回运行结果，使用默认超时时间
     * 
     * @see #invokeAll(List, long)
     */
    public List<Task> invokeAll(List<ProcessorCallableThread> tasks) {
        return invokeAll(tasks, DEFAULT_TASK_TIMEOUT * tasks.size());
    }
    /**
     * 在线程池中执行所有给定的任务并取回运行结果
     * 
     * @param timeout 以毫秒为单位的超时时间，小于0表示不设定超时
     * @see java.util.concurrent.ExecutorService#invokeAll(java.util.Collection)
     */
    public List<Task> invokeAll(List<ProcessorCallableThread> tasks, long timeout) {
        List<Task> taskList = new ArrayList<Task>(tasks.size());
        try {
            List<Future<Task>> futures = null;
            if (timeout < 0) {
                futures = executorService.invokeAll(tasks);
            } else {
                futures = executorService.invokeAll(tasks, timeout, TimeUnit.MILLISECONDS);
            }
            for (Future<Task> future : futures) {
                try {
                	taskList.add(future.get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return taskList;
    }

	/** 
     * 关闭当前ExecutorService 
     */  
    public void shutdown() {  
        if (executorService != null && !executorService.isShutdown()) {  
            try {  
                executorService.awaitTermination(Constants.TASK_TIMEOUT, TimeUnit.MILLISECONDS);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            executorService.shutdown();  
        }  
    }  
    /** 
     * 强行关闭当前ExecutorService 
     */  
    public void shutdownNow() {  
        if (executorService != null && !executorService.isShutdown()) {  
            try {  
                executorService.awaitTermination(Constants.TASK_TIMEOUT, TimeUnit.MILLISECONDS);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            executorService.shutdownNow(); 
        }  
    }  
  
    /** 
     * 关闭当前ExecutorService，随后根据poolSize创建新的ExecutorService 
     */  
    public void createExecutorService() {  
    	shutdown();  
        executorService = Executors.newFixedThreadPool(poolSize);  
    }  
  
    /** 
     * 调整线程池大小 
     * @see #createExecutorService() 
     */  
    public void setPoolSize(int poolSize) {  
        this.poolSize = poolSize;  
        createExecutorService();  
    }  
	/**
	 * ExecutorService对象是否关闭
	 * @return
	 */
	public boolean isShutdown(){
		return executorService.isShutdown();
	}
}
