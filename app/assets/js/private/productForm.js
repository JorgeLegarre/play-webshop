
function editModeForm(){
	//form enabled
	$("#form :input").prop( "disabled", false );
	$(".btn-group button").removeClass("disabled");
	
	
	//icon
	$("#editModeIcon").show();
	$("#viewModeIcon").hide();
	
	//categories
	$("#editModeCategories").show();
	$("#viewModeCategories").hide();
	
	//buttons
	$("#saveCancelButtons").show();
	
}

function viewModeForm(){
	//form disabled
	$("#form :input").prop( "disabled", true );
	
	//icon
	$("#editModeIcon").hide();
	$("#viewModeIcon").show();
	
	//categories
	$("#editModeCategories").hide();
	$("#viewModeCategories").show();
	
	//buttons
	$("#saveCancelButtons").hide();
}