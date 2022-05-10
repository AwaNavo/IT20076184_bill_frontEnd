package com;
import java.sql.*;
import java.text.DecimalFormat;

public class BillRepository {
	
	private static Connection con = null;
	private static DecimalFormat df = new DecimalFormat("0.00");
	

	//reading a bill 
	public static String getBillBasedMonthYear (String accountNo,String month,String year){
		String query = "select * from Bill where accountNo = ? and month = ? and year = ?";
		String output = "";
		
		
		try {
			con = DBConnect.getDBConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, accountNo);
			ps.setString(2, month);
			ps.setString(3, year);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				int ID = rs.getInt(1);
				String aNo = rs.getString(2);
				String mon = rs.getString(3);
				String yr = rs.getString(4);
				int consumeUnits = rs.getInt(5);
				double totalAmount = rs.getDouble(6);
				String AmountString = df.format(totalAmount);
				
				String newBill = "";
						newBill += "<div class='billCard card'>";
						newBill += "<div class='card-body'>";
						newBill += "<h5 class='card-title'> Account No: " + aNo + "</h5><br/>";
						newBill	+= "<p class='card-text'> <b>Year:</b> " + yr + "</p>";
						newBill += "<p class='card-text'> <b>Month:</b> " + mon + "</p>";
						newBill	+= "<p class='card-text'> <b>Consume Units:</b> " + consumeUnits + "</p>";
						newBill	+= "<p class='card-text'> <b>Payment Amount:</b> Rs. "+ AmountString + "</p><br/>";
						
						newBill	+= "<input type='button' name='btnUpdate' id='btnUpdate' class='btnUpdate btn btn-primary btn-sm mr-5' value='Update' data-billid='" + ID + "'>";
						newBill	+= "<input type='button' name='btnRemove' id='btnRemove' class='btnRemove btn btn-danger btn-sm' value='Remove' data-billid='" + ID + "'>";
						newBill	+= "</div> </div>";
				
				output = "{\"status\":\"success\", \"data\": \"" + newBill + "\"}"; 
								
			}
			else {
				output = "{\"status\":\"error\", \"data\": \"No Result Found\"}"; 
			}
			
