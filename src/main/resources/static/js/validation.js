var BASE_URL = window.location.origin+"/";
var count=0;

/***********  Alert Box Motion Control *************/
$(document).ready (function(){

	$(".alert-info").fadeTo(2000, 500).slideUp(500, function(){
		$(".alert-info").slideUp(500);
	});   
});

/***********  User Capabilities Control *************/
$(document).on("change", ".accessright", function(){
    console.log(this);
    if(this.checked){
    	$(this).parent().find('.checkboxesvalue').val(1);
    } else {
    	$(this).closest("tr").find(".checkboxesvalue").val(0);
    }
});

$(document).on("change", ".actionright", function(){
    if(this.checked){
    	$(this).parent().find('.checkboxesvalue').val(1);
    } else {
    	$(this).parent().find(".checkboxesvalue").val(0);
    }
});

$(document).on("change", ".accessright", function() {
	if(this.checked){
		$(this).closest("tr").find(".actionright").prop('disabled', false);
	} else {
		$(this).closest("tr").find(".actionright").prop('checked', false);
		$(this).closest("tr").find(".actionright").prop('disabled', true);
	}
});

/***********  Duplicate Form *************/
function addmorepartno(){
	
	var source = $(".form:first");
//	var clone = source.html();
	var clone = source.clone();
	
	clone.find(".btn_csv").attr('for',count);
	clone.find(".get_csv").attr("id", count);
	
	clone.find(".autostockin").hide();
	clone.find(".serialno").val("");
	clone.find(".serialno_status").text("");
	clone.find(".quantity").text("");
	clone.find(".modelno").val("");
	clone.find(".upccode").val("");
	clone.find(".customername").val("");
	clone.find(".invoiceno").val("");
	
	clone.appendTo(".clone_form");
	count++;
	return false;
}

/***********  Remove Form *************/
$(document).on("click", ".remove_partno", function() {
    $(this).closest(".clone_form .form").remove();
    return false;
});

/***********  Upload CSV to Form *************/
$(document).on("change", ".get_csv", function(e) {
	
	var object=this;
	var files;
	files = event.target.files;

	var file = e.target.files[0];
	  if (!file) {
	    return;
	  }
	  var reader = new FileReader();
	  reader.onload = function(e) {
		  
	    var contents = e.target.result;
	    console.log(typeof contents);
	    let replace = contents.replace(/\W+/g,"\n");
	    $(object).closest("div").find(".serialno").val(replace);
	    
	    var token = contents.split(/\W+\w/);
	    $(object).closest(".modal-body").find(".quantity").val(token.length);
	    $(object).closest(".form-row").find(".quantity").html(token.length);

	  };
	  reader.readAsText(file);
	  
	  $(this).val("");
});

/***********  Replace comma (,) to space and calculate serial no amount *************/
$(document).on("input", ".serialno", function() {

	let serialno= $(this).val();
	
	let replace = serialno.replace(","," ");
	
	$(this).val(replace);
	
	var token = serialno.split(/\W+\w/);
	
	$(this).closest(".modal-body").find(".quantity").val(token.length);
	$(this).closest(".form-row").find(".quantity").html(token.length);
	
	if(serialno=="")
		$(this).closest(".form-row").find(".quantity").html(" ");
	
    return false;
});

function subloc_select(id, val) {

	var data={ mainlocid:val }
	$.ajax({
		type: "POST",
		url: BASE_URL + "api/getSubLoc",
		contentType: "application/json",
		data: JSON.stringify (data),
		dataType: 'json',
		
		success: function (response) {

//			$("#"+id+" .dept_select").empty();
//			$("#"+id+" .dept_select").append("<option value='0'>Select Department</option>");
			$("#"+id+" .subloc_select").empty();
			$("#"+id+" .subloc_select").append("<option value='0'>Select Sub Location</option>");
			for(var i of response) {
				$("#"+id+" .subloc_select").append("<option value='"+i["sublocid"]+"'>"+i["sublocname"]+"</option>");
			}

		}
	});
}

function id_subloc_select(id, val) {

	var data={ mainlocid:val }
	$.ajax({
		type: "POST",
		url: BASE_URL + "api/getSubLoc",
		contentType: "application/json",
		data: JSON.stringify (data),
		dataType: 'json',
		
		success: function (response) {

//			$("#"+id+" .dept_select").empty();
//			$("#"+id+" .dept_select").append("<option value='0'>Select Department</option>");
			$("."+id+" .subloc_select").empty();
			$("."+id+" .subloc_select").append("<option value='0'>Select Sub Location</option>");
			for(var i of response) {
				$("."+id+" .subloc_select").append("<option value='"+i["sublocid"]+"'>"+i["sublocname"]+"</option>");
			}

		}
	});
}

