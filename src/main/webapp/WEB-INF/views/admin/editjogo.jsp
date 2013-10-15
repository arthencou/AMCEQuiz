<%@include file="/WEB-INF/views/include.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/editjogo.css" rel="stylesheet">

<div class="row">
	<div class="col-md-12">
		<div class="jumbotron">
			<h2 id="tituloPagina">Identificação</h2>
			<p>Aqui são preenchidos os dados de identificação do jogo.</p>
		</div>
	</div>
</div>

<div class="panel">
	<div class="panel-heading">
		<h3 class="panel-title"><strong>Grupo</strong></h3>
	</div>
	<div class="panel-body">
		<div class="row">
			<div id="descricaoGrupo" class="col-md-3">
				<p>Um agrupamento permite que o ranking de alunos considere mais de um jogo. 
				No entanto, este campo é opcional.</p>
			</div>
			<div class="col-md-5">
				<form:form class="form-horizontal" modelAttribute="agrupamento">
					<div class="container-fluid"><div class="row-fluid"><div class="control-group">
						<label for="inputNomeGrupo" class="control-label">Nome</label>
						<div class="input-group">
							<span class="input-group-addon">
								<form:checkbox id="checkboxInputNomeGrupo" path="checked"
									value="${agrupamento.checked}"
									onchange="ativacaoInputNomeGrupo();" />
							</span>
							<form:input path="nome" type="text" required="false" value="${agrupamento.nome}"
								id="inputNomeGrupo" class="form-control" placeholder="Nome do grupo"/>
						</div>
					</div></div></div>
				</form:form>
			</div>
		</div>
	</div>
</div>

<div class="panel">
	<div class="panel-heading">
		<h3 class="panel-title"><strong>Jogo</strong></h3>
	</div>
	<div class="panel-body">
		<div class="row">
			<div id="descricaoJogo" class="col-md-3">
				<p>Preencha as informações específicas do jogo.</p>
			</div>
			<div class="col-md-5">
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
		
		<core:if test="${saveSuccessful == false}">
			<div class="alert alert-danger">
				Não é possível criar um jogo com esse título.
				Talvez já exista um jogo com mesmo nome no grupo especificado.
			</div>
		</core:if>
		
		<button class="btn btn-info pull-right" onclick="saveChanges('gotoQuestoes','');">
			Prosseguir
		</button>
	</div>
</div>


<script>
function saveChanges(procedimento, args) {
	saveAgrupamento(procedimento, args);
}
function saveAgrupamento(procedimento, args) {
	var agrupamento = $('#agrupamento').serialize();
	$.ajax({
		type : "POST",
		url : "/${pageContext.request.contextPath}/admin/saveagrupamento",
		data : agrupamento,
		success : function() {
			saveJogo(procedimento, args);
		},
		error : function(e) {
			alert('Erro ao persistir o agrupamento! Error: ' + e);
		}
	});
}
function saveJogo(procedimento, args) {
	var jogo = $('#jogo').serialize();
	$.ajax({
		type : "POST",
		url : "/${pageContext.request.contextPath}/admin/savejogo",
		data : jogo,
		success : function() {
			window[procedimento](args);
		},
		error : function(e) {
			alert('Erro ao persistir o jogo! Error: ' + e.responseText);
		}
	});
}
function ativacaoInputNomeGrupo() {
	if ($('#checkboxInputNomeGrupo').prop('checked')) {
		$('#inputNomeGrupo').attr('readonly', false);
	} else {
		$('#inputNomeGrupo').attr('readonly', true);
	}
}
$(document).ready(function() {
});
</script>