<%@include file="/WEB-INF/views/include.jsp"%>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<div class="panel">
	<div class="panel-heading">
		<h3 class="panel-title"><strong>Questões</strong></h3>
	</div>
	<div class="panel-body">
		<div class="btn-group">
			<core:set var="contQ" value="${0}" />
			<core:forEach items="${questoesList}" var="questao">
				<core:set var="contQ" value="${contQ+1}" />
				<button id="questao${contQ}" type="button" class="btn btn-default" 
					onclick="selecionarQuestao(${contQ});">
					Questão ${contQ}
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
		<table class="panel-body">
			<tbody><tr>
				<td>
					<div id="questaoCode">
						<div class="span6">
			      			<h2>Corpo da Questão (em HTML e LaTeX)</h2>
			        		<textarea class="field span12" id="textarea" rows="6" 
			        			placeholder="Enter a short synopsis"></textarea>
			    		</div>
					</div>
				</td>
				<td>
					<div id="questaoRendered">
						<core:out value="${questaoAtual.texto}" />
					</div>
					<script>MathJax.Hub.Queue(["Typeset",MathJax.Hub,"questaoRendered"]);</script>
				</td>
			</tr></tbody>
		</table>
	</div>
</div>

<div class="container-fluid">
	<div class="panel">
		<div class="panel-heading">
			<h3 class="panel-title">Alternativas</h3>
		</div>
		<table><tbody><tr>
			<td>
				<div id="alternativasCode" class="container theme-showcase">
					<div class="input-group">
						<core:set var="ContA" value="${0}"/>
						<core:forEach items="${alternativasList}" var="alternativa">
							<span class="input-group-addon">
								<input type="radio"
									<core:if test="${questaoAtual.resposta.equals(alfabeto[ContA])}">
										checked="true"
									</core:if>>
							</span>
			        		<textarea class="field span12" id="textarea" rows="6" 
			        			placeholder="Texto da alternativa ${alfabeto[ContA]}"></textarea>
							<core:set var="ContA" value="${ContA+1}"/>
						</core:forEach>
					</div>
				</div>
			</td>
			<td>
				<div id="alternativasRendered" class="container theme-showcase">
					<core:set var="ContA" value="${0}"/>
					<core:forEach items="${alternativasList}" var="alternativa">
						<core:out value="${alfabeto[ContA]}"/>
						<core:out value="${alternativa.value}"/>
						<core:set var="ContA" value="${ContA+1}"/>
					</core:forEach>
				</div>
				<script>MathJax.Hub.Queue(["Typeset",MathJax.Hub,"alternativasRendered"]);</script>
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