function trial(form){
	
	let availSerial=$("#"+form+" .serialno_status");
	let newSerial=$("#"+form+" .quantity");
	let autostockin=$("#"+form+" .autostockin");
	let autoaddstock=$("#"+form+" .autoaddstock");
	let outstanding=$("#"+form+" .outstanding");
	let serialno=$("#"+form+" .serialno");

	let isChecked = true;
//	console.log(d.length);
	for(var i=0; i < serialno.length; i++){
//		console.log("d"+i+": "+d[i].value);
//		console.log(b[i].innerHTML);
//		console.log(a[i].innerHTML);
		
		if(parseInt(newSerial[i].innerHTML)>parseInt(availSerial[i].innerHTML)){
			var num=parseInt(newSerial[i].innerHTML)-parseInt(availSerial[i].innerHTML);
			$(autostockin[i]).show();
			$(outstanding[i]).html("Add "+num+" Stock?");
			$(autoaddstock[i]).prop("value", num);
		} else {
			$(autostockin[i]).hide();
			$(autoaddstock[i]).prop("checked", true);
			$(autoaddstock[i]).prop("value", 0);
		}
		
		if(autoaddstock[i].checked==false){
			isChecked=false;
		} 
	}
	
	if(isChecked){
		$("#"+form+" #modal_error").hide();
		return true;
	} else {
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Please tick all checkbox.");
		return false;
	}
//	if((parseInt(b)>parseInt(a))&&(c==0)){
////		console.log(0);
//		var num=parseInt(b)-parseInt(a);
//		$("#"+form+" .outstanding").html("Require "+num+" stock to upload all serial number <br>Add Stock Automatically?");
////		$("#OutstandingS/N").modal({show: true});
//		return false;
//		
//	} else {
////		console.log(1);
//		return true;
//	}
}
$(document).on("change", ".productid", function() {
	
	var current=this;
	var data={ productid: this.value };
	console.log(data);
	$.ajax({
		type: "POST",
		url: BASE_URL + "api/checkSerialAvailability",
		contentType: "application/json",
		data: JSON.stringify (data),
		dataType: 'json',
		
		success: function (response) {
			$(current).closest(".form-row").find(".serialno_status").html(response.status);
		}
	});
});

function reason_option(val) {

	var data={ stocktypeid:val }
	if(val!=0){
	$.ajax({
		type: "POST",
		url: BASE_URL + "api/getReason",
		contentType: "application/json",
		data: JSON.stringify (data),
		dataType: 'json',
		
		success: function (response) {
			$("#reason").show();
			$("#reason").empty();
			$("#reason").append("<option value='0'>Any Stock In Type</option>");
//			$("#"+id+" .subdept_select").empty();
//			$("#"+id+" .subdept_select").append("<option value='0'>Select Sub Department</option>");
			for(var i of response) {
				$("#reason").append("<option value='"+i["reasonid"]+"'>"+i["reason"]+"</option>");
			}

		}
	});
	} else {
		$("#reason").hide();
	}
}

function department_select(id, val) {

	var data={ orgid:val }
	$.ajax({
		type: "POST",
		url: BASE_URL + "api/getDept",
		contentType: "application/json",
		data: JSON.stringify (data),
		dataType: 'json',
		
		success: function (response) {

			$("#"+id+" .dept_select").empty();
			$("#"+id+" .dept_select").append("<option value='0'>Select Department</option>");
			$("#"+id+" .subdept_select").empty();
			$("#"+id+" .subdept_select").append("<option value='0'>Select Sub Department</option>");
			for(var i of response) {
				$("#"+id+" .dept_select").append("<option value='"+i["deptid"]+"'>"+i["deptname"]+"</option>");
			}

		}
	});
}

function subdepartment_select(id, val) {
	
	var data={ deptid:val }
	$.ajax({
		type: "POST",
		url: BASE_URL + "api/getSubDept",
		contentType: "application/json",
		data: JSON.stringify (data),
		dataType: 'json',
		
		success: function (response) {
			$("#"+id+" .subdept_select").empty();
			$("#"+id+" .subdept_select").append("<option value='0'>Select Sub Department</option>");
			if(!jQuery.isEmptyObject(response)){
				$("#"+id+" .subdept_select").append("<option value='0'>No Sub Department</option>");
			}
			for(var i of response) {
				$("#"+id+" .subdept_select").append("<option value='"+i["subdeptid"]+"'>"+i["subdeptname"]+"</option>");
			}
		}
	});
}

function userName(form) {
	var data= { 
			userid: $("#"+form+" .userid").val(),
			username: $("#"+form+" .username").val()
			}
		console.log(data);
	if(!$.isEmptyObject($("#"+form+" .username").val())) {
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkUserName",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		success: function (response) {
 			
 			if(response.flag=="true"){
 				$("#"+form+" .username" ).removeClass("is-invalid");
 				$("#"+form+" .username" ).addClass("is-valid");
 				$("#"+form+" .username_status" ).removeClass("text-danger");
 				$("#"+form+" .username_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .username" ).removeClass("is-valid");
 				$("#"+form+" .username" ).addClass("is-invalid");
 				$("#"+form+" .username_status" ).removeClass("text-success");
 				$("#"+form+" .username_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .username_status" ).html(response.status);
 			$("#"+form+" .username_flag").html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .username_status" ).html("Error");
 			$("#"+form+" .username_flag").html("false");
			}
 		});}else {
 			$("#"+form+" .username" ).removeClass("is-valid");
			$("#"+form+" .username" ).removeClass("is-invalid");
 	 		$("#"+form+" .username_status").html("");
 	 		$("#"+form+" .username_flag" ).html("false");
 	 	}

}

function userForm(form) {
	
	var usernameflag= $("#"+form+" .username_flag").text();
	let username=$("#"+form+" .username").val();

	if(usernameflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("User Name - "+username+" Exists.");
		return false;
	} else {
		$("#"+form+" #modal_error").hide();
		return true;
	}
}

