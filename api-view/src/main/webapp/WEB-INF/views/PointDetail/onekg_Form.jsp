<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<div>
		<label>CHROM</label>
		<div>
			<c:out value="${onekg.CHROM}" />
		</div>
	</div>
		<div>
		<label>POS</label>
		<div>
			<c:out value="${onekg.POS}" />
		</div>
	</div>
	<div>
		<label>REF</label>
		<div>
			<c:out value="${onekg.REF}" />
		</div>
	</div>
	<div>
		<label>OBS</label>
		<div>
			<c:out value="${onekg.OBS}" />
		</div>
	</div>
	<div>
		<label>AF_EAS</label>
		<div>
			<c:out value="${onekg.AF_EAS}" />
		</div>
	</div>
	<div>
		<label>RS</label>
		<div>
			<c:out value="${onekg.RS}" />
		</div>
	</div>
</div>