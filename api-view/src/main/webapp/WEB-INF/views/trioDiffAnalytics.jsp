<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>TrioDiffAnalytics</title>
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
			
			<div class="col-md-offset-1 col-md-11">
				<div class="col-md-12">
					<h1>Trio Difference Analyztic</h1>
					<form  class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">Child</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="child" placeholder="ChildId" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Father</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="father" placeholder="FatherId" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Mother</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="mother" placeholder="MotherId" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-offset-2 col-md-10">
								<button type="submit" class="btn btn-default" id="analyze">Analyze</button>
							</div>
						</div>
					</form>
				</div>
				<div class="col-md-offset-2 col-md-10">
					<h4 id="info"></h4>
				</div>
			</div>
		</div>
	</div>

<script src='<c:url value="/web-resources/lib/jquery/jquery-3.2.0.min.js" />'></script>
<script src='<c:url value="/web-resources/lib/bootstrap-3.3.7/js/bootstrap.min.js" />'></script>	
<script type="text/javascript">
	$(document).on("click","#analyze", function(e) {
		e.preventDefault();
		$('#info').html('back-end is handing, keep this page and waiting please, <br /> <b>During this time You can not do Save/Update operation</b>');
		$.ajax({
			type: 'GET',
			url: 'handleTrioDiff/' + $('#child').val() + '/' + $('#father').val() + '/' + $('#mother').val(),
			success: function(msg) {
				$('#info').html(msg);
			},
			error: function(msg) {
				$('#info').html(msg);
			}
		});
	}); 
</script>
<script src='<c:url value="/web-resources/lib/jquery/jquery-3.2.0.min.js" />'></script>
<script src='<c:url value="/web-resources/lib/bootstrap-3.3.7/js/bootstrap.min.js" />'></script>
</body>
</html>