function orgCode(form) {
	var data= { 
			orgid: $("#"+form+" .orgid").val(),
			orgcode: $("#"+form+" .orgcode").val()
			}
		if(!$.isEmptyObject($("#"+form+" .orgcode").val())) {
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkOrgCode",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
	 			$("#"+form+" .orgcode" ).removeClass("is-invalid");
				$("#"+form+" .orgcode" ).addClass("is-valid");
				$("#"+form+" .orgcode_status" ).removeClass("text-danger");
 				$("#"+form+" .orgcode_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .orgcode" ).removeClass("is-valid");
 				$("#"+form+" .orgcode" ).addClass("is-invalid");
 				$("#"+form+" .orgcode_status" ).removeClass("text-success");
 				$("#"+form+" .orgcode_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .orgcode_status").html(response.status);
 			$("#"+form+" .orgcode_flag").html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .orgcode_status" ).html("Error");
 			$("#"+form+" .orgcode_flag").html("false");
			}
 		});
		} else {
			$("#"+form+" .orgcode").removeClass("is-valid");
			$("#"+form+" .orgcode").removeClass("is-invalid");
			$("#"+form+" .orgcode_status").html("");
			$("#"+form+" .orgcode_flag").html("false");
		}
}

function orgName(form) {
	var data= { 
			orgid: $("#"+form+" .orgid").val(),
			orgname: $("#"+form+" .orgname").val()
			}
	if(!$.isEmptyObject($("#"+form+" .orgname").val())) {
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkOrgName",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
	 			$("#"+form+" .orgname" ).removeClass("is-invalid");
				$("#"+form+" .orgname" ).addClass("is-valid");
				$("#"+form+" .orgname_status" ).removeClass("text-danger");
 				$("#"+form+" .orgname_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .orgname" ).removeClass("is-valid");
 				$("#"+form+" .orgname" ).addClass("is-invalid");
 				$("#"+form+" .orgname_status" ).removeClass("text-success");
 				$("#"+form+" .orgname_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .orgname_status" ).html(response.status);
 			$("#"+form+" .orgname_flag" ).html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .orgname_status" ).html("Error");
 			$("#"+form+" .orgname_flag").html("false");
			}
 		});
	} else {
		$("#"+form+" .orgname").removeClass("is-valid");
		$("#"+form+" .orgname").removeClass("is-invalid");
		$("#"+form+" .orgname_status").html("");
		$("#"+form+" .orgname_falg").html("false");
	}
}

function orgForm(form) {
	
	var orgcodeflag=$("#"+form+" .orgcode_flag" ).text();
	var orgnameflag=$("#"+form+" .orgname_flag" ).text();
	let orgcode=$("#"+form+" .orgcode").val();
	let orgname=$("#"+form+" .orgname").val();

	if(orgcodeflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Organization Code - "+orgcode+" Exists.");
		return false;
	} else if(orgnameflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Organization Name - "+orgname+" Exists.");
		return false;
	} else {
		$("#"+form+" #modal_error").hide();
		return true;
	}
}

function deptCode(form) {
	var data= { 
			deptid: $("#"+form+" .deptid").val(),
			deptcode: $("#"+form+" .deptcode").val()
			}
 	
 	if(!$.isEmptyObject($("#"+form+" .deptcode").val())) {
 		
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkDeptCode",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
	 			$("#"+form+" .deptcode" ).removeClass("is-invalid");
				$("#"+form+" .deptcode" ).addClass("is-valid");
				$("#"+form+" .deptcode_status" ).removeClass("text-danger");
 				$("#"+form+" .deptcode_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .deptcode" ).removeClass("is-valid");
 				$("#"+form+" .deptcode" ).addClass("is-invalid");
 				$("#"+form+" .deptcode_status" ).removeClass("text-success");
 				$("#"+form+" .deptcode_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .deptcode_status").html(response.status);
 			$("#"+form+" .deptcode_flag").html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .deptcode_status" ).html("Error");
 			$("#"+form+" .deptcode_flag" ).html("false");
			}
 		});
 	} else {
 		$("#"+form+" .deptcode").removeClass("is-valid");
		$("#"+form+" .deptcode").removeClass("is-invalid");
 		$("#"+form+" .deptcode_status").html("");
 		$("#"+form+" .deptcode_flag" ).html("false");
 	}
}

function deptName(form) {
	var data= { 
			deptid: $("#"+form+" .deptid").val(),
			deptname: $("#"+form+" .deptname").val()
			}
 	
 	if(!$.isEmptyObject($("#"+form+" .deptname").val())) {
 		
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkDeptName",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
	 			$("#"+form+" .deptname" ).removeClass("is-invalid");
				$("#"+form+" .deptname" ).addClass("is-valid");
				$("#"+form+" .deptname_status" ).removeClass("text-danger");
 				$("#"+form+" .deptname_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .deptname" ).removeClass("is-valid");
 				$("#"+form+" .deptname" ).addClass("is-invalid");
 				$("#"+form+" .deptname_status" ).removeClass("text-success");
 				$("#"+form+" .deptname_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .deptname_status" ).html(response.status);
 			$("#"+form+" .deptname_flag" ).html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .deptname_status" ).html("Error");
 			$("#"+form+" .deptname_flag" ).html("false");
			}
 		});
 	} else {
 		$("#"+form+" .deptname").removeClass("is-valid");
		$("#"+form+" .deptname").removeClass("is-invalid");
 		$("#"+form+" .deptname_status" ).html("");
 		$("#"+form+" .deptname_flag").html("false");
 	}
}

