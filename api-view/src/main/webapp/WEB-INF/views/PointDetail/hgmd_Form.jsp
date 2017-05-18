<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<div>
		<label>CHROM</label>
		<div>
			<c:out value="${hgmd.CHROM}" />
		</div>
	</div>
	<div>
		<label>POS</label>
		<div>
			<c:out value="${hgmd.POS}" />
		</div>
	</div>
	<div>
		<label>REF</label>
		<div>
			<c:out value="${hgmd.REF}" />
		</div>
	</div>
	<div>
		<label>ALT</label>
		<div>
			<c:out value="${hgmd.ALT}" />
		</div>
	</div>
	<div>
		<label>CLASS</label>
		<div>
			<c:out value="${hgmd.CLASS}" />
		</div>
	</div>
	<div>
		<label>GENE</label>
		<div>
			<c:out value="${hgmd.GENE}" />
		</div>
	</div>
	<div>
		<label>DNA</label>
		<div>
			<c:out value="${hgmd.DNA}" />
		</div>
	</div>
	<div>
		<label>PROT</label>
		<div>
			<c:out value="${hgmd.PROT}" />
		</div>
	</div>
	<div>
		<label>PHEN</label>
		<div>
			<c:out value="${hgmd.PHEN}" />
		</div>
	</div>
</div>