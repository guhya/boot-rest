<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<style>
.container {padding: 2rem 0rem;}
@media (min-width: 576px){
  #modalLogin.modal-dialog {max-width: 400px;.modal-content {padding: 1rem;}}
}
.form-title {margin: -2rem 0rem 2rem;}
.btn-round {border-radius: 3rem;}
.delimiter {padding: 1rem;}
.signup-section {padding: 0.3rem 0rem;}
.fade{-webkit-transition-property: none;-moz-transition-property: none;-o-transition-property: none;transition-property: none;transition:none;}
.fade#modalLogin {background : #6c757d;}
</style>

<div class="modal fade fadeLogin" id="modalLogin" 
	tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true"
	data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header border-bottom-0">
			</div>
			<div class="modal-body">
				<div class="form-title text-center">
					<h4>Login</h4>
				</div>
				<div class="d-flex flex-column text-center">
					<form id="loginForm">
						<div class="form-group">
							<input type="text" class="form-control" id="username"
								placeholder="Username">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" id="password"
								placeholder="Password">
						</div>
						<div class="form-group">
							<div class="invalid-feedback" id="invalid-login" style="display:none;"></div>
						</div>
						<div class="form-group">
							<button type="button" class="btn btn-info btn-block btn-round" onclick="doLogin()">Login</button>
						</div>
					</form>
				</div>
			</div>
			<div class="modal-footer d-flex justify-content-center">
				<div class="signup-section">
					Not a member yet? <a href="#a" class="text-info"> Sign Up</a>.
				</div>
			</div>
		</div>
	</div>
</div>


<script>

	$(document).ready(function(){
		$("#password").on("keyup", function(e){
			if(e.keyCode == 13) doLogin();
		})		
	});
	
	var setCookie = function(name,value,days) {
	    var expires = "";
	    if (days) {
	        var date = new Date();
	        date.setTime(date.getTime() + (days*24*60*60*1000));
	        expires = "; expires=" + date.toUTCString();
	    }
	    document.cookie = name + "=" + (value || "")  + expires + "; path=/";
	};

	var getCookie = function(name) {
	    var nameEQ = name + "=";
	    var ca = document.cookie.split(';');
	    for(var i=0;i < ca.length;i++) {
	        var c = ca[i];
	        while (c.charAt(0)==' ') c = c.substring(1,c.length);
	        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	    }
	    return "";
	};


	$.ajaxSetup({
		beforeSend : function (xhr) {
			checkHeader(xhr);
		}
	});

	var authHeader = getCookie("token");
	var checkHeader = function(req){
		if(authHeader == undefined || authHeader == ""){
			if(req) req.abort();
			showLogin(); 
			return $.Deferred().reject().promise();
		}else{
			if (req != undefined) req.setRequestHeader("Authorization", "Bearer " + authHeader.trim());
			return $.Deferred().resolve().promise();
		}
	};

	var showLogin = function(){
		$("#modalLogin").modal("show");
	};

	var doLogin = function(){
        $("#invalid-login").html("").hide();
		var username = $("#username").val();
		var password = $("#password").val();
		$.ajax({
	        dataType : "JSON",
	        method : "POST",
	        global : false,
	        contentType : "application/json",
	        url : "/v1/users/login",
	        beforeSend : function (xhr) {
				console.log("Logging in");
			},		        
	        data : JSON.stringify({
	        	"username" : username,
	        	"password" : password
	        }),
	        success : function(res){
	        	authHeader = res.data.token;
	        	setCookie("token", authHeader, 1);
	        	window.location.reload();
	        },
	        error : function(res){
		        $("#invalid-login").html(res.responseJSON.message).show();
		    }
		});
	};
	
</script>
