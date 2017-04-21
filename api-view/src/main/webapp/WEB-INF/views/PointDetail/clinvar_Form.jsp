<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<div>
		<label>CHROM</label>
		<div>
			<c:out value="${clinvar.CHROM}" />
		</div>
	</div>
	<div>
		<label>START</label>
		<div>
			<c:out value="${clinvar.START}" />
		</div>
	</div>
	<div>
		<label>END</label>
		<div>
			<c:out value="${clinvar.END}" />
		</div>
	</div>
	<div>
		<label>REF</label>
		<div>
			<c:out value="${clinvar.REF}" />
		</div>
	</div>
	<div>
		<label>ALT</label>
		<div>
			<c:out value="${clinvar.ALT}" />
		</div>
	</div>
	<div>
		<label>CLNSIG</label>
		<div>
			<c:out value="${clinvar.CLNSIG}" />
		</div>
	</div>
	<div>
		<label>CLNDBN</label>
		<div>
			<c:out value="${clinvar.CLNDBN}" />
		</div>
	</div>
	<div>
		<label>CLNACC</label>
		<div>
			<c:out value="${clinvar.CLNACC}" />
		</div>
	</div>
	<div>
		<label>CLNDSDB</label>
		<div>
			<c:out value="${clinvar.CLNDSDB}" />
		</div>
	</div>
	<div>
		<label>CLNDSDBID</label>
		<div>
			<c:out value="${clinvar.CLNDSDBID}" />
		</div>
	</div>
</div>