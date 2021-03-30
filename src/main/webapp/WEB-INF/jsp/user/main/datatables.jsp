<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
	<div class="col mb-4">
		<button type="button" data-toggle="modal" data-target="#modalForm" 
			class="btn btn-primary">Write</button>
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
						<input type="hidden" class="form-control" name="seq" id="seq" value="" required>
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
					<div class="form-group">
						<input type="file" class="form-control-file" id="file">
					</div>
					<input type="submit" name="submitHandler" style="display:none"/>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" type="submit" onclick="doSubmit()">Submit</button>
			</div>
		</div>
	</div>
</div>

<script>
	var authHeader = "";
	var checkHeader = function(req){
		if(authHeader == ""){
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
		        	authHeader = res.data.attributes.token;
		        }
			});
		}else{
			var d = new $.Deferred();
			req.setRequestHeader("Authorization", "Bearer " + authHeader.trim());
			return d.promise();
		}
	};

	var doEdit = function(id){
		$.ajax({
	        dataType : "JSON",
	        method : "GET",
	        global : false,
	        contentType : "application/json",
	        url : "/v1/boards/get/"+id,
	        success : function(res){
	        	$.each(res.data.attributes, function(key, val) {
	        		$("#"+key).val(val);
        	    });
	        	dt.draw();
	        }
		});
	};
	
	var doDelete = function(id){
		$.ajax({
	        dataType : "JSON",
	        method : "POST",
	        global : false,
	        contentType : "application/json",
	        url : "/v1/boards/delete",
	        data : JSON.stringify({
	        	"seq" : id
	        }),
	        success : function(res){
	        	dt.draw();
	        }
		});
	};
	
	var doSubmit = function(){
		
		var frm = document.inputForm;
		if (!frm.checkValidity()) {
			frm.submitHandler.click();
			return;
		}
		
		var data = {};
		var arr = $("#inputForm").serializeArray();
		for(el in arr){
			if(arr[el].name == "seq" && arr[el].value == "") continue;
			data[arr[el].name] = arr[el].value;
		}
		var url = "";
		if(data.seq != undefined)
			url = "/v1/boards/update";
		else
			url = "/v1/boards/create";
		
		$.ajax({
	        dataType : "JSON",
	        method : "POST",
	        global : false,
	        contentType : "application/json",
	        url : url,
	        data : JSON.stringify(data),
	        success : function(res){
	        	$("#modalForm").modal("hide");
	        	dt.draw();
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
	var cols = [
		{"data" : "seq", "width": "5%"},
		{"data" : "title", "width": "15%"},
		{"data" : "subtitle", "width": "10%"},
		{"data" : "summary", "width": "15%"},
		{
			"className" : "", 
			"width": "30%",
			"render" : function (data, type, row, meta) {
				var val = row.content.length > 35 ? row.content.substring(0, 35) + "..." : row.content;
				return val;
			}
		},
		{"data" : "author","className" : "text-center", "width": "5%"},
		{"data" : "regDate","className" : "text-center", "width": "10%"},
		{
			"className" : "text-center",
			"width": "10%",
			"render" : function (data, type, row, meta) {
				return "<button type='button' onclick='doEdit("+row.seq+")' class='btn btn-secondary' data-toggle='modal' data-target='#modalForm'>Edit</button>"
				+"&nbsp;<button type='button' onclick='doDelete("+row.seq+")' class='btn btn-danger'>Delete</button>"
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
							json.data = json.data.attributes;
							return JSON.stringify(json);
						}
					})
				},
				columns : cols,
				drawCallback : function(settings) { 
			        var response = settings.json;
			    },
			});
		});
	});
</script>
