package cn.dong.util.concurrent;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProductQueue <T> {

	private final T[] items;

	private final Lock lock = new ReentrantLock();

	private Condition notFull = lock.newCondition();

	private Condition notEmpty = lock.newCondition();

	//
	private int head, tail, count;

	@SuppressWarnings("unchecked")
	public ProductQueue(int maxSize) {
		Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0].getClass();
		items = (T[])Array.newInstance(entityClass, maxSize);
	}

	public ProductQueue() {
		this(10);
	}

	public void put(T t) throws InterruptedException {
		lock.lock();
		try {
			while (count == getCapacity()) {
				notFull.await();
			}
			items[tail] = t;
			if (++tail == getCapacity()) {
				tail = 0;
			}
			++count;
			notEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public T take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0) {
				notEmpty.await();
			}
			T ret = items[head];
			items[head] = null;// GC
			//
			if (++head == getCapacity()) {
				head = 0;
			}
			--count;
			notFull.signalAll();
			return ret;
		} finally {
			lock.unlock();
		}
	}

	public int getCapacity() {
		return items.length;
	}

	public int size() {
		lock.lock();
		try {
			return count;
		} finally {
			lock.unlock();
		}
	}

}
