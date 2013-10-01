<%@include file="/WEB-INF/views/header.jsp"%>
<%@include file="/WEB-INF/views/include.jsp"%>

<core:forEach items="${jogadasDados}" var="jogada">
	Questao ${jogada.key}
	<core:choose>
		<core:when test="${jogada.value.isCorrect == true}">
			<p>    - Respota correta</p>
		</core:when>
		<core:otherwise>
			<p>    - Respota incorreta</p>
		</core:otherwise>
	</core:choose>
	<core:set 
		var="totalTime" 
		value="${(jogada.value.timeFinish - jogada.value.timeStart) / 1000}" />
	<p>    - Tempo de resposta: 
	<core:choose>
		<core:when test="${totalTime >= 61}">
			${totalTime / 60 } minutos.
		</core:when>
		<core:otherwise>
			${totalTime } segundos.
		</core:otherwise>
	</core:choose></p>
</core:forEach>

<%@include file="/WEB-INF/views/footer.jsp"%>