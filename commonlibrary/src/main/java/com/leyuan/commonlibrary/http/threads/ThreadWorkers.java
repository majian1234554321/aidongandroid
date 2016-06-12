package com.leyuan.commonlibrary.http.threads;

class ThreadWorkers extends Thread {
	
	private static final int MAX_FREE_TIME = 30000;
	private static int i = 0;
	private ThreadPool pool;
	private Runnable t;
	boolean isWorking;
	boolean isFree;

	public ThreadWorkers() {
		super();
		pool = ThreadPool.getInstance();
		isWorking = true;
		setName("pool_thread:" + (i++));
	}

	@Override
	public void run() {
		while (true) {
			try {
				t = null;
				synchronized (ThreadPool.DATA_LOCK) {
					if (pool.tasks == null || pool.tasks.isEmpty()) {
						isFree = true;
						ThreadPool.DATA_LOCK.wait(MAX_FREE_TIME);
						isFree = false;
						if (pool.tasks == null || pool.tasks.isEmpty()) {
							isWorking = false;
							break;
						}
					} else {
						t = pool.tasks.get(0);
						pool.tasks.remove(0);

					}
				}
				if (t != null) {
					t.run();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
