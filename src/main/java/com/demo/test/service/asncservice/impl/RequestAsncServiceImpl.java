package com.demo.test.service.asncservice.impl;

import com.demo.test.queue.RequestQueue;
import com.demo.test.request.Request;
import com.demo.test.service.asncservice.RequestAsncService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

@Service
public class RequestAsncServiceImpl implements RequestAsncService {
    @Override
    public void process(Request request) {
        ArrayBlockingQueue<Request> queue = getRoutingQueue(request.getReaderId());
        try {
            queue.put(request);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }


    /**
     * 根据读者readerId路由到不同的内存队列中去
     * 一个读者不可能同时对不同的文章点赞，只可能10000个有对同个文章点赞，所以以读者id进行路由
     *
     * @param readerId
     * @return
     */
    private ArrayBlockingQueue<Request> getRoutingQueue(Long readerId) {
        RequestQueue requestQueue = RequestQueue.getInstance();
        int h;
        //拿到hash值
        int hash = (readerId == null) ? 0 : (h = readerId.hashCode()) ^ (h >>> 16);
        //对hash值取模，结果一定在0 到requestQueue.getQueueSize之间
        int index = (requestQueue.getQueueSize() - 1) & hash;
        ArrayBlockingQueue<Request> queue = requestQueue.getQueue(index);
        return queue;
    }
}
