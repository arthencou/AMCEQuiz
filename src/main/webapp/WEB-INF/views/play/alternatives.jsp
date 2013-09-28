<%@include file="/WEB-INF/views/include.jsp"%>
	<core:forEach items="${alternativasList}" var="alternativa">
		<p><a href="#" onclick="submitResposta('${alternativa.key}')">
			<core:out value="${alternativa.key}) ${alternativa.value}" />
		</a></p>
	</core:forEach>