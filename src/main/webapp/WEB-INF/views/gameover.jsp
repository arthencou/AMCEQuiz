<%@include file="/WEB-INF/views/header.jsp"%>
<%@include file="/WEB-INF/views/include.jsp"%>

<core:forEach items="${jogadasDados}" var="jogada">
	<ul>
		<li>Questao ${jogada.key}
			<ul>
				<core:choose>
					<core:when test="${jogada.value.isCorrect == true}">
						<li>Respota correta</li>
					</core:when>
					<core:otherwise>
						<li>Respota incorreta</li>
					</core:otherwise>
				</core:choose>
				<core:set 
					var="totalTime" 
					value="${(jogada.value.timeFinish - jogada.value.timeStart) / 1000}" />
				<li>Tempo de resposta: 
					<core:choose>
						<core:when test="${totalTime >= 61}">
							${totalTime / 60 } minutos.
						</core:when>
						<core:otherwise>
							${totalTime } segundos.
						</core:otherwise>
					</core:choose>
				</li>
			</ul>
		</li>
	</ul>
</core:forEach>

<%@include file="/WEB-INF/views/footer.jsp"%>