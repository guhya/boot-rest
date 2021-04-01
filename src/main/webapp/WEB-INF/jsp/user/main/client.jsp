<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div class="row">
	<div class="col">
		<p>${user.timestamp}</p>
		<p>${user.status}</p>
		<p>${user.data.attributes}</p>
	</div>
</div>


<div class="row">
	<div class="col">
		<p>${board.timestamp}</p>
		<p>${board.status}</p>
		<p>${board.data.attributes.seq}</p>
		<p>${board.data.attributes.title}</p>
		<p>${board.data.attributes.subtitle}</p>
		<p>${board.data.attributes.summary}</p>
		<p>${board.data.attributes.content}</p>
		<p>${board.data.attributes.author}</p>
		<p>${board.data.attributes.regDate}</p>
	</div>
</div>


<div class="row">
	<c:forEach items="${boardList.data.attributes}" var="board">
		<div class="col-xs-12 m-3">
			<span>${board.seq}</span>
			<span>${board.title}</span>
			<span>${board.subtitle}</span>
			<span>${board.summary}</span>
			<span>${board.content}</span>
			<span>${board.author}</span>
			<span>${board.regDate}</span>
		</div>
	</c:forEach>
</div>
