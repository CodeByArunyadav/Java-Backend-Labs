package com.hoxcloud.multi_threading_concurrency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.*;

@SpringBootApplication
@Slf4j
public class MultiThreadingConcurrencyApplication {


    public static void main(String[] args) {

        SpringApplication.run(
                MultiThreadingConcurrencyApplication.class,
                args
        );

        //threadPoolLearning();

        learnFutureResultToCheckEvenOdd();

        //learnFutureCompletableResultCheck();


    }

    public static void threadPoolLearning() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3,                                    // corePoolSize
                4,                                    // maximumPoolSize
                2L,                                   // keepAliveTime
                TimeUnit.SECONDS,                     // unit
                new ArrayBlockingQueue<>(3),         // queue capacity
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        //ConcurrencyThread cThread = new ConcurrencyThread();

        for (int i = 1; i < 10; i++) {

            log.info("Submitting Task #{} to executor", i);

            ConcurrencyThread cThread = new ConcurrencyThread(i);

            executor.execute(cThread);

            log.info(
                    "Task #{} submitted | Active Threads: {} | Current Pool Size: {} | Queue Size: {}",
                    i,
                    executor.getActiveCount(),
                    executor.getPoolSize(),
                    executor.getQueue().size()
            );
        }

        executor.shutdown();
    }

    public static void learnFutureResultToCheckEvenOdd() {
        // ExecutorService executorService=Executors.newFixedThreadPool(2);
        ThreadPoolExecutor executorService =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        try {

            for (int i = 4; i <= 10; i += 2) {

                int taskId = i;

                log.info("Submitting Task #{} to executor", taskId);

                Future<String> result = executorService.submit(() -> {

                    log.info(
                            "Task #{} started | Thread: {}",
                            taskId,
                            Thread.currentThread().getName()
                    );

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }

                    return (taskId % 2 == 0)
                            ? "Given number is even"
                            : "Given number is odd";
                });

                log.info(
                        "Task #{} submitted | Active Threads: {} | Pool Size: {} | Queue Size: {}",
                        taskId,
                        executorService.getActiveCount(),
                        executorService.getPoolSize(),
                        executorService.getQueue().size()
                );

                String message = result.get();

                log.info(
                        "Task #{} result: {}",
                        taskId,
                        message
                );
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        } catch (ExecutionException e) {
            log.error("Task execution failed", e);

        } finally {
            executorService.shutdown();
        }

    }

    public static void learnFutureCompletableResultCheck() {

        ThreadPoolExecutor executor  =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        for (int i = 4; i <= 10; i += 2) {

            int finalI1 = i;
            CompletableFuture<String> future =
                    CompletableFuture.supplyAsync(() -> {

                        log.info(
                                "Task #{} started | Thread: {} | Active: {} | Pool: {} | Queue: {}",
                                finalI1,
                                Thread.currentThread().getName(),
                                executor.getActiveCount(),
                                executor.getPoolSize(),
                                executor.getQueue().size()
                        );

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            throw new RuntimeException(e);
                        }

                        log.info("Task #{} processing completed", finalI1);

                        return "Hello";

                    }, executor);

            int finalI = i;
            future.thenAccept(result ->
                    log.info(
                            "Task #{} result received | Result: {} | Thread: {}",
                            finalI,
                            result,
                            Thread.currentThread().getName()
                    )
            );
        }
    }
}


