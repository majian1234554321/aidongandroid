package com.example.aidong.widget.ninephotoview.util;

/**
 * Created by 大灯泡 on 2016/11/1.
 * <p>
 * 简单的对象池
 */
public class SimpleObjectPool<T> {

    private T[] objectsPool;
    private int curPointer = -1;

    public SimpleObjectPool(int size) {
        objectsPool = (T[]) new Object[size];
    }

    public synchronized T get() {
        if (curPointer == -1 || curPointer > objectsPool.length) return null;
        T obj = objectsPool[curPointer];
        objectsPool[curPointer] = null;
        curPointer--;
        return obj;
    }

    public synchronized boolean put(T t) {
        if (curPointer == -1 || curPointer < objectsPool.length - 1) {
            curPointer++;
            objectsPool[curPointer] = t;
            return true;
        }
        return false;
    }

    public void clearPool() {
        for (int i = 0; i < objectsPool.length; i++) {
            objectsPool[i] = null;
        }
        curPointer = -1;
    }
}
