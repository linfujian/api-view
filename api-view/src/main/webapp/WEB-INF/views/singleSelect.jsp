<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
 <link rel="stylesheet"
		href='<c:url value="/web-resources/lib/bootstrap-3.3.7/css/bootstrap.min.css" />'>
 <link rel="stylesheet"
		href='<c:url value="/web-resources/lib/jquery-ui-1.12.1.custom/jquery-ui.min.css" />' >	
<title>Single Query</title>
</head>
<body>
<p>
<button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#queryPanel" aria-expanded="false" aria-controls="queryPanel">
    show/hide Query
 </button>
</p>
<div class="collapse" id="queryPanel">
  <div class="card card-block">
	<form class="form-horizontal">
	  <div class="form-group">
	    <label for="sampleId" class="col-sm-2 control-label">SampleId</label>
	    <div class="col-sm-5">
	      <input type="text" class="form-control" id="input_sample" placeholder="SampleId">
	    </div>
	  </div>
	</form>
	<div class="col-sm-offset-2 col-sm-12">
		<label class="radio-inline">
		  <input type="radio" name="inlineRadioOptions" id="rangeQuery"  onclick="getRadio('RangeQuery')">RangeQuery
		</label>
		<label class="radio-inline">
		  <input type="radio" name="inlineRadioOptions" id="symbolQuery" onclick="getRadio('SymbolQuery')">SymbolQuery
		</label>
		<label class="radio-inline">
		  <input type="radio" name="inlineRadioOptions" id="nmQuery" onclick="getRadio('NMQuery')">NMQuery
		</label>
	</div>
	<div id="queryInput" ></div>
  </div>
</div>

<form:form>
	<div id="showresult">
	</div>
</form:form>

<input type="hidden" id="queryType" />
<input type="hidden" id="page" />
<div class="col-sm-offset-7" id="page-selection"></div>

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
<div class="dialog" id="varAnno_detail" style="display:none;">
	<%@ include file="PointDetail/varAnno_Form.jsp" %>
</div>
<div class="dialog" id="history_detail" style="display:none;">
	<%@ include file="PointDetail/varAnnoHis_Form.jsp" %>
</div>
<div class="dialog" id="hgmd_detail" style="display:none;">
	<%@ include file="PointDetail/hgmd_Form.jsp" %>
</div>

<script src='<c:url value="/web-resources/lib/jquery/jquery-3.2.0.min.js" />'></script>
<script src='<c:url value="/web-resources/lib/jquery/bootpag.min.js" />'></script>
<script src='<c:url value="/web-resources/lib/bootstrap-3.3.7/js/bootstrap.min.js" />'></script>
<script type="text/javascript" src='<c:url value="/web-resources/lib/jquery-ui-1.12.1.custom/jquery-ui.min.js" />'></script>
<script type="text/javascript" src='<c:url value="/web-resources/js/js-for-queryselect.js" />'></script>	
</body>
</html>