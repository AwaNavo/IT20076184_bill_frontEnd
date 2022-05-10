package com;

public class Bill {
	
	private int billID;
	private String accountNo;
	private String month;
	private String year;
	private int consumeUnits;
	private double totalAmount;
	
	public Bill() {
		
	}

	public Bill(int billID, String accountNo, String month, String year, int consumeUnits, double totalAmount) {
		super();
		this.billID = billID;
		this.accountNo = accountNo;
		this.month = month;
		this.year = year;
		this.consumeUnits = consumeUnits;
		this.totalAmount = totalAmount;
	}

	public int getBillID() {
		return billID;
	}

	public void setBillID(int billID) {
		this.billID = billID;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getConsumeUnits() {
		return consumeUnits;
	}

	public void setConsumeUnits(int consumeUnits) {
		this.consumeUnits = consumeUnits;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
}
