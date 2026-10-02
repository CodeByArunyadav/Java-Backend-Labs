package com.hoxcloud.multi_threading_concurrency;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcurrencyThread implements Runnable{


    @Override
    public void run() {

        log.info("Thread in Running state");
        try {
           Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
