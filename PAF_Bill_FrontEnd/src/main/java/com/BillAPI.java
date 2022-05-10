package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/BillAPI")
public class BillAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public BillAPI() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		//inserting in form
		String output = BillRepository.addNewBill(request.getParameter("accountNo"), 
											  request.getParameter("month"), 
											  request.getParameter("year"), 
											  Integer.parseInt(request.getParameter("consumeUnits")));
		response.getWriter().write(output);
		
	}


	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//update form
		Map paras = getParasMap(request);
		String output = BillRepository.updateBill(Integer.parseInt(paras.get("hidBillIDSave").toString()),
												  paras.get("accountNo").toString(), 
												  paras.get("month").toString(), 
												  paras.get("year").toString(),
												  Integer.parseInt(paras.get("consumeUnits").toString()) );
		response.getWriter().write(output);
	}


	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//delete form
		Map paras = getParasMap(request);
		String output = BillRepository.deleteBill(Integer.parseInt(paras.get("billID").toString()));
		
		response.getWriter().write(output);
		
	}
	
	//CUSTOM METHOD FOR PUT & DELETE requests
		private static Map getParasMap(HttpServletRequest request) 
		{ 
			Map<String, String> map = new HashMap<String, String>(); 
			try{ 
				 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
				 String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : ""; 
				 scanner.close(); 
				 
				 String[] params = queryString.split("&"); 
				 
				 for (String param : params) 
				 { 
					 String[] p = param.split("="); 
					 map.put(p[0], p[1]); 
				 } 
				 
			} catch (Exception e) { 
		 
			} 
			
			return map; 
		}

}
