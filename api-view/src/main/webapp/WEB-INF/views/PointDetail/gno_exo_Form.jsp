<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<div>
		<label>CHROM</label>
		<div>
			<c:out value="${gno_exo.CHROM}" />
		</div>
	</div>
	<div>
		<label>START</label>
		<div>
			<c:out value="${gno_exo.START}" />
		</div>
	</div>
	<div>
		<label>END</label>
		<div>
			<c:out value="${gno_exo.END}" />
		</div>
	</div>
	<div>
		<label>REF</label>
		<div>
			<c:out value="${gno_exo.REF}" />
		</div>
	</div>
	<div>
		<label>ALT</label>
		<div>
			<c:out value="${gno_exo.ALT}" />
		</div>
	</div>
	<div>
		<label>AF_GNOMAD</label>
		<div>
			<c:out value="${gno_exo.AF_GNOMAD}" />
		</div>
	</div>
	<div>
		<label>AF_AFR</label>
		<div>
			<c:out value="${gno_exo.AF_AFR}" />
		</div>
	</div>
	<div>
		<label>AF_AMR</label>
		<div>
			<c:out value="${gno_exo.AF_AMR}" />
		</div>
	</div>
	<div>
		<label>AF_ASJ</label>
		<div>
			<c:out value="${gno_exo.AF_ASJ}" />
		</div>
	</div>
	<div>
		<label>AF_EAS</label>
		<div>
			<c:out value="${gno_exo.AF_EAS}" />
		</div>
	</div>
	<div>
		<label>AF_FIN</label>
		<div>
			<c:out value="${gno_exo.AF_FIN}" />
		</div>
	</div>
	<div>
		<label>AF_NFE</label>
		<div>
			<c:out value="${gno_exo.AF_NFE}" />
		</div>
	</div>
	<div>
		<label>AF_OTH</label>
		<div>
			<c:out value="${gno_exo.AF_OTH}" />
		</div>
	</div>
	<div>
		<label>AF_SAS</label>
		<div>
			<c:out value="${gno_exo.AF_SAS}" />
		</div>
	</div>
</div>