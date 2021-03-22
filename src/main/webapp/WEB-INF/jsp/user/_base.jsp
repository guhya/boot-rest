<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<title>Grid Application</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/cupertino/jquery-ui.css" />
	
	<script src="//unpkg.com/jquery@2.2.4/dist/jquery.js"></script>    
    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
		integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<link rel="stylesheet" href="/resources/user/lib/pqgrid/pqgrid.dev.css" />
	<script type="text/javascript" src="/resources/user/lib/pqgrid/pqgrid.dev.js"></script>

<body>
	<%
		/*####################################### */
	%>
	<tiles:insertAttribute name="header" />
	<%
		/*####################################### */
	%>

	<%
		/*####################################### */
	%>
	<tiles:insertAttribute name="body" />
	<%
		/*####################################### */
	%>

	<%
		/*####################################### */
	%>
	<tiles:insertAttribute name="footer" />
	<%
		/*####################################### */
	%>
	
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script>
		$.fn.bootstrapBtn = $.fn.button.noConflict();
		$.fn.bootstrapTooltip = $.fn.tooltip.noConflict();     
	</script>
	
</body>
</html>