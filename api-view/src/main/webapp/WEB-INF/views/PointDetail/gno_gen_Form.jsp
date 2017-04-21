<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<div>
		<label>CHROM</label>
		<div>
			<c:out value="${gno_gen.CHROM}" />
		</div>
	</div>
		<div>
		<label>POS</label>
		<div>
			<c:out value="${gno_gen.POS}" />
		</div>
	</div>
	<div>
		<label>RS</label>
		<div>
			<c:out value="${gno_gen.ID}" />
		</div>
	</div>
	<div>
		<label>REF</label>
		<div>
			<c:out value="${gno_gen.REF}" />
		</div>
	</div>
	<div>
		<label>ALT</label>
		<div>
			<c:out value="${gno_gen.ALT}" />
		</div>
	</div>
	<div>
		<label>AF_GNOMAD</label>
		<div>
			<c:out value="${gno_gen.AF_GNOMAD}" />
		</div>
	</div>
	<div>
		<label>AF_AFR</label>
		<div>
			<c:out value="${gno_gen.AF_AFR}" />
		</div>
	</div>
	<div>
		<label>AF_AMR</label>
		<div>
			<c:out value="${gno_gen.AF_AMR}" />
		</div>
	</div>
	<div>
		<label>AF_ASJ</label>
		<div>
			<c:out value="${gno_gen.AF_ASJ}" />
		</div>
	</div>
	<div>
		<label>AF_EAS</label>
		<div>
			<c:out value="${gno_gen.AF_EAS}" />
		</div>
	</div>
	<div>
		<label>AF_FIN</label>
		<div>
			<c:out value="${gno_gen.AF_FIN}" />
		</div>
	</div>
	<div>
		<label>AF_NFE</label>
		<div>
			<c:out value="${gno_gen.AF_NFE}" />
		</div>
	</div>
	<div>
		<label>AF_OTH</label>
		<div>
			<c:out value="${gno_gen.AF_OTH}" />
		</div>
	</div>
	<div>
		<label>AF_Male</label>
		<div>
			<c:out value="${gno_gen.AF_Male}" />
		</div>
	</div>
	<div>
		<label>AF_Female</label>
		<div>
			<c:out value="${gno_gen.AF_Female}" />
		</div>
	</div>
</div>