// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable();
});

$(document).ready(function() {
  $('.settingTable').DataTable({
      "paging":   false,
      "ordering": false,
      "info":     false
  } );
});