function deptForm(form) {
	
	var deptcodeflag=$("#"+form+" .deptcode_flag" ).text();
	var deptnameflag=$("#"+form+" .deptname_flag" ).text();
	let deptcode=$("#"+form+" .deptcode").val();
	let deptname=$("#"+form+" .deptname").val();

	if(deptcodeflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Department Code - "+deptcode+" Exists.");
		return false;
	} else if(deptnameflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Department Name - "+deptname+" Exists.");
		return false;
	} else {
		$("#"+form+" #modal_error").hide();
		return true;
	}
}

function subdeptCode(form) {
	var data= { 
			subdeptid: $("#"+form+" .subdeptid").val(),
			subdeptcode: $("#"+form+" .subdeptcode").val()
			}
 	
 	if(!$.isEmptyObject($("#"+form+" .subdeptcode").val())) {
 		
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkSubDeptCode",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
	 			$("#"+form+" .subdeptcode" ).removeClass("is-invalid");
				$("#"+form+" .subdeptcode" ).addClass("is-valid");
				$("#"+form+" .subdeptcode_status" ).removeClass("text-danger");
 				$("#"+form+" .subdeptcode_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .subdeptcode" ).removeClass("is-valid");
 				$("#"+form+" .subdeptcode" ).addClass("is-invalid");
 				$("#"+form+" .subdeptcode_status" ).removeClass("text-success");
 				$("#"+form+" .subdeptcode_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .subdeptcode_status").html(response.status);
 			$("#"+form+" .subdeptcode_flag").html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .subdeptcode_status").html("Error");
 			$("#"+form+" .subdeptcode_flag").html("false");
			}
 		});
 	} else {
 		$("#"+form+" .subdeptcode").removeClass("is-valid");
		$("#"+form+" .subdeptcode").removeClass("is-invalid");
 		$("#"+form+" .subdeptcode_status").html("");
 		$("#"+form+" .subdeptcode_flag").html("false");
 	}
}

function subdeptName(form) {
	var data= { 
			subdeptid: $("#"+form+" .subdeptid").val(),
			subdeptname: $("#"+form+" .subdeptname").val()
			}
 	
 	if(!$.isEmptyObject($("#"+form+" .subdeptname").val())) {
 		
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkSubDeptName",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
	 			$("#"+form+" .subdeptname" ).removeClass("is-invalid");
				$("#"+form+" .subdeptname" ).addClass("is-valid");
				$("#"+form+" .subdeptname_status" ).removeClass("text-danger");
 				$("#"+form+" .subdeptname_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .subdeptname" ).removeClass("is-valid");
 				$("#"+form+" .subdeptname" ).addClass("is-invalid");
 				$("#"+form+" .subdeptname_status" ).removeClass("text-success");
 				$("#"+form+" .subdeptname_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .subdeptname_status").html(response.status);
 			$("#"+form+" .subdeptname_flag").html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .subdeptname_status").html("Error");
 			$("#"+form+" .subdeptname_flag").html("false");
			}
 		});
 	} else {
 		$("#"+form+" .subdeptname").removeClass("is-valid");
		$("#"+form+" .subdeptname").removeClass("is-invalid");
 		$("#"+form+" .subdeptname_status").html("");
 		$("#"+form+" .subdeptname_flag").html("false");
 	}
}

function subdeptForm(form) {
	
	var subdeptcodeflag=$("#"+form+" .subdeptcode_flag" ).text();
	var subdeptnameflag=$("#"+form+" .subdeptname_flag" ).text();
	let subdeptcode=$("#"+form+" .subdeptcode").val();
	let subdeptname=$("#"+form+" .subdeptname").val();

	if(subdeptcodeflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Sub Department Code - "+subdeptcode+" Exists.");
		return false;
	} else if(subdeptnameflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Sub Department Name - "+subdeptname+" Exists.");
		return false;
	} else {
		$("#"+form+" #modal_error").hide();
		return true;
	}
}

function mainlocName(form) {
	var data= { 
			mainlocid: $("#"+form+" .mainlocid").val(),
			mainlocname: $("#"+form+" .mainlocname").val()
			}
 	
 	if(!$.isEmptyObject($("#"+form+" .mainlocname").val())) {
 		
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkMainLocName",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
	 			$("#"+form+" .mainlocname" ).removeClass("is-invalid");
				$("#"+form+" .mainlocname" ).addClass("is-valid");
				$("#"+form+" .mainlocname_status" ).removeClass("text-danger");
 				$("#"+form+" .mainlocname_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .mainlocname" ).removeClass("is-valid");
 				$("#"+form+" .mainlocname" ).addClass("is-invalid");
 				$("#"+form+" .mainlocname_status" ).removeClass("text-success");
 				$("#"+form+" .mainlocname_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .mainlocname_status").html(response.status);
 			$("#"+form+" .mainlocname_flag").html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .mainlocname_status").html("Error");
 			$("#"+form+" .mainlocname_flag").html("false");
			}
 		});
 	} else {
 		$("#"+form+" .mainlocname").removeClass("is-valid");
		$("#"+form+" .mainlocname").removeClass("is-invalid");
 		$("#"+form+" .mainlocname_status").html("");
 		$("#"+form+" .mainlocname_flag").html("false");
 	}
}

