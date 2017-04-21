<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<div>
		<label>CHROM</label>
		<div>
			<c:out value="${annovar.CHROM}" />
		</div>
	</div>
	<div>
		<label>START</label>
		<div>
			<c:out value="${annovar.START}" />
		</div>
	</div>
	<div>
		<label>END</label>
		<div>
			<c:out value="${annovar.END}" />
		</div>
	</div>
	<div>
		<label>REF</label>
		<div>
			<c:out value="${annovar.REF}" />
		</div>
	</div>
	<div>
		<label>ALT</label>
		<div>
			<c:out value="${annovar.ALT}" />
		</div>
	</div>
	<div>
		<label>SIFT_score</label>
		<div>
			<c:out value="${annovar.SIFT_score}" />
		</div>
	</div>
	<div>
		<label>SIFT_pred</label>
		<div>
			<c:out value="${annovar.SIFT_pred}" />
		</div>
	</div>
	<div>
		<label>Polyphen2_HDIV_score</label>
		<div>
			<c:out value="${annovar.polyphen2_HDIV_score}" />
		</div>
	</div>
	<div>
		<label>Polyphen2_HDIV_pred</label>
		<div>
			<c:out value="${annovar.polyphen2_HDIV_pred}" />
		</div>
	</div>
	<div>
		<label>Polyphen2_HVAR_score</label>
		<div>
			<c:out value="${annovar.polyphen2_HVAR_score}" />
		</div>
	</div>
	<div>
		<label>Polyphen2_HVAR_pred</label>
		<div>
			<c:out value="${annovar.polyphen2_HVAR_pred}" />
		</div>
	</div>
</div>