package com.icp.monitor.displayer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.icp.monitor.displayer.jmx.JMXMemAsyncContextQueue;

@WebServlet(asyncSupported=true,urlPatterns={"/async"})
public class JMXAsyncServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html;charset=UTF-8"); 
        resp.setHeader("Cache-Control", "private"); 
        resp.setHeader("Pragma", "no-cache"); 
        resp.setCharacterEncoding("UTF-8"); 
        PrintWriter writer = resp.getWriter(); 
        writer.println("<!-- Comet is a programming technique that enables web  servers to send data to the client without having any need for the client to request it. -->\n"); 
        writer.flush();
        final AsyncContext ac = req.startAsync(); 
        ac.setTimeout(10 * 60 * 1000);
        
        ac.addListener(new AsyncListener() { 
        	
            public void onComplete(AsyncEvent event) throws IOException { 
            	JMXMemAsyncContextQueue.MEM_ASYNC_CONTEXT_QUEUE.remove(ac); 
            } 

            public void onTimeout(AsyncEvent event) throws IOException { 
            	JMXMemAsyncContextQueue.MEM_ASYNC_CONTEXT_QUEUE.remove(ac); 
            } 

            public void onError(AsyncEvent event) throws IOException { 
            	JMXMemAsyncContextQueue.MEM_ASYNC_CONTEXT_QUEUE.remove(ac); 
            } 

            public void onStartAsync(AsyncEvent event) throws IOException { 
            } 
        
        }); 
        
        JMXMemAsyncContextQueue.MEM_ASYNC_CONTEXT_QUEUE.add(ac); 
        
        
	}

	
	
}
