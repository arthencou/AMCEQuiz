<%@include file="/WEB-INF/views/include.jsp"%>
<%@include file="/WEB-INF/views/header.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/home.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/gameover.css" rel="stylesheet">

<%@include file="/WEB-INF/views/barra.jsp"%>

<style>
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
									<span class="azul">correta</span>
									<core:set var="totalAcertos" value="${totalAcertos+1}" />
								</core:when>
								<core:when test="${jogada.value.isCorrect == 0}">
									<span class="negrito">não respondida</span>
								</core:when>
								<core:otherwise>
									<span class="vermelho">incorreta</span>
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
			Total de acertos: <span class="negrito">${totalAcertos}</span>
		</p>
		<p>
			Tempo total de jogo: 
			<core:choose>
				<core:when test="${tempoTotalJogo >= 61}">
					<fmt:formatNumber var="ttm" value="${tempoTotalJogo/60-0.5}" 
						maxFractionDigits="0" />
					<fmt:formatNumber var="tts" value="${(tempoTotalJogo/60-ttm)*60}" 
						maxFractionDigits="0" />
					<span class="negrito">
						${ttm} 
						<core:choose>
							<core:when test="${ttm > 1}">minutos</core:when>
							<core:otherwise>minuto</core:otherwise>
						</core:choose>
						e ${tts} segundos.
					</span>
				</core:when>
				<core:otherwise>
					<span class="negrito">${tempoTotalJogo} segundos</span>
				</core:otherwise>
			</core:choose>
		</p>
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>