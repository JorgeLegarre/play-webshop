
function editModeForm(){
	//form enabled
	$("#form :input").prop( "disabled", false );
	$(".btn-group button").removeClass("disabled");
	
	
	//icon
	$("#editModeIcon").show();
	$("#viewModeIcon").hide();
	
	//password confirm
	$("#repassword").show();
	
	//calendar
	$("#editDob").show();
	$("#viewDob").hide();
	
	//buttons
	$("#saveCancelButtons").show();
	
}

function viewModeForm(){
	//form disabled
	$("#form :input").prop( "disabled", true );
	
	//icon
	$("#editModeIcon").hide();
	$("#viewModeIcon").show();
	
	//password confirm
	$("#repassword").hide();
	
	//calendar
	$("#editDob").hide();
	$("#viewDob").show();
	
	//buttons
	$("#saveCancelButtons").hide();
}