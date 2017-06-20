<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Query Type Select</title>
<link rel="stylesheet" href='<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/latest/css/bootstrap.min.css" />' >
<style type="text/css">
	.row {
		margin-top: 10px;
		margin-bottom: 10px;
	}
</style>
</head>
<body>
<div class="container">
	<div class="row">
		<div class="col-md-offset-1">
			<button class="btn btn-default" type="button" onclick="window.open('singlesample');return false;">Single Sample Query</button>
			
			<button class="btn btn-default" type="button" onclick="window.open('singlesampleall');return false;">Single Sample All Query</button>
			
			<button class="btn btn-default" type="button" onclick="window.open('triodiffanalytics');return false;">Trio Diff Analytics</button>
			
			<button class="btn btn-default" type="button" onclick="window.open('threesample');return false;">Trio Sample Query</button>
			
			<button class="btn btn-default" type="button" onclick="window.open('uploadvcf');return false;">Upload VCF File</button>	
		</div>

		<div class="col-md-offset-1" style="display:inline-block; width:30%;">
			<h3>SampleId In DB</h3>
			<table class="table table-hover" style="width:50%;">
				<thead>
					<tr>
						<th align="center">No</th>
						<th align="center">Id</th>
					</tr>
				</thead>
				<tbody>
				  <c:forEach items="${sampleIds}" var="point" varStatus="loopCounter">
				  	<tr>
						<td><c:out value="${loopCounter.count}" /></td>
						<td><c:out value="${point}" /></td>
					</tr>
				  </c:forEach>
				</tbody>
			</table>
		</div>

		<div style="display:inline-block; width:30%">
			<h3>Trio Different In DB</h3>
			<table class="table table-hover" style="width:50%;">
				<thead>
					<tr>
						<th align="center">No</th>
						<th align="center">Child</th>
						<th align="center">Father</th>
						<th align="center">Mother</th>
					</tr>
				</thead>
				<tbody>
				  <c:forEach items="${trioDiffIds}" var="point" varStatus="loopCounter">
				  	<tr>
						<td><c:out value="${loopCounter.count}" /></td>
						<td><c:out value="${point.sampleId}" /></td>
						<td><c:out value="${point.fatherId}" /></td>
						<td><c:out value="${point.motherId}" /></td>
					</tr>
				  </c:forEach>
				</tbody>
			</table>
		</div>
			</div>
		</div>

</body>
</html>