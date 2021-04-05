<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
	<div class="col mb-4">
		<button type="button" onclick="doAdd()" class="btn btn-primary">Write</button>
		<hr>
	</div>
</div>
<div class="row">
	<div class="col">
		<table id="myTable" class="table table-striped table-bordered" style="width:100%">
		    <thead>
		        <tr>
		            <th>Id</th>
		            <th>Title</th>
		            <th>Subtitle</th>
		            <th>Summary</th>
		            <th>Content</th>
		            <th>Author</th>
		            <th>Reg. Date</th>
		            <th>Action</th>
		        </tr>
		    </thead>
		</table>
	</div>
</div>	
<div class="row">
	<div class="col mb-4">
	</div>
</div>

<div id="modalForm" class="modal" aria-hidden="true" tabindex="-1" aria-labelledby="modalTitle">
	<div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="modalTitle">Form</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form id="inputForm" name="inputForm">
					<div class="form-row">
						<input type="text" style="display:none;" name="seq" id="seq" value="">
						<div class="col-md-8 mb-3">
							<label>Title</label> 
							<input type="text" class="form-control" name="title" id="title" value="" required>
						</div>
						<div class="col-md-4 mb-3">
							<label>Author</label> 
							<input type="text" class="form-control" name="author" id="author" value="" required>
						</div>
					</div>
					<div class="form-row">
						<div class="col-md-12 mb-3">
							<label>Sub Title</label> 
							<input type="text" class="form-control" name="subtitle" id="subtitle" value="" required>
						</div>
					</div>
					<div class="form-row">
						<div class="col-md-12 mb-3">
							<label>Summary</label> 
							<textarea class="form-control" name="summary" id="summary" rows="3" required></textarea>
						</div>
					</div>
					<div class="form-row">
						<div class="col-md-12 mb-3">
							<label>Content</label> 
							<textarea class="form-control" name="content" id="content" rows="6" required></textarea>
						</div>
					</div>
					<input type="submit" name="submitHandler" style="display:none"/>
				</form>
				<form id="fileForm" name="fileForm" enctype="multipart/form-data">
					<div class="form-row" id="mainImageConGroup">
						<div class="col-md-3 mb-3">
							<img id="mainImageCon" src="" class="img-thumbnail">						
              			</div>
					</div>
					<div class="form-row" id="mainImageGroup">
						<div class="col-md-12 mb-3">
                			<input type="file" id="mainImage" class="form-control-file" name="mainImage">
              			</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" type="submit" onclick="doSubmit()">Submit</button>
			</div>
		</div>
	</div>
</div>