function mainlocForm(form) {
	
	var mainlocnameflag=$("#"+form+" .mainlocname_flag" ).text();
	let mainlocname=$("#"+form+" .mainlocname").val();

	if(mainlocnameflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Main Location - "+mainlocname+" Exists.");
		return false;
	} else {
		$("#"+form+" #modal_error").hide();
		return true;
	}
	
}

function sublocName(form) {
	var data= { 
			sublocid: $("#"+form+" .sublocid").val(),
			sublocname: $("#"+form+" .sublocname").val()
			}
 	
 	if(!$.isEmptyObject($("#"+form+" .sublocname").val())) {
 		
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkSubLocName",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
	 			$("#"+form+" .sublocname" ).removeClass("is-invalid");
				$("#"+form+" .sublocname" ).addClass("is-valid");
				$("#"+form+" .sublocname_status" ).removeClass("text-danger");
 				$("#"+form+" .sublocname_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .sublocname" ).removeClass("is-valid");
 				$("#"+form+" .sublocname" ).addClass("is-invalid");
 				$("#"+form+" .sublocname_status" ).removeClass("text-success");
 				$("#"+form+" .sublocname_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .sublocname_status").html(response.status);
 			$("#"+form+" .sublocname_flag").html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .sublocname_status").html("Error");
 			$("#"+form+" .sublocname_flag").html("false");
			}
 		});
 	} else {
 		$("#"+form+" .sublocname").removeClass("is-valid");
		$("#"+form+" .sublocname").removeClass("is-invalid");
 		$("#"+form+" .sublocname_status").html("");
 		$("#"+form+" .sublocname_flag").html("false");
 	}
}

function sublocForm(form) {
	
	var sublocnameflag=$("#"+form+" .sublocname_flag" ).text();
	let sublocname=$("#"+form+" .sublocname").val();

	if(sublocnameflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Sub Location - "+sublocname+" Exists.");
		return false;
	} else {
		$("#"+form+" #modal_error").hide();
		return true;
	}
	
}

function hardwareCode(form) {
	var data= { 
			hardwareid: $("#"+form+" .hardwareid").val(),
			hardwarecode: $("#"+form+" .hardwarecode").val()
			}
		if(!$.isEmptyObject($("#"+form+" .hardwarecode").val())) {
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkHardwareCode",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
 				$("#"+form+" .hardwarecode" ).removeClass("is-invalid");
 				$("#"+form+" .hardwarecode" ).addClass("is-valid");
				$("#"+form+" .hardwarecode_status" ).removeClass("text-danger");
 				$("#"+form+" .hardwarecode_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .hardwarecode" ).removeClass("is-valid");
 				$("#"+form+" .hardwarecode" ).addClass("is-invalid");
 				$("#"+form+" .hardwarecode_status" ).removeClass("text-success");
 				$("#"+form+" .hardwarecode_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .hardwarecode_status").html(response.status);
 			$("#"+form+" .hardwarecode_flag").html(response.flag);
  		},
 		error : function(e) {
 		
 			$("#"+form+" .hardwarecode_status" ).html("Error");
 			$("#"+form+" .hardwarecode_flag").html("false");
			}
 		});
		} else {
			$("#"+form+" .hardwarecode").removeClass("is-valid");
			$("#"+form+" .hardwarecode").removeClass("is-invalid");
			$("#"+form+" .hardwarecode_status").html("");
			$("#"+form+" .hardwarecode_flag").html("false");
		}
}

function hardwareType(form) {
	var data= { 
			hardwareid: $("#"+form+" .hardwareid").val(),
			hardwaretype: $("#"+form+" .hardwaretype").val()
			}
	if(!$.isEmptyObject($("#"+form+" .hardwaretype").val())) {
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkHardwareType",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
 				$("#"+form+" .hardwaretype" ).removeClass("is-invalid");
 				$("#"+form+" .hardwaretype" ).addClass("is-valid");
				$("#"+form+" .hardwaretype_status" ).removeClass("text-danger");
 				$("#"+form+" .hardwaretype_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .hardwaretype" ).removeClass("is-valid");
 				$("#"+form+" .hardwaretype" ).addClass("is-invalid");
 				$("#"+form+" .hardwaretype_status" ).removeClass("text-success");
 				$("#"+form+" .hardwaretype_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .hardwaretype_status" ).html(response.status);
 			$("#"+form+" .hardwaretype_flag" ).html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .hardwaretype_status" ).html("Error");
 			$("#"+form+" .hardwaretype_flag").html("false");
			}
 		});
	} else {
		$("#"+form+" .hardwaretype").removeClass("is-valid");
		$("#"+form+" .hardwaretype").removeClass("is-invalid");
		$("#"+form+" .hardwaretype_status").html("");
		$("#"+form+" .hardwaretype_falg").html("false");
	}
}

