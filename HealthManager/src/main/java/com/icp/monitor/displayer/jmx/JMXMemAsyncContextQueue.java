package com.icp.monitor.displayer.jmx;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.servlet.AsyncContext;

public class JMXMemAsyncContextQueue {

	public static final Queue<AsyncContext> MEM_ASYNC_CONTEXT_QUEUE = new ConcurrentLinkedQueue<AsyncContext>(); 
	private JMXMemAsyncContextQueueWriter queueWriter = new JMXMemAsyncContextQueueWriter(MEM_ASYNC_CONTEXT_QUEUE);
	
}
