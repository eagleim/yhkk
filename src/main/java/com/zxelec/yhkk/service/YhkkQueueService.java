package com.zxelec.yhkk.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
//import java.util.concurrent.linkedblockingqueue;

@Component
public class YhkkQueueService {
    private LinkedBlockingQueue<List<Map<String,?>>> queue = new LinkedBlockingQueue<List<Map<String,?>>>(2);

    public void add(){
        queue.o
    }

}
