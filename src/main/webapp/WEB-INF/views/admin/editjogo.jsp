<%@include file="/WEB-INF/views/include.jsp"%>

<core:choose>
	<core:when test="${saveSuccessful == true}">
		<script>gotoQuestoes();</script>
	</core:when>
	<core:otherwise>
		<form:form class="form-horizontal" role="form" modelAttribute="agrupamento">
			<div class="form-group">
				<label for="inputNomeGrupo" class="control-label">Nome do grupo</label>
				<form:input path="nome" type="text" required="false" value="${agrupamento.nome}"
					id="inputNomeGrupo" class="form-control" placeholder="Nome do grupo"/>
			</div>
		</form:form>
		
		<form:form class="form-horizontal" modelAttribute="jogo">
			<div class="form-group">
				<label for="inputNomeJogo" class="control-label">Nome do jogo</label>
				<form:input path="nome" type="text" required="true" value="${jogo.nome}"
					id="inputNomeJogo" class="form-control" placeholder="Nome do jogo"/>
			</div>
			<div class="form-group">
				<label for="inputTempoJogo" class="control-label">Tempo máximo da partida</label>
				<form:input path="tempoMaximo" type="number" required="false" value="${jogo.tempoMaximo}"
					 id="inputTempoJogo" class="form-control" />
			</div>
			
			<core:if test="${saveSuccessful == false}">
				<div class="alert alert-danger">
					Não é possível criar um jogo com esse título.
					Talvez já exista um jogo com mesmo nome no grupo especificado.
				</div>
			</core:if>
		</form:form>
		
		<button class="btn btn-info" onclick="submit();">
			Prosseguir
		</button>
	</core:otherwise>
</core:choose>

<script>
function submit() {
	saveAgrupamento();
}
function saveAgrupamento() {
	var agrupamento = $('#agrupamento').serialize();
	$.ajax({
		type : "POST",
		url : "/amcequiz/admin/saveagrupamento",
		data : agrupamento,
		success : function(response) {
			saveJogo();
		},
		error : function(e) {
			alert('Erro ao persistir o agrupamento! Error: ' + e);
		}
	});
}
function saveJogo() {
	var jogo = $('#jogo').serialize();
	$.ajax({
		type : "POST",
		timeout : 8000,
		url : "/amcequiz/admin/savejogo",
		data : jogo,
		success : function(response) {
			$("#conteudo").html(response);
		},
		error : function(e) {
			alert('Erro ao persistir o jogo! Error: ' + e.responseText);
		}
	});
}
</script>