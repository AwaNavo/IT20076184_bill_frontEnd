<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Bill Management</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<link rel="stylesheet" href="Views/bills.css">
		<script src="Components/jquery-3.6.0.min.js"></script>
		<script src="Components/bills.js"></script>
		
	</head>
	
	<body>
	
	<br/>
	<br/>
	<div class = "row">
		<div class = "col-md-5 offset-md-3">
		<h1>Bill Management</h1>
		</div>
	</div>
	<br/>
	
	<!-- FORM -->
	<div class = "row" style = "background-color:#EEEDE7 !important; box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;">
		<div class = "col-md-5 offset-md-3">
			<form id = "formBill" name ="formBill" >
				 <br/><br/>				
				 <h6>Account No: </h6>
				 <input name="accountNo" id ="accountNo" type="text" class="form-control" placeholder="Enter Account No"><br/>
				 <h6>Year: </h6>
				 <input name="year" id = "year" type="text" class="form-control" placeholder="Enter Year: ex: 2022"><br/>
				 <h6>Month: </h6>
				 <input name="month" id = "month" type="text" class="form-control" placeholder="Enter Month : ex: January"><br/>
				 <h6>Consume Units: </h6>
				 <input name="consumeUnits" id ="consumeUnits" type="text" class="form-control" placeholder="Enter consume units"><br/>
			 	 <br/>
			 	 <input name="btnSave" id ="btnSave" type="button" value="Save" class="btn btn-primary col-md-4"><br/>
				
				<!-- Hidden id value for update -->
				<input type="hidden" id="hidBillIDSave" name="hidBillIDSave" value="">
				<br/><br/>
			</form>
		</div>
		
		<!-- ALERT -->
		<br/>
		<!-- Error Alert -->
		<div id="alertError" class="alert alert-danger col-md-3 offset-md-3"></div><br/>	
		
		<!-- Success Alert -->				
		<div id="alertSuccess" class="alert alert-success col-md-3 offset-md-3"></div>
		<br/>
	</div>
	

	<br/>
	<br/>
	
	<div class = "row">
		<div class = "col-md-5 offset-md-3">
			<h4>Search Bill...  <i class="fa fa-search" aria-hidden="true"></i></h4>
		</div>
	</div>
	<br/>
	<img src="Images/Bg1.png"  class="img-fluid col-md-5 offset-md-8" alt="Responsive image" style = "width:120px !important; height:80px !important;">
	<br/><br/>
	<!-- Search Field -->
  	<div class = "row">
		<br/>
		<div class = "col-md-6 offset-md-3">
		<form id = "searchFormBill" name ="searchFormBill" style = "background-color:#EEEDE7 !important; padding: 20px !important; box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;">			
			<div class = "form-row">	
				<div class="form-group col-md-3 ">
			   		<label for="fiedl1">AccountNo: </label>
			   		<input type="text" class="form-control" id="accountNoSearch" name = "accountNoSearch" placeholder="ACC/NO">
			 	</div>
			 	<div class="form-group col-md-2">
			   		<label for="fiedl2">Year: </label>
			   		<input type="text" class="form-control" id="yearSearch" name = "yearSearch" placeholder="YYYY">
			 	</div>
			 	<div class="form-group col-md-3">
			   		<label for="fiedl3">Month: </label>
			   		<input type="text" class="form-control" id="monthSearch" name = "monthSearch" placeholder="January">
			 	</div>
			 	<div class = "form-group col-md-1">
			 		<label for="fiedl4"> </label>
			 		<input name="btnSearch" id ="btnSearch" type="button" value="Search" class="btn btn-secondary">
			 	</div>
			 </div>
		</form>
		</div>
	</div> 
	

	
	
	<br/>
	<!-- Search Error Alert -->
	<div id="alertSearchError" class="alert alert-danger col-md-3 offset-md-3"></div><br/>
	<div id="alertSearchSuccess" class="alert alert-success col-md-4 offset-md-3"></div><br/>
	
	<!-- Card Result -->			
	<br>
	<br/>
	<div class = "row">	
		<div class = "col-md-8 offset-md-3" id = "divBillGrid"> </div>
	</div>
	<br/><br/>
	<!-- END -->
	
	</body>
</html>