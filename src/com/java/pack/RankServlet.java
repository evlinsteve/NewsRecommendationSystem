package com.java.pack;


import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RankServlet
 */

public class RankServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RankServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String user = request.getParameter("user");
		System.out.println(user);
			
			RankedSearch vsm = new RankedSearch();
				    
	 	 		//Keyword search
		//	String[] query = {"football"};
			
			String[] filename = new String[10];
			String[] query = user.split(" ");
			vsm.rankSearch(query);
			
			filename  = vsm.getFileName();
			if(filename !=null)
			{
		System.out.println(filename.length);
		for(int i=0;i<filename.length-1;i++)
		{
			System.out.println(filename[i]);
		}
		
			request.setAttribute("Fileoutput", filename );
		request.getRequestDispatcher("index.jsp").forward(request, response);  
			}
			else
			{
				filename = new String[1];
				filename[0] = "No Search Result";
				request.setAttribute("Fileoutput", filename);
				request.getRequestDispatcher("index.jsp").forward(request, response);  
			}
	
	}

}
