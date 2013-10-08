<%@include file="/WEB-INF/views/include.jsp"%>

<div class="panel">
	<div class="panel-heading">
		<h3 class="panel-title"><strong>Grupo</strong></h3>
	</div>
	<div class="panel-body">
		<p>Um agrupamento permite que o ranking de alunos considere mais de um jogo. 
		No entanto, este campo é opcional.</p>
		<form:form class="form-horizontal" modelAttribute="agrupamento">
			<div class="container-fluid"><div class="row-fluid"><div class="control-group">
				<label for="inputNomeGrupo" class="control-label">Nome</label>
				<div class="input-group">
					<span class="input-group-addon">
						<input type="checkbox" checked="true" />
					</span>
					<form:input path="nome" type="text" required="false" value="${agrupamento.nome}"
						id="inputNomeGrupo" class="form-control" placeholder="Nome do grupo"/>
				</div>
			</div></div></div>
		</form:form>
	</div>
</div>

<div class="panel">
	<div class="panel-heading">
		<h3 class="panel-title"><strong>Jogo</strong></h3>
	</div>
	<div class="panel-body">
		<p>Preencha as informações específicas do jogo.</p>
		<core:if test="${saveSuccessful == false}">
			<div class="alert alert-danger">
				Não é possível criar um jogo com esse título.
				Talvez já exista um jogo com mesmo nome no grupo especificado.
			</div>
		</core:if>
		<form:form class="form-horizontal" modelAttribute="jogo">
			<div class="container-fluid"><div class="row-fluid">
				<div class="control-group">
					<label for="inputNomeJogo" class="control-label">Nome</label>
					<form:input path="nome" type="text" required="true" value="${jogo.nome}"
						id="inputNomeJogo" class="form-control" placeholder="Nome do jogo"/>
				</div>
				<div class="control-group">
					<label for="inputTempoJogo" class="control-label">Limite de tempo</label>
					<form:input path="tempoMaximo" type="number" required="false" value="${jogo.tempoMaximo}"
						 id="inputTempoJogo" class="form-control" />
				</div>
			</div></div>
		</form:form>
	</div>
</div>

<button class="btn btn-info" onclick="submit();">
	Prosseguir
</button>

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
			gotoQuestoes();
		},
		error : function(e) {
			alert('Erro ao persistir o jogo! Error: ' + e.responseText);
		}
	});
}
</script>