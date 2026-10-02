package com.hoxcloud.multi_threading_concurrency;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcurrencyThread implements Runnable{
    private final int taskId;

    public ConcurrencyThread(int taskId) {
        this.taskId = taskId;
    }


    @Override
    public void run() {

        log.info("Executing Task #{}", taskId);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
