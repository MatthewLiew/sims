// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable();
  
});

$(document).ready(function() {
	$('#stockinouthistory').DataTable({
		  "order": [[ 2, "desc" ]]
	  });
});

$(document).ready(function() {
	$('#stocktransferhistory').DataTable({
		  "order": [[ 7, "desc" ]]
	  });
});

$(document).ready(function() {
  $('#settingTable').DataTable({
      "paging":   false,
      "ordering": false,
      "info":     false,
      "searching":   false
  });
});

//$(document).ready(function() {
//  $('#stockin').DataTable({
//      "paging":   false,
//      "ordering": false,
////      "info":     false
//  });
//});
//
//$(document).ready(function() {
//  $('#stockout').DataTable({
//      "paging":   false,
//      "ordering": false,
////	      "info":     false
//  });
//});