package com.demo.test.queue;

import com.demo.test.request.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class RequestQueue {
    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<ArrayBlockingQueue<Request>>();

    /**
     * 单例，静态内部内类
     */
    private static class SingleTon{
        static RequestQueue instance;
        static{
            instance = new RequestQueue();
        }
        static RequestQueue getInstance(){
            return instance;
        }
    }

    public static RequestQueue getInstance(){
        return SingleTon.getInstance();
    }


    public void addQueue(ArrayBlockingQueue<Request> queue){
        queues.add(queue);
    }

    /**
     * 获取内存队列的数量
     * @return
     */
    public int getQueueSize(){
        return queues.size();
    }

    /**
     * 获取内存队列
     * @param index
     * @return
     */
    public ArrayBlockingQueue<Request> getQueue(int index){
        return queues.get(index);
    }
}
