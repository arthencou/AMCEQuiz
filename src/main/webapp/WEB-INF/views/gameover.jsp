<%@include file="/WEB-INF/views/include.jsp"%>
<%@include file="/WEB-INF/views/header.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/home.css" rel="stylesheet">

<%@include file="/WEB-INF/views/barra.jsp"%>

<style>
.negrito {
  	padding-left: 15px;
	font-weight:bold;
}
.azul {
  	padding-left: 15px;
	font-weight:bold;
	color:blue;
}
.vermelho {
  	padding-left: 15px;
	font-weight:bold;
	color:red;
}
</style>

<div class="panel panel-default">
	<div class="panel-heading">
		<h1 class="panel-title">Resumo do jogo</h1>
	</div>
	<div class="panel-body">
		<core:choose>
			<core:when test="${save == true}">
				<core:set var="totalAcertos" value="${0}" />
				<core:forEach items="${jogadasDados}" var="jogada">
					<ul>
						<li>Questão ${jogada.key}:
							<core:choose>
								<core:when test="${jogada.value.isCorrect() == true}">
									<p class="azul">correta</p>
									<core:set var="totalAcertos" value="${totalAcertos+1}" />
								</core:when>
								<core:when test="${jogada.value.isCorrect == 0}">
									<p class="negrito">não respondida</p>
								</core:when>
								<core:otherwise>
									<p class="vermelho">incorreta</p>
								</core:otherwise>
							</core:choose>
						</li>
					</ul>
				</core:forEach>
				<div class="alert alert-info">
					Seu desempenho foi submetido ao ranking.
				</div>
			</core:when>
			<core:otherwise>
				<div class="alert alert-warning">
					Você desistiu do jogo. Seu desempenho não foi submetido ao ranking.
				</div>
			</core:otherwise>
		</core:choose>
	</div>
	<div class="panel-footer">
		<p>
			Total de acertos: <div class="negrito">${totalAcertos}</div>
		</p>
		<p>
			Tempo total de jogo: 
			<core:choose>
				<core:when test="${tempoTotalJogo >= 61}">
					<fmt:formatNumber var="ttm" value="${tempoTotalJogo/60}" 
						maxFractionDigits="0" />
					<fmt:formatNumber var="tts" value="${(tempoTotalJogo/60-ttm)*60}" 
						maxFractionDigits="0" />
					<div class="negrito">${tt} minutos e ${tts} segundos.</div>
				</core:when>
				<core:otherwise>
					<div class="negrito">${tempoTotalJogo} segundos</div>
				</core:otherwise>
			</core:choose>
		</p>
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>