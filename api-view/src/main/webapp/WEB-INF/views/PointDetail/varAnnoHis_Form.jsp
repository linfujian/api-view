<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet"
	href='<c:url value="/web-resources/lib/bootstrap-3.3.7/css/bootstrap.min.css" />'>

<table class="table">
	<thead>
		<tr>
			<th>Revision</th>
			<th>DataTime</th>
			<th>Action</th>
			<th>Func</th>
			<th>ExonicFunc</th>
			<th>Cdot</th>
			<th>Pdot</th>
			<th>Category</th>
			<th>Comments</th>
			<th>OperUser</th>
		</tr>
	</thead>
	<tbody>
	  <c:forEach items="${varAnnoHisList}" var="varAnno">
	  	<tr>
	  		<td><c:out value="${varAnno.revision}"></c:out></td>
	  		<td><c:out value="${varAnno.datetime}"></c:out></td>
	  		<td><c:out value="${varAnno.action}"></c:out></td>
	  		<td><c:out value="${varAnno.func}"></c:out> </td>
	  		<td><c:out value="${varAnno.exonicFunc}"></c:out> </td>
	  		<td><c:out value="${varAnno.cdot}"></c:out> </td>
	  		<td><c:out value="${varAnno.pdot}"></c:out> </td>
	  		<td><c:out value="${varAnno.category}"></c:out></td>
	  		<td><c:out value="${varAnno.comments}"></c:out></td>
	  		<td><c:out value="${varAnno.operUser}"></c:out></td>
		</tr>
	  </c:forEach>
	</tbody>
</table>