function hardwareForm(form) {
	
	var hardwarecodeflag=$("#"+form+" .hardwarecode_flag" ).text();
	var hardwaretypeflag=$("#"+form+" .hardwaretype_flag" ).text();
	let hardwarecode=$("#"+form+" .hardwarecode").val();
	let hardwaretype=$("#"+form+" .hardwaretype").val();

	if(hardwarecodeflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Hardware Code - "+hardwarecode+" Exists.");
		return false;
	} else if(hardwaretypeflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Hardware Type - "+hardwaretype+" Exists.");
		return false;
	} else {
		$("#"+form+" #modal_error").hide();
		return true;
	}
}

function brandCode(form) {
	var data= { 
			brandid: $("#"+form+" .brandid").val(),
			brandcode: $("#"+form+" .brandcode").val()
			}
		if(!$.isEmptyObject($("#"+form+" .brandcode").val())) {
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkBrandCode",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
	 			$("#"+form+" .brandcode" ).removeClass("is-invalid");
				$("#"+form+" .brandcode" ).addClass("is-valid");
				$("#"+form+" .brandcode_status" ).removeClass("text-danger");
 				$("#"+form+" .brandcode_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .brandcode" ).removeClass("is-valid");
 				$("#"+form+" .brandcode" ).addClass("is-invalid");
 				$("#"+form+" .brandcode_status" ).removeClass("text-success");
 				$("#"+form+" .brandcode_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .brandcode_status").html(response.status);
 			$("#"+form+" .brandcode_flag").html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .brandcode_status" ).html("Error");
 			$("#"+form+" .brandcode_flag").html("false");
			}
 		});
		} else {
			$("#"+form+" .brandcode").removeClass("is-valid");
			$("#"+form+" .brandcode").removeClass("is-invalid");
			$("#"+form+" .brandcode_status").html("");
			$("#"+form+" .brandcode_flag").html("false");
		}
}

function brandName(form) {
	var data= { 
			brandid: $("#"+form+" .brandid").val(),
			brandname: $("#"+form+" .brandname").val()
			}
	if(!$.isEmptyObject($("#"+form+" .brandname").val())) {
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkBrandName",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
	 			$("#"+form+" .brandname" ).removeClass("is-invalid");
				$("#"+form+" .brandname" ).addClass("is-valid");
				$("#"+form+" .brandname_status" ).removeClass("text-danger");
 				$("#"+form+" .brandname_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .brandname" ).removeClass("is-valid");
 				$("#"+form+" .brandname" ).addClass("is-invalid");
 				$("#"+form+" .brandname_status" ).removeClass("text-success");
 				$("#"+form+" .brandname_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .brandname_status" ).html(response.status);
 			$("#"+form+" .brandname_flag" ).html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .brandname_status" ).html("Error");
 			$("#"+form+" .brandname_flag").html("false");
			}
 		});
	} else {
		$("#"+form+" .brandname").removeClass("is-valid");
		$("#"+form+" .brandname").removeClass("is-invalid");
		$("#"+form+" .brandname_status").html("");
		$("#"+form+" .brandname_falg").html("false");
	}
}

function brandForm(form) {
	
	var brandcodeflag=$("#"+form+" .brandcode_flag" ).text();
	var brandnameflag=$("#"+form+" .brandname_flag" ).text();
	let brandcode=$("#"+form+" .brandcode").val();
	let brandname=$("#"+form+" .brandname").val();

	if(brandcodeflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Brand Code - "+brandcode+" Exists.");
		return false;
	} else if(brandnameflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Brand Name - "+brandname+" Exists.");
		return false;
	} else {
		$("#"+form+" #modal_error").hide();
		return true;
	}
}

function partnoCode(form) {
	var data= { 
			partnoid: $("#"+form+" .partnoid").val(),
			partnocode: $("#"+form+" .partnocode").val()
			}
		if(!$.isEmptyObject($("#"+form+" .partnocode").val())) {
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkPartNoCode",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			$("#"+form+" .partnocode_status").html(response.status);
 			$("#"+form+" .partnocode_flag").html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .partnocode_status" ).html("Error");
 			$("#"+form+" .partnocode_flag").html("false");
			}
 		});
		} else {
			$("#"+form+" .partnocode_status").html("Field is required");
			$("#"+form+" .partnocode_flag").html("false");
		}
}

function partnoModelNo(form) {
//	var data= { 
//			partnoid: $("#"+form+" .partnoid").val(),
//			modelno: $("#"+form+" .modelno").val()
//			}
//	if(!$.isEmptyObject($("#"+form+" .modelno").val())) {
//// 		$.ajax({
//// 		type: "POST",
//// 		contentType: "application/json",
//// 		url: BASE_URL + "api/checkModelNo",
//// 		data: JSON.stringify (data),
//// 		dataType: 'json',
//// 		
//// 		success: function (response) {
//// 			$("#"+form+" .modelno_status" ).html(response.status);
//// 			$("#"+form+" .modelno_flag" ).html(response.flag);
////  		},
//// 		error : function(e) {
//// 			$("#"+form+" .modelno_status" ).html("Error");
//// 			$("#"+form+" .modelno_flag").html("false");
////			}
//// 		});
//		$("#"+form+" .modelno_status").html("");
//		$("#"+form+" .modelno_falg").html("true");
//	} else {
//		$("#"+form+" .modelno_status").html("Field is required");
//		$("#"+form+" .modelno_falg").html("false");
//	}
}

