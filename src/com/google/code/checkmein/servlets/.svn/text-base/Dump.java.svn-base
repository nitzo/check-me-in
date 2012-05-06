package com.google.code.checkmein.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.*;


import sun.misc.BASE64Decoder;










@SuppressWarnings("serial")
public class Dump extends HttpServlet {
	
	
		
	public static String dump(HttpServletRequest request){
		
	
	    StringBuilder out = new StringBuilder();
	    
	    String title = "Reading All Request Parameters (GET)";
	    out.append( "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
	                "<TABLE BORDER=1 ALIGN=CENTER>\n" +
	                "<TR BGCOLOR=\"#FFAD00\">\n" +
	                "<TH>Parameter Name<TH>Parameter Value(s)");
	    
	    Enumeration paramNames = request.getParameterNames();
	    
	    while(paramNames.hasMoreElements()) {
	      String paramName = (String)paramNames.nextElement();
	      out.append("<TR><TD>" + paramName + "\n<TD>");
	      String[] paramValues = request.getParameterValues(paramName);
	      if (paramValues.length == 1) {
	        String paramValue = paramValues[0];
	        if (paramValue.length() == 0)
	          out.append("<I>No Value</I>");
	        else
	          out.append(paramValue);
	      } else {
	        out.append("<UL>");
	        for(int i=0; i<paramValues.length; i++) {
	          out.append("<LI>" + paramValues[i]);
	        }
	        out.append("</UL>");
	      }
	    }
	    out.append("</TABLE>\n");

	    return out.toString();
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
     	throws IOException {


		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String title = "Reading All Request Parameters (GET)";
	    out.println( "<BODY BGCOLOR=\"#FDF5E6\">\n" +
	                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
	                "<TABLE BORDER=1 ALIGN=CENTER>\n" +
	                "<TR BGCOLOR=\"#FFAD00\">\n" +
	                "<TH>Parameter Name<TH>Parameter Value(s)");
	    Enumeration paramNames = request.getParameterNames();
	    
	    while(paramNames.hasMoreElements()) {
	      String paramName = (String)paramNames.nextElement();
	      out.println("<TR><TD>" + paramName + "\n<TD>");
	      String[] paramValues = request.getParameterValues(paramName);
	      if (paramValues.length == 1) {
	        String paramValue = paramValues[0];
	        if (paramValue.length() == 0)
	          out.print("<I>No Value</I>");
	        else
	          out.print(paramValue);
	      } else {
	        out.println("<UL>");
	        for(int i=0; i<paramValues.length; i++) {
	          out.println("<LI>" + paramValues[i]);
	        }
	        out.println("</UL>");
	      }
	    }
	    out.println("</TABLE>\n");
	    

		Cookie[] cookies = request.getCookies();
		
		if (cookies != null){
			out.println("<TABLE>\n");
			for (Cookie c : cookies){
				 out.println("<TR><TD>" + c.getName() + "\n<TD>");
				 out.println(c.getValue());
			}
			out.println("</TABLE>\n");
			
		}
		out.println("</BODY></HTML>");
		
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
 	throws ServletException, IOException {


	response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Reading All Request Parameters (POST)";
    out.println( "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                "<TABLE BORDER=1 ALIGN=CENTER>\n" +
                "<TR BGCOLOR=\"#FFAD00\">\n" +
                "<TH>Parameter Name<TH>Parameter Value(s)");
    Enumeration paramNames = request.getParameterNames();
    
    while(paramNames.hasMoreElements()) {
      String paramName = (String)paramNames.nextElement();
      out.println("<TR><TD>" + paramName + "\n<TD>");
      String[] paramValues = request.getParameterValues(paramName);
      if (paramValues.length == 1) {
        String paramValue = paramValues[0];
        if (paramValue.length() == 0)
          out.print("<I>No Value</I>");
        else
          out.print(paramValue);
      } else {
        out.println("<UL>");
        for(int i=0; i<paramValues.length; i++) {
          out.println("<LI>" + paramValues[i]);
        }
        out.println("</UL>");
      }
    }
    
    out.println("</TABLE>\n");
    
//    BufferedReader reader = request.getReader();
//    
//    String nextLine = reader.readLine();
//    
//    while (nextLine != null){
//    	out.println(nextLine);
//    	nextLine = reader.readLine();
//    }
    
//    out.println(request.getParameter("name"));
    
    out.println("\n</BODY></HTML>");

	
		
	
	
	
}
}
