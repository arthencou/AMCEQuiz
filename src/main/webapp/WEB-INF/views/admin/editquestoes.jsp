<%@include file="/WEB-INF/views/include.jsp"%>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<div class="panel">
	<div class="panel-heading">
		<h3 class="panel-title"><strong>Questões</strong></h3>
	</div>
	<div class="panel-body">
		<div class="btn-group">
			<core:set var="count" value="${0}" />
			<core:forEach items="${questoesList}" var="questao">
				<core:set var="count" value="${count+1}" />
				<button id="questao${count}" type="button" class="btn btn-default" 
					onclick="selecionarQuestao(${count});">
					Questão ${count}
				</button>
			</core:forEach>
		</div>
	</div>
</div>


<div class="container-fluid">
	<div class="panel">
		<div class="panel-heading">
			<h3 id="questaoTitle" class="panel-title">Questão</h3>
		</div>
		<div  id="questao" class="panel-body">
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="panel">
        <div class="panel-heading">
          <h3 class="panel-title">Alternativas</h3>
        </div>
        <table><tbody><tr>
          <td>
			<div id="alternativas" class="container theme-showcase">
			  <core:forEach items="${alternativasList}" var="alternativa">
			  	
			  </core:forEach>
			</div>
		  </td>
		</tr></tbody></table>
	</div>
</div>

<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script>
function selecionarQuestao(qnum) {
	$.ajax({
		type : "POST",
		url : "/amcequiz/admin/setquestao",
		data : "qnum=" + qnum,
		success : function(response) {
			questaoAtual = qnum;
			$('#questaoTitle').text('Questão '+qnum);
			carregarQuestao();
			carregarAlternativas();
		},
		error : function(e) {
			alert('Falha ao selecionar questão! Error: ' + e);
		}
	});
}
</script>