<script>
	var authHeader = getCookie("token");
	var checkHeader = function(req){
		if(authHeader == undefined || authHeader == ""){
			return $.ajax({
		        dataType : "JSON",
		        method : "POST",
		        global : false,
		        contentType : "application/json",
		        url : "/v1/users/login",
		        beforeSend : function (xhr) {
					console.log("Logging in");
				},		        
		        data : JSON.stringify({
		        	"username" : "username",
		        	"password" : "admin"
		        }),
		        success : function(res){
		        	authHeader = res.data.token;
		        	setCookie("token", authHeader, 1);
		        }
			});
		}else{
			if (req != undefined) req.setRequestHeader("Authorization", "Bearer " + authHeader.trim());
			return $.Deferred().resolve().promise();
		}
	};
	
	var resetForm = function(){
		document.inputForm.reset();
		document.fileForm.reset();
    	$("#mainImageCon").attr("src", "");
		$("#mainImageConGroup").show();
	};

	var doEdit = function(id){
		resetForm();
		$.ajax({
	        dataType : "JSON",
	        method : "GET",
	        contentType : "application/json",
	        url : "/v1/boards/get/"+id,
	        success : function(res){
	        	$.each(res.data, function(key, val) {
	        		if($("#"+key).prop("type") != "file"){
	        			$("#"+key).val(val);
	        		}
        	    });
	        	CKEDITOR.instances["content"].setData(res.data.content);
	        	var files = res.data.files;
	        	if(files[0] != undefined){
		        	$("#mainImageCon").attr("src", "/public/board/" + files[0].name);
	        		$("#mainImageConGroup").show();
	        	}else{
	        		$("#mainImageConGroup").hide();
	        	}
	    		$("#modalForm").modal("show");
	        }
		});
	};

	var doAdd = function(){
		resetForm();
		$("#modalForm").modal("show");
	};
	
	
	var doDelete = function(id){		
		$.ajax({
	        dataType : "JSON",
	        method : "POST",
	        contentType : "application/json",
	        url : "/v1/boards/delete",
	        data : JSON.stringify({
	        	"seq" : id
	        }),
	        success : function(res){
	        	dt.draw(false);
	        }
		});
	};
	
	var doSubmit = function(){
		var frm = document.inputForm;
		if (!frm.checkValidity()) {
			frm.submitHandler.click();
			return;
		}
		
		var frm = document.fileForm;
		if(frm.file && frm.file.value !== ""){
			if (frm.file.value.match(/(.jpg|.jpeg|.gif|.png)$/i) === null){
				return;
			}
		}

		doSave().then(function(res){
			var data = {
					"ownerSeq" : res.data.seq,
					"channel" : "board"
			};
			doUpload(data).always(function(res){
				console.log("Cleaning up, doesn't matter if error");
				$("#modalForm").modal("hide");
				dt.draw(false);
			});
		});
	};

	var ewdebug = {};
	var doSave = function(){
		$("#inputForm .invalid-feedback").remove();
		$("#inputForm .is-invalid").removeClass("is-invalid");
		
		var data = {};
		var arr = $("#inputForm").serializeArray();
		for(el in arr){
			if(arr[el].name == "seq" && arr[el].value == "") continue;
			data[arr[el].name] = arr[el].value;
		}
		data.content = CKEDITOR.instances["content"].getData();
		
		var url = "";
		if(data.seq != undefined)
			url = "/v1/boards/update";
		else
			url = "/v1/boards/create";
		
		return $.ajax({
	        dataType : "JSON",
	        method : "POST",
	        contentType : "application/json",
	        url : url,
	        data : JSON.stringify(data),
	        success : function(res){
	        	console.log("Saving data success");
	        },
	        error : function(res){
		        ewdebug = res;
		        if(res.status == 400){
			        var data = ewdebug.responseJSON.data;
					for(key in data){
						if (data.hasOwnProperty(key)){
							var $el = $("#inputForm").find("#"+key);
							$el.addClass("is-invalid");
							$el.after("<div class='invalid-feedback'>"+data[key]+"</div>");
						}
					}
			    }
		    }
		});
	};
	
	var doUpload = function(data){
		var formData = new FormData();
		var mainImage = $("#mainImage")[0].files;
		if(mainImage.length > 0) {
			for(key in data){
				if (data.hasOwnProperty(key))
					formData.append(key, data[key]);
			}
			formData.append("mainImage", mainImage[0]);
		}else{
			return $.Deferred().resolve().promise();
		}
		
		console.log(data, formData);
		
		return $.ajax({
	        dataType : "JSON",
	        method : "POST",
	        url : "/v1/files/upload",
	        data : formData,
	        contentType : false,
	        processData: false,
	        success : function(res){
	        	console.log("Uploading file success");
	        }
		});
	};
	
	$.ajaxSetup({
		beforeSend : function (req) {
			checkHeader(req);
		}
	});
	
	var ewdebug = {};
	var dt;
	var dp = new DOMParser();
	var cols = [
		{"data" : "seq", "className" : "d-none d-xl-table-cell", "width": "5%"},
		{"data" : "title", "width": "15%"},
		{"data" : "subtitle", "className" : "d-none d-xl-table-cell", "width": "10%"},
		{"data" : "summary", "className" : "d-none d-xl-table-cell", "width": "15%"},
		{
			"className" : "d-none d-md-table-cell", 
			"width": "30%",
			"render" : function (data, type, row, meta) {
				var val = dp.parseFromString(row.content, "text/html");
				val = val.body.textContent || "";
				val = val.length > 35 ? val.substring(0, 35) + "..." : row.content;
				return val;
			}
		},
		{"data" : "author", "className" : "text-center d-none d-xl-table-cell", "width": "5%"},
		{"data" : "regDate", "className" : "text-center d-none d-xl-table-cell", "width": "10%"},
		{
			"className" : "text-center",
			"width": "10%",
			"render" : function (data, type, row, meta) {
				var pd  = "";
					pd += "<div class=\"row\"><div class=\"col\">Are you sure you want to delete ?<hr></div></div></div>";
					pd += "<div class=\"row\"><div class=\"col text-right\">";
					pd += "<a onclick=\"doDelete("+row.seq+")\" class=\"btn btn-outline-danger btn-sm\">Yes</a>";
				    pd += "&nbsp;";
				    pd += "<a onclick=\"\" class=\"btn btn-outline-secondary btn-sm\">No</a>";
					pd += "</div></div></div>";

				var el  = "";
					el += "<a onclick='doEdit("+row.seq+")' class='btn btn-secondary'>Edit</a>";
				    el += "&nbsp;";
				    el += "<a tabindex='0' title='Delete' data-toggle='popover' data-placement='left' data-content='"+pd+"' class='btn btn-danger'>Delete</a>";
				
				return el;
			}
		},
	];
	
	$(document).ready(function(){
		checkHeader().done(function(val){
			dt = $("#myTable").DataTable({
				dom : 	"<'row'<'col-sm-12 col-md-6'l><'col-sm-12 col-md-6'f>>" +
						"<'row'<'col-sm-12'tr>>" +
						"<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
				pageLength 	: 10,
				lengthMenu 	: [5, 10, 20, 50, 100],
				bProcessing : true,
				bServerSide : true,
				order : [[ 0, "desc" ]],
				sAjaxSource : "/v1/boards/list",
				fnServerData : function(sSource, aoData, fnCallback, oSettings) {
					var start, size, keyword, sortColumn, sortOrder	
				    for (var i = 0; i < aoData.length; i++) {
				        if (aoData[i].name == "iDisplayStart") start = aoData[i].value;
				        else if (aoData[i].name == "iDisplayLength") size = aoData[i].value;
				        else if (aoData[i].name == "sSearch") keyword = aoData[i].value;
				        else if (aoData[i].name == "iSortCol_0") sortColumn = aoData[i].value;
				        else if (aoData[i].name == "sSortDir_0") sortOrder = aoData[i].value;
				    }
				    
					oSettings.jqXHR = $.ajax({
						dataType : "json",
						type : "GET",
						url : sSource,
						data : {
							pageSize : size,
							page : start / size + 1,
							condition : "all",
							keyword : keyword,
							sortColumn : cols[sortColumn].data,
							sortOrder : sortOrder.toUpperCase()
						},
						success : fnCallback,
						dataFilter : function(data){
							var json = JSON.parse(data);
							json.recordsTotal = json.meta.totalRecords;
							json.recordsFiltered = json.meta.totalRecords;
							json.data = json.data;
							return JSON.stringify(json);
						}
					})
				},
				columns : cols,
				language : {
		            	processing : "Loading..."
		            },				
				drawCallback : function(settings) { 
			        var response = settings.json;
					$("[data-toggle='popover']").popover({
						html : true,
						sanitize : false,
						trigger : "focus"
					});
			    },
			});
		});
		CKEDITOR.replace("content", {
		      height: 150
		});

	});
</script>
