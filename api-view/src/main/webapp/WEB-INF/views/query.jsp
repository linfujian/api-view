<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Query Page</title>
 <link rel="stylesheet"
		href='<c:url value="/web-resources/lib/bootstrap-3.3.7/css/bootstrap.min.css" />'>
 <link rel="stylesheet"
		href='<c:url value="/web-resources/lib/jquery-ui-1.12.1.custom/jquery-ui.min.css" />' >
<style type="text/css">
	.table th {
		text-align: center;
	}
	.table td {
		text-align: center;
	}
	
</style>
</head>
<body>
<div id="query_select">
	<div class="col-sm-4">
		<br>
		<input type="text" class="form-control"  id="input_sample" placeholder="SampleID Input Here">
		<br>
	</div>
	<div class="col-sm-12" id="range_select">
	<button class="btn btn-default" onclick="sendRange()">RangeQuery</button><br><br>
	</div>
 	<div class="col-sm-12" id="symbol_select">
	<button class="btn btn-default" onclick="sendSymbol()">Symbol</button><br><br>
	</div>
	<div class="col-sm-12" id="nm_select">
	<button class="btn btn-default" onclick="sendNM()">NM</button>
	</div>
</div>
<div id="queryPage_range" class="queryPage">
	<form class="form-horizontal" id="range_form">
		<div class="form-group">
		<br>
			<label for="inputChr" class="col-sm-2 control-label">Chr</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="range_chr" placeholder="Chro" />
			</div>
		</div>
		<div class="form-group">
			<label for="inputStart" class="col-sm-2 control-label">Start</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="range_start" placeholder="Start point">
			</div>
		</div>
		<div class="form-group">
			<label for="inputEnd" class="col-sm-2 control-label">End</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="range_end" placeholder="End point">
			</div>
		</div>
	</form>
	<div class="col-sm-offset-2 col-sm-10">
	<button class="btn btn-default" id="range_query">Query</button>
	</div>
</div>

<div id="queryPage_symbol" class="queryPage">
	<form class="form-horizontal">
	<div class="form-group">
	<br>
		<label for="inputSymbol" class="col-sm-2 control-label">Symbol</label>
		<div class="col-sm-5">
		<input type="text" class="form-control" id="symbol" placeholder="Symbol" >
		</div>
	</div>
	</form>
	<div class="col-sm-offset-2 col-sm-10">
	<button class="btn btn-default" id="symbol_query">Query</button>
	</div>
</div>

<div id="queryPage_nm" class="queryPage">
	<form class="form-horizontal">
	<div class="form-group">
	<br>
		<label for="inputNm" class="col-sm-2 control-label">NM</label>
		<div class="col-sm-5">
		<input type="text" class="form-control" id="nm" placeholder="NM" >
		</div>
	</div>
	</form>
	<div class="col-sm-offset-2 col-sm-10">
	<button class="btn btn-default" id="nm_query">Query</button>
	</div>
</div>

<div id="showResult">

</div>

<div class="dialog" id="gno_gen_detail" style="display:none;">
	<%@ include file="PointDetail/gno_gen_Form.jsp" %>
</div>
<div class="dialog" id="gno_exo_detail" style="display:none;">
	<%@ include file="PointDetail/gno_exo_Form.jsp" %>
</div>
<div class="dialog" id="onekg_detail" style="display:none;">
	<%@ include file="PointDetail/onekg_Form.jsp" %>
</div>
<div class="dialog" id="esp_detail" style="display:none;">
	<%@ include file="PointDetail/esp_Form.jsp" %>
</div>
<div class="dialog" id="exac_detail" style="display:none;">
	<%@ include file="PointDetail/exac_Form.jsp" %>
</div>
<div class="dialog" id="annovar_detail" style="display:none;">
	<%@ include file="PointDetail/annovar_Form.jsp" %>
</div>
<div class="dialog" id="clinvar_detail" style="display:none;">
	<%@ include file="PointDetail/clinvar_Form.jsp" %>
</div>

<script type="text/javascript" src='<c:url value="/web-resources/lib/jquery/jquery-3.2.0.min.js" />'></script>
<script type="text/javascript" src='<c:url value="/web-resources/lib/jquery-ui-1.12.1.custom/jquery-ui.min.js" />'></script>
<%-- <script type="text/javascript" src='<c:url value="/web-resources/lib/bootstrap-3.3.7/js/bootstrap.min.js" />'></script> --%>
<script type="text/javascript" src='<c:url value="/web-resources/js/js-for-query.js" />'></script>
<script type="text/javascript">

</script>
</body>
</html>