<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<div>
		<label>CHROM</label>
		<div>
			<c:out value="${exac.CHROM}" />
		</div>
	</div>
		<div>
		<label>START</label>
		<div>
			<c:out value="${exac.START}" />
		</div>
	</div>
	<div>
		<label>END</label>
		<div>
			<c:out value="${exac.END}" />
		</div>
	</div>
	<div>
		<label>REF</label>
		<div>
			<c:out value="${exac.REF}" />
		</div>
	</div>
	<div>
		<label>ALT</label>
		<div>
			<c:out value="${exac.ALT}" />
		</div>
	</div>
	<div>
		<label>AF_ALL</label>
		<div>
			<c:out value="${exac.EXAC_ALL}" />
		</div>
	</div>
	<div>
		<label>AF_AFR</label>
		<div>
			<c:out value="${exac.EXAC_AFR}" />
		</div>
	</div>
	<div>
		<label>AF_AMR</label>
		<div>
			<c:out value="${exac.EXAC_AMR}" />
		</div>
	</div>
	<div>
		<label>AF_EAS</label>
		<div>
			<c:out value="${exac.EXAC_EAS}" />
		</div>
	</div>
	<div>
		<label>AF_FIN</label>
		<div>
			<c:out value="${exac.EXAC_FIN}" />
		</div>
	</div>
	<div>
		<label>AF_NFE</label>
		<div>
			<c:out value="${exac.EXAC_NFE}" />
		</div>
	</div>
	<div>
		<label>AF_OTH</label>
		<div>
			<c:out value="${exac.EXAC_OTH}" />
		</div>
	</div>
	<div>
		<label>AF_SAS</label>
		<div>
			<c:out value="${exac.EXAC_SAS}" />
		</div>
	</div>
</div>