			ps.close();
			con.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Error while reading bills based on month & date");
			output = "{\"status\":\"error\", \"data\": \"Error while reading the bill.\"}"; 
		}
		return output;
		
	}
	
	//calculating total amount to be paid based on given consume units
	public static double getTotalPaymentAmount(int consumeUnits) {
		double totalAmount = 0;
		double unitPrice= 0;
		double unitPrice_firstPart = 7.85; //first units 28 (0-28)
		double unitPrice_secondPart = 7.85; //units (29-56)
		double unitPrice_thirdPart = 10.0; //units (57-84)
		
		//implement logic to calculate total amount
		if(consumeUnits >= 85 ) {
			unitPrice = 28;
			totalAmount = unitPrice * consumeUnits;
			return totalAmount;
		}
		else {
			int remainder = consumeUnits % 28;
			int ans = consumeUnits / 28;
			
			if(ans == 1 && remainder == 0 || ans == 0) {
		
				if(ans == 0) {
					totalAmount = remainder * unitPrice_firstPart;
				}
				else {
					totalAmount = unitPrice_firstPart * 28;
				}
			}
			
			else if ((ans == 1 && remainder != 0) ||( ans == 2 && remainder == 0)) {
				
				if(ans == 1 && remainder != 0) {
					totalAmount = (unitPrice_firstPart * 28) + (remainder * unitPrice_secondPart);
				}
				else {
				
					totalAmount = (unitPrice_firstPart * 28) + (unitPrice_secondPart * 28);
				}
			}
			else { // units 57-84
				
				totalAmount = (unitPrice_firstPart * 28) + (unitPrice_secondPart * 28) + (remainder * unitPrice_thirdPart);
			}
		}
		
		return totalAmount;
	}
	
	//check duplication bills for insert
	public static boolean isBillValid(String accountNo,String month, String year) {
		String query = "select * from Bill where accountNo = ? and month = ? and year = ?";
		boolean isValid = false;
		
		try {
			con = DBConnect.getDBConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, accountNo);
			ps.setString(2, month);
			ps.setString(3, year);
			
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				isValid = true;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Error while checking validation for the insert of the bill");
		}
		return isValid;
	}
	
	//adding new bill
	public static String addNewBill(String accountNo, String month, String year, int consumeUnits) {
		
		double totalAmount = 0.0;
		String query = "insert into Bill values (?,?,?,?,?,?)";
		String output = "";
		
		//checking the bill is already exist or not
		boolean isValid = isBillValid(accountNo, month, year);
		
		if(isValid == false) {
			System.out.println("Bill is already exists");
			output = "{\"status\":\"error\", \"data\": \"Bill is already exists..\"}";
			return output;
		}
		
		totalAmount = getTotalPaymentAmount(consumeUnits);	
		try {
			con = DBConnect.getDBConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, 0);
			ps.setString(2, accountNo);
			ps.setString(3, month);
			ps.setString(4, year);
			ps.setInt(5, consumeUnits);
			ps.setDouble(6,totalAmount);
			
			int noRows = ps.executeUpdate();
			
			if(noRows > 0) {
				output = "{\"status\":\"success\", \"data\": \"Bill is successfully added..\"}"; 
			}
			else {
				output = "{\"status\":\"error\", \"data\": \"Error while adding a new bill..\"}"; 
			}
			ps.close();
			con.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Error while adding a new bill");
			output = "{\"status\":\"error\", \"data\": \"Error while adding a new bill..\"}"; 
		}
			
	    return output;
	}
	
	//check duplication bills for UPDATE
	public static boolean isBillValidForUpdate(int billID,String accountNo,String month, String year) {
		String query = "select * from Bill where accountNo = ? and month = ? and year = ? and billID != ?";
		boolean isValid = false;
		
		try {
			con = DBConnect.getDBConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, accountNo);
			ps.setString(2, month);
			ps.setString(3, year);
			ps.setInt(4, billID);
			
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				isValid = true;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Error while checking validation for update of the bill");
		}
		return isValid;
	}
	
	//update bill 
	public static String updateBill(int billID, String accountNo, String month, String year, int consumeUnits) {
		
		double totalAmount = 0.0;
		String query = "update Bill set accountNo = ? , month = ? , year = ? , consumeUnits = ?, totalAmount = ? where billID = ?";
		String output = "";
		
		//checking the bill to be updated is already exist or not
		boolean isValid = isBillValidForUpdate(billID,accountNo, month, year);
		
		if(isValid == false) {
			System.out.println("Bill is already exists.");
			output = "{\"status\":\"error\", \"data\": \"Bill is already exists..\"}"; 
			return output;
		}
		
		totalAmount = getTotalPaymentAmount(consumeUnits);
		try {
			con = DBConnect.getDBConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, accountNo);
			ps.setString(2, month);
			ps.setString(3, year);
			ps.setInt(4, consumeUnits);
			ps.setDouble(5,totalAmount);
			ps.setInt(6, billID);
			
			int noRows = ps.executeUpdate();
			
			if(noRows > 0) {
				output = "{\"status\":\"success\", \"data\": \"Bill is successfully updated..\"}"; 
			}
			else {
				output = "{\"status\":\"error\", \"data\": \"Error while updating bill..\"}"; 
			}
			ps.close();
			con.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Error while updating the bill");
			output = "{\"status\":\"error\", \"data\": \"Error while updating bill..\"}"; 
		}
		
		return output;
		
	}
	
	//delete bill
	public static String deleteBill(int billID) {
		
		String output = "";
		String query = "delete from Bill where billID = ? ";
		int noRows = 0;
		
		try {
			con = DBConnect.getDBConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, billID);
			noRows = ps.executeUpdate();
			
			if(noRows > 0) {
				output = "{\"status\":\"success\", \"data\": \"Bill is successfully deleted..\"}"; 
			}
			else{
				output = "{\"status\":\"error\", \"data\": \"Error while deleting bill..\"}"; 
			}
				
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Error while deleting the bill");
			output = "{\"status\":\"error\", \"data\": \"Error while deleting bill..\"}"; 
		}
		
		return output;
		
	}
	
	
}
