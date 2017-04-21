<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<div>
		<label>CHROM</label>
		<div>
			<c:out value="${esp.CHROM}" />
		</div>
	</div>
	<div>
		<label>START</label>
		<div>
			<c:out value="${esp.START}" />
		</div>
	</div>
	<div>
		<label>END</label>
		<div>
			<c:out value="${esp.END}" />
		</div>
	</div>
	<div>
		<label>REF</label>
		<div>
			<c:out value="${esp.REF}" />
		</div>
	</div>
	<div>
		<label>ALT</label>
		<div>
			<c:out value="${esp.ALT}" />
		</div>
	</div>
	<div>
		<label>AF_ALL</label>
		<div>
			<c:out value="${esp.AF_ALL}" />
		</div>
	</div>
	<div>
		<label>RS</label>
		<div>
			<c:out value="${esp.RS}" />
		</div>
	</div>
</div>