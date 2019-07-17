package com.demo.test.threadPool;

import com.demo.test.queue.RequestQueue;
import com.demo.test.request.Request;
import com.demo.test.thread.WorkThread;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class RequestProcessThreadPool {

    /**
     * 线程池
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);


    public RequestProcessThreadPool() {
        Integer threadPoolSize = 10;
        Integer queueCapacity = 100;
        RequestQueue queues = RequestQueue.getInstance();
        for(int i=0;i<threadPoolSize;i++){
            ArrayBlockingQueue<Request> requestQueue = new ArrayBlockingQueue<Request>(queueCapacity);
            queues.addQueue(requestQueue);
            threadPool.submit(new WorkThread(requestQueue));
        }
        System.out.println("线程池初始化完毕");
    }

    private static class SingleTon{
        static RequestProcessThreadPool instance;
        static{
            instance = new RequestProcessThreadPool();
        }
        static RequestProcessThreadPool getInstance(){
            return instance;
        }
    }

    private static RequestProcessThreadPool getInstance(){
        return SingleTon.getInstance();
    }
}
