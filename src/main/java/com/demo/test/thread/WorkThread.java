package com.demo.test.thread;

import com.demo.test.request.Request;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

public class WorkThread implements Callable<Boolean> {
    private ArrayBlockingQueue<Request> queue;

    public WorkThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            while (true) {
                Request request = queue.take();
                request.process();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
