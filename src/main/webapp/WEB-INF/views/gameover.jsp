<%@include file="/WEB-INF/views/header.jsp"%>
<%@include file="/WEB-INF/views/include.jsp"%>
<style>
negrito {
	font-weight:bold;
}
azul {
	color:blue;
}
vermelho {
	color:red;
}
</style>

<div class="panel panel-default">
	<div class="panel-heading">
		<h1 class="panel-title">Resumo do jogo</h1>
	</div>
	<div class="panel-body">
		<core:set var="totalAcertos" value="${0}" />
		<core:forEach items="${jogadasDados}" var="jogada">
			<ul>
				<li>Questao ${jogada.key}:
					<core:choose>
						<core:when test="${jogada.value.isCorrect() == true}">
							<negrito><azul>Respota correta</azul></negrito>
							<core:set var="totalAcertos" value="${totalAcertos+1}" />
						</core:when>
						<core:otherwise>
							<negrito><vermelho>Respota incorreta</vermelho></negrito>
						</core:otherwise>
					</core:choose>
				</li>
			</ul>
		</core:forEach>
	</div>
	<div class="panel-footer">
		<p>
			Total de acertos: <negrito>${totalAcertos}</negrito>
		</p>
		<p>
			Tempo total de jogo: 
			<core:choose>
				<core:when test="${tempoTotalJogo >= 61}">
					<negrito>${tempoTotalJogo / 60 } minutos</negrito>
				</core:when>
				<core:otherwise>
					<negrito>${tempoTotalJogo } segundos</negrito>
				</core:otherwise>
			</core:choose>
		</p>
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>