function partnoSerialNo(form) {
	var data= { 
			partnoid: $("#"+form+" .partnoid").val(),
			serialno: $("#"+form+" .serialno").val()
			}
	if(!$.isEmptyObject($("#"+form+" .serialno").val())) {
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkSerialNo",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			$("#"+form+" .serialno_status" ).html(response.status);
 			$("#"+form+" .serialno_flag" ).html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .serialno_status" ).html("Error");
 			$("#"+form+" .serialno_flag").html("false");
			}
 		});
	} else {
		$("#"+form+" .serialno_status").html("Field is required");
		$("#"+form+" .serialno_falg").html("false");
	}
}

function partnoUpcCode(form) {
	var data= { 
			partnoid: $("#"+form+" .partnoid").val(),
			upccode: $("#"+form+" .upccode").val()
			}
	if(!$.isEmptyObject($("#"+form+" .upccode").val())) {
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkUpcCode",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			$("#"+form+" .upccode_status" ).html(response.status);
 			$("#"+form+" .upccode_flag" ).html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .upccode_status" ).html("Error");
 			$("#"+form+" .upccode_flag").html("false");
			}
 		});
	} else {
		$("#"+form+" .upccode_status").html("Field is required");
		$("#"+form+" .upccode_falg").html("false");
	}
}

function partnoForm(form) {
	
//	var partnocodeinput= $.isEmptyObject($("#"+form+" .partnocode").val());
//	var modelnoinput= $.isEmptyObject($("#"+form+" .modelno").val());
	var serialnoinput= $.isEmptyObject($("#"+form+" .serialno").val());
//	var upccodeinput= $.isEmptyObject($("#"+form+" .upccode").val());
//	var partnocodeflag=$("#"+form+" .partnocode_flag" ).text();
//	var modelnoflag=$("#"+form+" .modelno_flag" ).text();
	var serialnoflag=$("#"+form+" .serialno_flag" ).text();
//	var upccodeflag=$("#"+form+" .upccode_flag" ).text();

//	if(partnocodeinput){
//		$("#"+form+" .partnocode_status").html("Field is required");
//	}
//	if(modelnoinput){
//		$("#"+form+" .modelno_status").html("Field is required");
//	}
	if(serialnoinput){
		$("#"+form+" .serialno_status").html("Field is required");
	}
//	if(upccodeinput){
//		$("#"+form+" .upccode_status").html("Field is required");
//	}
//	if(((modelnoinput)||(modelnoflag=="false"))
//			||((serialnoinput)||(serialnoflag=="false"))||((upccodeinput)||(upccodeflag=="false"))){
//	((modelnoinput)||(modelnoflag=="false"))||
	if(((serialnoinput)||(serialnoflag=="false"))){
		$("#"+form+" .error").html("Please complete the form");
		return false;
	} else {
		return true;
	}
}

function productCode(form) {
	var data= { 
			productid: $("#"+form+" .productid").val(),
			productcode: $("#"+form+" .productcode").val()
			}
		if(!$.isEmptyObject($("#"+form+" .productcode").val())) {
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkProductCode",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
	 			$("#"+form+" .productcode" ).removeClass("is-invalid");
				$("#"+form+" .productcode" ).addClass("is-valid");
				$("#"+form+" .productcode_status" ).removeClass("text-danger");
 				$("#"+form+" .productcode_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .productcode" ).removeClass("is-valid");
 				$("#"+form+" .productcode" ).addClass("is-invalid");
 				$("#"+form+" .productcode_status" ).removeClass("text-success");
 				$("#"+form+" .productcode_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .productcode_status").html(response.status);
 			$("#"+form+" .productcode_flag").html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .productcode_status" ).html("Error");
 			$("#"+form+" .productcode_flag").html("false");
			}
 		});
		} else {
			$("#"+form+" .productcode").removeClass("is-valid");
			$("#"+form+" .productcode").removeClass("is-invalid");
			$("#"+form+" .productcode_status").html("");
			$("#"+form+" .productcode_flag").html("false");
		}
}

function productName(form) {
	var data= { 
			productid: $("#"+form+" .productid").val(),
			productname: $("#"+form+" .productname").val()
			}
	if(!$.isEmptyObject($("#"+form+" .productname").val())) {
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkProductName",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
	 			$("#"+form+" .productname" ).removeClass("is-invalid");
				$("#"+form+" .productname" ).addClass("is-valid");
				$("#"+form+" .productname_status" ).removeClass("text-danger");
 				$("#"+form+" .productname_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .productname" ).removeClass("is-valid");
 				$("#"+form+" .productname" ).addClass("is-invalid");
 				$("#"+form+" .productname_status" ).removeClass("text-success");
 				$("#"+form+" .productname_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .productname_status" ).html(response.status);
 			$("#"+form+" .productname_flag" ).html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .productname_status" ).html("Error");
 			$("#"+form+" .productname_flag").html("false");
			}
 		});
	} else {
		$("#"+form+" .productname").removeClass("is-valid");
		$("#"+form+" .productname").removeClass("is-invalid");
		$("#"+form+" .productname_status").html("");
		$("#"+form+" .productname_falg").html("false");
	}
}

