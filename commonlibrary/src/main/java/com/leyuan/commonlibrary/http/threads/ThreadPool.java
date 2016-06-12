package com.leyuan.commonlibrary.http.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	static final Object DATA_LOCK = new Object();
	private static final int MAX_THREADS = 10;
	private ThreadWorkers[] threads;
	private static ThreadPool pool;
	List<Runnable> tasks;
	private static ExecutorService fixedThreadPool;

	public static ThreadPool getInstance() {
		if (pool == null) {
			synchronized (ThreadPool.class) {
				if (pool == null) {
					pool = new ThreadPool();
				}
			}
		}
		return pool;
	}
	
	public static ExecutorService getExecutorServiceInstance() {
		if (fixedThreadPool == null) {
			synchronized (ThreadPool.class) {
				if (fixedThreadPool == null) {
					fixedThreadPool = Executors.newFixedThreadPool(3);
				}
			}
		}
		return fixedThreadPool;
	}

	private ThreadPool() {
		threads = new ThreadWorkers[MAX_THREADS];
		tasks = new ArrayList<Runnable>();
	}

	public void addTask(Runnable task) {
		synchronized (DATA_LOCK) {
			createThread();
			tasks.add(task);
			DATA_LOCK.notify();
		}

	}

	public void addTaskToFront(Runnable task) {
		synchronized (DATA_LOCK) {
			createThread();
			tasks.add(0, task);
			DATA_LOCK.notify();
		}

	}

	private void createThread() {
		if (hasFreeTask()) {
			return;
		}
		for (int i = 0; i < threads.length; i++) {
			ThreadWorkers wk = threads[i];

			if (wk == null || !wk.isWorking) {
				wk = new ThreadWorkers();
				wk.start();
				threads[i] = wk;
				break;
			}
		}
	}

	private boolean hasFreeTask() {
		for (int i = 0; i < threads.length; i++) {
			if (threads[i] != null && threads[i].isFree) {
				return true;
			}
		}
		return false;
	}

}
