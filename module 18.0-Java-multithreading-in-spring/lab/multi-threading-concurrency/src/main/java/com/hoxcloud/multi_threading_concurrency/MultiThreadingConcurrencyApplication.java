package com.hoxcloud.multi_threading_concurrency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class MultiThreadingConcurrencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiThreadingConcurrencyApplication.class, args);

		// Cast upfront to access ThreadPoolExecutor metrics directly
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
				3,                                    // corePoolSize
				4,                                    // maximumPoolSize
				2L,                                   // keepAliveTime
				TimeUnit.SECONDS,                     // unit
				new ArrayBlockingQueue<Runnable>(3),  // workQueue capacity
				new ThreadPoolExecutor.DiscardOldestPolicy()
		);

		ConcurrencyThread cThread = new ConcurrencyThread();

		for (int i = 1; i < 10; i++) {
			log.info("Submitting Task #{} to executor", i);

			// Submits Runnable task to worker thread pool
			executor.execute(cThread);

			// Log accurate pool metrics
			log.info("Task #{} submitted | Active Threads: {} | Current Pool Size: {} | Queue Size: {}",
					i,
					executor.getActiveCount(),
					executor.getPoolSize(),
					executor.getQueue().size()
			);
		}

		// Graceful shutdown: allows submitted tasks to finish executing
		executor.shutdown();
	}
}
