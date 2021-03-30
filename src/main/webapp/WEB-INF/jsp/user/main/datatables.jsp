<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
		            <th>Reg. date</th>
		        </tr>
		    </thead>
		    <tbody>
		        <tr>
		            <th>Id</th>
		            <th>Title</th>
		            <th>Subtitle</th>
		            <th>Summary</th>
		            <th>Content</th>
		            <th>Author</th>
		            <th>Reg. date</th>
		        </tr>
		    </tbody>
		</table>
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

	$.ajaxSetup({
		beforeSend : function (req) {
			checkHeader(req);
		}
	});
	var ewdebug = {};

	var dt;
	var cols = [
		{"data" : "seq"},
		{"data" : "title"},
		{"data" : "subtitle"},
		{"data" : "summary"},
		{"data" : "content"},
		{"data" : "author"},
		{"data" : "regDate"},
	];
	$(document).ready(function(){
		checkHeader().done(function(val){
			dt = $("#myTable").DataTable({
				pageLength 	: 10,
				lengthMenu 	: [5, 10, 20, 50, 100],
				bProcessing : true,
				bServerSide : true,
				order : [[ 0, "desc" ]],
				sAjaxSource : "/v1/boards/list",
				fnServerData : function(sSource, aoData, fnCallback, oSettings) {
					console.log(aoData);
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
