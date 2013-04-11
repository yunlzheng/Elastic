package com.icp.monitor.displayer.jmx;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.servlet.AsyncContext;

public class JMXMemAsyncContextQueueWriter{

	/** 
     * AsyncContext 队列
     */ 
	private Queue<AsyncContext> queue = new ConcurrentLinkedQueue<AsyncContext>(); 
     
    public static final BlockingQueue<String> MESSAGE_QUEUE = new LinkedBlockingDeque<String>();
     
	public JMXMemAsyncContextQueueWriter(Queue<AsyncContext> queue){
		
		this.queue = queue;
		Thread notifierThread = new Thread(notifierRunnable); 
        notifierThread.start(); 
        
        Thread messageThread = new Thread(sendMessage);
        messageThread.start();
		
	}
	private Runnable sendMessage = new Runnable(){

		int count = 0;
		public void run() {
			
			try {
				
				System.out.println("send Message "+count);
				MESSAGE_QUEUE.put("count:"+(count++));
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
		}
		
	};
	
	private Runnable notifierRunnable = new Runnable(){

		public void run() {
			
			boolean done = false;
			while(!done){
				
				String message = null;
				try {
					
					message = MESSAGE_QUEUE.take();
					System.out.println("take message:"+message);
					for(AsyncContext ac:queue){
						
						try{
							
							PrintWriter acWriter = ac.getResponse().getWriter();
							System.out.println("write message:"+message);
							acWriter.println(message);
							acWriter.flush();
							
						}catch (Exception e) {
							
							e.printStackTrace();
							queue.remove(ac);
							
						}
						
					}
					
				} catch (InterruptedException e) {
					
					done=true;
					e.printStackTrace();
				}
				
			}
			
		}
		
		
	};
	
	public void close() throws IOException{
		
		for(AsyncContext ac:queue){
			ac.getResponse().getWriter().close();
		}
		
	}
	

}
