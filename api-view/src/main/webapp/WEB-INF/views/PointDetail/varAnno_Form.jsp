<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<div>
		<label>CHROM</label>
		<div>
			<c:out value="${varAnno.CHROM}" />
		</div>
	</div>
	<div>
		<label>POS</label>
		<div>
			<c:out value="${varAnno.POS}" />
		</div>
	</div>
	<div>
		<label>REF</label>
		<div>
			<c:out value="${varAnno.REF}" />
		</div>
	</div>
	<div>
		<label>ALT</label>
		<div>
			<c:out value="${varAnno.ALT}" />
		</div>
	</div>
	<div>
		<label>RS</label>
		<div>
			<c:out value="${varAnno.RS}" />
		</div>
	</div>
	<div>
		<label>Gene Symbol</label>
		<div>
			<c:out value="${varAnno.geneSymbol}" />
		</div>
	</div>
	<div>
		<label>C dot</label>
		<div>
			<c:out value="${varAnno.cdot}" />
		</div>
	</div>
	<div>
		<label>P dot</label>
		<div>
			<c:out value="${varAnno.pdot}" />
		</div>
	</div>
	<div>
		<label>Category</label>
		<div>
			<c:out value="${varAnno.category}" />
		</div>
	</div>
	<div>
		<label>Comments</label>
		<div>
			<c:out value="${varAnno.comments}" />
		</div>
	</div>
</div>