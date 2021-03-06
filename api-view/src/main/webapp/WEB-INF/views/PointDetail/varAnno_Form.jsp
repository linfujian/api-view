<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div>
	<div>
		<label>CHROM</label>
		<div>
			<c:out value="${varAnno.CHROM}" />
		</div>
	</div>
	<div>
		<label>START</label>
		<div>
			<c:out value="${varAnno.START}" />
		</div>
	</div>
	<div>
		<label>END</label>
		<div>
			<c:out value="${varAnno.END}" />
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
		<label>Func</label>
		<div>
			<c:out value="${varAnno.func}" />
		</div>
	</div>
	<div>
		<label>ExonicFunc</label>
		<div>
			<c:out value="${varAnno.exonicFunc}" />
		</div>
	</div>
	<div>
		<label>C dot</label>
		<div>
			<c:set var="cdotParts" value="${fn:split(varAnno.cdot, '/')}" />
			<c:forEach items="${cdotParts}" var="cdot">
				<c:out value="${cdot}" /> <br />
			</c:forEach>
		</div>
	</div>
	<div>
		<label>P dot</label>
		<div>
			<c:set var="pdotParts" value="${fn:split(varAnno.pdot, '/')}" />
			<c:forEach items="${pdotParts}" var="pdot">
				<c:out value="${pdot}" /> <br />
			</c:forEach>
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