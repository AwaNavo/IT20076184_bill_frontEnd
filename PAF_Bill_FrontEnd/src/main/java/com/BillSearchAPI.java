package com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BillSearchAPI
 */
@WebServlet("/BillSearchAPI")
public class BillSearchAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Search in form
		String output = BillRepository.getBillBasedMonthYear(request.getParameter("accountNoSearch"),
				 											  request.getParameter("monthSearch"),
															  request.getParameter("yearSearch"));
		response.getWriter().write(output);
	}

}
