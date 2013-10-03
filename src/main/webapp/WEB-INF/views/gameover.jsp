<%@include file="/WEB-INF/views/header.jsp"%>
<%@include file="/WEB-INF/views/include.jsp"%>

<div class="container-fluid">
	<ul class="nav nav-pills pull-right">
		<li class="active"><a href="/amcequiz/gameover?save=true">Salvar jogo</a></li>
		<li><a href="/amcequiz/gameover">Desistir do jogo</a></li>
	</ul>
</div>

<style>
negrito {
	font-weight:bold;
}
azul {
	font-weight:bold;
	color:blue;
}
vermelho {
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
						<li>Questao ${jogada.key}:
							<core:choose>
								<core:when test="${jogada.value.isCorrect() == true}">
									<azul>Respota correta</azul>
									<core:set var="totalAcertos" value="${totalAcertos+1}" />
								</core:when>
								<core:otherwise>
									<vermelho>Respota incorreta</vermelho>
								</core:otherwise>
							</core:choose>
						</li>
					</ul>
				</core:forEach>
				<div class="alert alert-info">
					Seu desempenho foi submetido no ranking.
				</div>
			</core:when>
			<core:otherwise>
				<div class="alert alert-warning">
					Você desistiu do jogo. Seu desempenho não foi submetido no ranking.
				</div>
			</core:otherwise>
		</core:choose>
	</div>
	<div class="panel-footer">
		<p>
			Total de acertos: <negrito>${totalAcertos}</negrito>
		</p>
		<p>
			Tempo total de jogo: 
			<core:choose>
				<core:when test="${tempoTotalJogo >= 61}">
					<negrito>${tempoTotalJogo / 60} minutos</negrito>
				</core:when>
				<core:otherwise>
					<negrito>${tempoTotalJogo} segundos</negrito>
				</core:otherwise>
			</core:choose>
		</p>
	</div>
</div>



<%@include file="/WEB-INF/views/footer.jsp"%>