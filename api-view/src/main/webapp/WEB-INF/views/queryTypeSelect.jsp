<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Query Type Select</title>
<link rel="stylesheet" href='<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/latest/css/bootstrap.min.css" />' >
</head>
<body>

<div>
	<button class="btn btn-default" type="button" onclick="window.open('singlesample');return false;">Single Sample Query</button>
	<button class="btn btn-default" type="button" onclick="window.open('threesample');return false;">Three Sample Query</button>
	<button class="btn btn-default" type="button" onclick="window.open('singlesampleall');return false;">Single Sample All Query</button>	
</div>

</body>
</html>