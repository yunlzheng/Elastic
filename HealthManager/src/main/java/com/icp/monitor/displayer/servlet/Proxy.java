package com.icp.monitor.displayer.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Proxy extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String query = null;
		
		PrintWriter out = response.getWriter();
		query = request.getParameter("URL");
		if (query == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Missing URL parameter");
			return;
		}

		try {
			// query = URLDecoder.decode(query,"utf-8");
		} catch (Exception exception) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"URL decode error " + exception);
			return;
		}

		try {
			URL url = new URL(query);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));

			String line;
			while ((line = in.readLine()) != null)
				out.println(line);
			out.flush();
		} catch (IOException exception) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Exception: "
					+ exception);
		}
	}
}
