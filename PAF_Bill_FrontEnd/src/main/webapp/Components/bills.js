
//implement both cotroller and client mode in same file
//CONTROLLER=====================================

$(document).ready(function(){
	if($("#alertSuccess").text().trim() == "" ){
		$("#alertSuccess").hide();
	}
	
	$("#alertError").hide();	
	$("#alertSearchError").hide();
	$("#alertSearchSuccess").hide();
	$("#divBillGrid").show();
});

//SAVE----------
$(document).on("click","#btnSave",function(event){
	 
	//clear alerts in form
	 $("#alertSuccess").text(""); 
	 $("#alertSuccess").hide(); 
	 $("#alertError").text(""); 
	 $("#alertError").hide();

	//clear alerts in search form
	 $("#alertSearchError").text(""); 
	 $("#alertSearchError").hide();
	 $("#alertSearchSuccess").text(""); 
	 $("#alertSearchSuccess").hide();
	

	//form validations
	var status = validateBillForm();
	if(status != true){
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	//if valid --return TRUE -- sumbit the form
	var type = ($("#hidBillIDSave").val() == "" ? "POST": "PUT");
	
	//ajax for insert / update
	$.ajax(
		{
			url: "BillAPI",
			type: type,
			data: $("#formBill").serialize(),
			dataType: "text",
			complete: function(response,status){
				
				onBillSaveComplete(response.responseText,status);
			}
		}
	);
});

function onBillSaveComplete(response,status){
	
	if(status == "success"){
		
		//get the result set from response
		var resultSet = JSON.parse(response); 
	
		if (resultSet.status.trim() == "success"){
			 
			 $("#alertSuccess").text("Successfully saved."); 
			 $("#alertSuccess").show(); 
		
		}
		else if (resultSet.status.trim() == "error") { 
		 	
			$("#alertError").text(resultSet.data); 
		 	$("#alertError").show(); 
		}
	}
	else if(status == "error"){
		   
	   $("#alertError").text("Error while saving."); 
	   $("#alertError").show(); 
	}
	else{
		
		$("#alertError").text("Unknown error while saving.."); 
 		$("#alertError").show(); 
	}
	
	//reset hidItemID
	$("#hidBillIDSave").val("");
	
	//reset form values
	$("#formBill")[0].reset();
	
};

//SEARCH----------------
$(document).on("click","#btnSearch",function(event){
	 
	//clear alerts in search form
	 $("#alertSearchError").text(""); 
	 $("#alertSearchError").hide();
	 $("#alertSearchSuccess").text(""); 
	 $("#alertSearchSuccess").hide();

	//clear alerts in form
	 $("#alertSuccess").text(""); 
	 $("#alertSuccess").hide(); 
	 $("#alertError").text(""); 
	 $("#alertError").hide();
	
	//hide the displayed card
	$("#divBillGrid").hide();
	
	//clear older form values if exist
	$("#formBill")[0].reset();

	//form validations
	var status = validateSearchForm();
	if(status != true){
		$("#alertSearchError").text(status);
		$("#alertSearchError").show();
		return;
	}
	
	//if valid --return TRUE -- sumbit the form
	var type = "POST";
	//ajax for search
	$.ajax(
		{
			url: "BillSearchAPI",
			type: type,
			data: $("#searchFormBill").serialize(),
			dataType: "text",
			complete: function(response,status){
				
				onBillSearchComplete(response.responseText,status);
			}
		}
	);
	
	

});

//respond handler for searching
function onBillSearchComplete(response,status){
	
	if(status == "success"){
		console.log("error");
		
		//get the result set from response
		var resultSet = JSON.parse(response); 
		
	
		if (resultSet.status.trim() == "success"){
			 
			//display bill card
			$("#divBillGrid").html(resultSet.data);
			$("#divBillGrid").show();
 
		
		}
		else if (resultSet.status.trim() == "error") { 
		 	
			$("#alertSearchError").text(resultSet.data); 
		 	$("#alertSearchError").show(); 
		}
	}
	else if(status == "error"){
		   
	   $("#alertSearchError").text("Error while searching."); 
	   $("#alertSearchError").show(); 
	}
	else{
		
		$("#alertSearchError").text("Unknown error while searching.."); 
 		$("#alertSearchError").show(); 
	}
	
	
	//reset search form values
	$("#searchFormBill")[0].reset();
	
}

//UPDATE------------
$(document).on("click",".btnUpdate",function(event){
	
	$("#hidBillIDSave").val($(this).data("billid"));
	$("#accountNo").val($(this).closest("div").find('h5:eq(0)').text().trim().substring(12)); 
 	$("#year").val($(this).closest("div").find('p:eq(0)').text().trim().substring(6)); 
 	$("#month").val($(this).closest("div").find('p:eq(1)').text().trim().substring(7)); 
 	$("#consumeUnits").val($(this).closest("div").find('p:eq(2)').text().trim().substring(14)); 
	
	//hide the displayed card and displayed alert
	$("#divBillGrid").hide();
	$("#alertSearchSuccess").text("Required bill details update to the form.. ");
	$("#alertSearchSuccess").show();
});

//DELETE-------------
$(document).on("click",".btnRemove",function(event){
	
	//ajax request for delete
	$.ajax(
		{
			url: "BillAPI",
			type: "DELETE",
			data: "billID=" + $(this).data("billid"),
			dataType:"text",
			complete: function(response,status){
				
				onBillDeleteComplete(response.responseText,status);
			}
		}
	);
	
});

//delete response handler
function onBillDeleteComplete(response,status){
	
	if(status == "success"){
		
		var resultSet = JSON.parse(response);
		
		if(resultSet.status.trim() == "success"){
			
			//hide the displayed card
			$("#divBillGrid").hide();
			//display success message
			$("#alertSearchSuccess").text("Successfully deleted."); 
 			$("#alertSearchSuccess").show(); 
 			
		}
		else if(resultSet.status.trim() == "error"){
			 
			$("#alertSearchError").text(resultSet.data); 
 			$("#alertSearchError").show(); 
		}
		
	}
	else if(status == "error"){
		
		$("#alertSearchError").text("Error while deleting."); 
 		$("#alertSearchError").show(); 
		
	}
	else{
		
		$("#alertSearchError").text("Unknown Error while deleting."); 
 		$("#alertSearchError").show();
		
	}
}



//CLIENT MODEL======================================

function validateBillForm(){
	
	//ACCOUNT NO
	if($("#accountNo").val().trim() == ""){
		return "Insert Account Number";
	}
	
	//YEAR
	if($("#year").val().trim() == ""){
		return "Insert Year";
	}
	//is year numeric value
	var tmpYear = $("#year").val().trim();
	if(!$.isNumeric(tmpYear)){
		return "Insert a numeric value for year";
	}
	
	//is year has 4 digits only
	if($("#year").val().length != 4){
		return "Insert year with four digits format";
	}
	
	//MONTH
	if($("#month").val().trim() == ""){
		return "Insert Month";
	}
	
	//MONTH within range
	var monthArry = ["January","February","March","April","May","June","July","August","Septemeber","November","December"];
	if($.inArray( $("#month").val().trim(),monthArry) == -1 ){
		return "Insert month in valid format";
	}
	
	//CONSUME UNITS
	if($("#consumeUnits").val().trim() == ""){
		return "Insert Consume Units" ;
	}
	
	//is numeric value
	var tmpConsumeUnits = $("#consumeUnits").val().trim();
	if(!$.isNumeric(tmpConsumeUnits)){
		return "Insert a numeric value for consumeUnits";
	}
	
	//is valid numeric value
	if( parseInt($("#consumeUnits").val().trim()) <= 0){
		return "Insert valid consme units";
	}
	
	return true;
	
}

function validateSearchForm(){
	
	//account no
	if($("#accountNoSearch").val().trim() == ""){
		return "Account Number is required";
	}
	
	//year
	if($("#yearSearch").val().trim() == ""){
		return "Year is required";
	}
	
	//is year numberic
	var tmpYear = $("#yearSearch").val().trim();
	if(!$.isNumeric(tmpYear)){
		return "Year should be numeric";
	}
	
	//is year has 4 digits only
	if($("#yearSearch").val().length != 4){
		return "Year should be four digits format";
	}
	
	//month
	if($("#monthSearch").val().trim() == ""){
		return "Month is required";
	}
	
	//is month valid range
	var monthArry = ["January","February","March","April","May","June","July","August","Septemeber","November","December"];
	if($.inArray( $("#monthSearch").val().trim(),monthArry) == -1 ){
		return "Month should be valid format - Ex: January";
	}
	
	return true;
}






