function productLBValue(form) {
	var data= { 
			productid: $("#"+form+" .productid").val(),
			lbvalue: $("#"+form+" .lbvalue").val()
			}
	if(!$.isEmptyObject($("#"+form+" .lbvalue").val())) {
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkLBValue",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			$("#"+form+" .lbvalue_status" ).html(response.status);
 			$("#"+form+" .lbvalue_flag" ).html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .lbvalue_status" ).html("Error");
 			$("#"+form+" .lbvalue_flag").html("false");
			}
 		});
	} else {
		$("#"+form+" .lbvalue_status").html("Field is required");
		$("#"+form+" .lbvalue_falg").html("false");
	}
}

function productForm(form) {
	
	var productcodeflag=$("#"+form+" .productcode_flag" ).text();
	var productnameflag=$("#"+form+" .productname_flag" ).text();
	let productcode=$("#"+form+" .productcode").val();
	let productname=$("#"+form+" .productname").val();
	
	if(productcodeflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Product Code - "+productcode+" Exists.");
		return false;
	} else if(productnameflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Product Name - "+productname+" Exists.");
		return false;
	} else {
		$("#"+form+" #modal_error").hide();
		return true;
	}
	
}

function checkreason(form) {
	var data= { 
			reasonid: $("#"+form+" .reasonid").val(),
			reason: $("#"+form+" .reason").val()
			}
 	if(!$.isEmptyObject($("#"+form+" .reason").val())) {
 		
 		$.ajax({
 		type: "POST",
 		contentType: "application/json",
 		url: BASE_URL + "api/checkReason",
 		data: JSON.stringify (data),
 		dataType: 'json',
 		
 		success: function (response) {
 			if(response.flag=="true"){
	 			$("#"+form+" .reason" ).removeClass("is-invalid");
				$("#"+form+" .reason" ).addClass("is-valid");
				$("#"+form+" .reason_status" ).removeClass("text-danger");
 				$("#"+form+" .reason_status" ).addClass("text-success");
 			} else {
 				$("#"+form+" .reason" ).removeClass("is-valid");
 				$("#"+form+" .reason" ).addClass("is-invalid");
 				$("#"+form+" .reason_status" ).removeClass("text-success");
 				$("#"+form+" .reason_status" ).addClass("text-danger");
 			}
 			$("#"+form+" .reason_status").html(response.status);
 			$("#"+form+" .reason_flag").html(response.flag);
  		},
 		error : function(e) {
 			$("#"+form+" .reason_status").html("Error");
 			$("#"+form+" .reason_flag").html("false");
			}
 		});
 	} else {
 		$("#"+form+" .reason").removeClass("is-valid");
		$("#"+form+" .reason").removeClass("is-invalid");
 		$("#"+form+" .reason_status").html("");
 		$("#"+form+" .reason_flag").html("false");
 	}
}

function reasonForm(form) {
	
	var reasonflag=$("#"+form+" .reason_flag" ).text();
	let reason=$("#"+form+" .reason").val();
	
	if(reasonflag=="false"){
		$("#"+form+" #modal_error").show();
		$("#"+form+" #modal_error").text("Product Code - "+productcode+" Exists.");
		return false;
	}  else {
		$("#"+form+" #modal_error").hide();
		return true;
	}
}

//$(this).parse({
//config: {
////	 header:"true",
//delimiter: "auto",
//complete: function(results){
////	 $(".serialno").val(results.data);
////	 name.val("hell");
////	 console.log(object);
//	 console.log(typeof results);
//	 console.log(results.data);
//	 var contents="";
//	 var data=results.data;
//	 console.log(data);
//	 for(i=0;i<data.length;i++){
////		 console.log(typeof data[i]);
////		 console.log(data[i][0]);
//		 contents+=data[i][0];
//		 contents+=" ";
//		 
//	 }
//	 console.log(contents);
////	 let abc=results.data;
//	 let replace = contents.replace(/\W+/g,"\n");
//	 $(object).closest("div").find(".serialno").val(replace);
////	 $(this).closest("div").find(".serialno").val(results.data);
////	 $(this).closest(".form-row").find("#serialno").remove();
//	 
//	},
//},
//before: function(file, inputElem)
//{
////console.log("Parsing file...", file);
//},
//error: function(err, file)
//{
////console.log("ERROR:", err, file);
//},
//complete: function(results)
//{
////	 console.log("Done with all files");
//}
//});


//$(document).on("keyup",".username", function() {
//let current = this;
//let form = "#"+this.form.id;
//console.log(form);
//var data= { 
//		userid: $(form+" .userid").val(),
//		username: $(form+" .username").val()
//		}
//
//$.ajax({
//		type: "POST",
//		contentType: "application/json",
//		url: BASE_URL + "api/checkUserName",
//		data: JSON.stringify (data),
//		dataType: 'json',
//		success: function (response) {
//			
//			if(response.flag=="true"){
//				
//				$(current).closest(".form-group").addClass("has-success");
//				$(form+" .username_status" ).removeClass("text-danger");
//				$(form+" .username_status" ).addClass("text-success");
//			} else {
//				$(form+" .username_status" ).removeClass("text-success");
//				$(form+" .username_status" ).addClass("text-danger");
//			}
//			$(form+" .username_status" ).html(response.status);
//			$(form+" .username_flag").html(response.flag);
//		},
//		error : function(e) {
//			$("#"+form+" .username_status" ).html("Error");
//			$("#"+form+" .username_flag").html("false");
//		}
//		});
//});
