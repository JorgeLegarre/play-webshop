
function editModeForm(){
	//form enabled
	$("#form :input").prop( "disabled", false );
	$(".btn-group button").removeClass("disabled");
	
	
	//icon
	$("#editModeIcon").show();
	$("#viewModeIcon").hide();
	
	//buttons
	$("#saveCancelButtons").show();
	
}

function viewModeForm(){
	//form disabled
	$("#form :input").prop( "disabled", true );
	
	//icon
	$("#editModeIcon").hide();
	$("#viewModeIcon").show();
	
	//buttons
	$("#saveCancelButtons").hide();
}