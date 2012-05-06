package com.google.code.checkmein.servlets;

import java.io.IOException;

import java.io.PrintWriter;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.checkmein.db.Event;
import com.google.code.checkmein.db.PMF;

@SuppressWarnings("serial")
public class StatisticsServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		String eventID = req.getParameter("eventid");
		//System.out.println(eventID);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Event event= pm.getObjectById(Event.class,eventID);
		//event.setChart();
		//pm.close();
		
		//String color="#F5F5F5";
		String titel="http://img411.imageshack.us/img411/7934/title2x.png";
			//"http://img835.imageshack.us/img835/8852/title1a.png"; 
			//"http://img140.imageshack.us/img140/4688/titlet.png";
		String persent_png= "http://img688.imageshack.us/img688/5209/persrnt.png";
		String logo="http://img9.imageshack.us/img9/6003/checkmeinv10.jpg"; 
			//"http://img593.imageshack.us/img593/2337/logopy.png";
		String font="Tahoma";
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title> Statistic </title>");
		out.println("</head>");
		out.println("<body>");
		//out.println("<body bgcolor=" + color + ">" );
		out.println("<basefont face="+font+">");
		out.println("&nbsp <img src="+ logo +">");
		out.println("<center><img size=80% src="+ titel +"> &nbsp &nbsp &nbsp <img src="+ persent_png +"> </center><br><br>");
		out.println("<br><br><h2><center> " + event.getAttending() + " &nbsp People \"Attending\" the event</center></h2>");
		out.println("<h2><center> "  + event.getCheckedIn() + " &nbsp people actually attended the event</center></h2><br>");
		//out.println("<br><center><img src="+event.getChart()+"></center>");
		out.println("</body>");
		out.println("</html>");
		out.close();
		
		pm.close();
	}
	
}
