
function editModeForm(){
	//form enabled
	$("#form :input").prop( "disabled", false );
	$(".btn-group button").removeClass("disabled");
	
	
	//icon
	$("#editModeIcon").show();
	$("#viewModeIcon").hide();
	
	//Status
	$("#editStatus").show();
	$("#viewStatus").hide();
	
	//info
	$("#info").show();
	
	//details
	$(".edit").show();
	$(".view").hide();
	
	//buttons
	$("#saveCancelButtons").show();
	
}

function viewModeForm(){
	//form disabled
	$("#form :input").prop( "disabled", true );
	
	//icon
	$("#editModeIcon").hide();
	$("#viewModeIcon").show();
	
	//status
	$("#editStatus").hide();
	$("#viewStatus").show();
	
	//info
	$("#info").hide();
	
	//details
	$(".edit").hide();
	$(".view").show();
	
	//buttons
	$("#saveCancelButtons").hide();
}