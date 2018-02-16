package org.usfirst.frc4048.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class WorkQueue extends LinkedBlockingQueue<String> {
	private static final long serialVersionUID = 1L;

	public void append(final String id) throws InterruptedException {
		this.put(id);
	}

	public String getNext() throws InterruptedException {
		return this.poll(1,  TimeUnit.SECONDS);
	}
}
