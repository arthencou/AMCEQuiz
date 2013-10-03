<%@include file="/WEB-INF/views/header.jsp"%>
<%@include file="/WEB-INF/views/include.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/play.css" rel="stylesheet">

<div class="container-fluid">
	<ul class="nav nav-pills pull-right">
		<li><a href="/amcequiz/gameover">Desistir do jogo</a></li>
		<li class="active"><a href="/amcequiz/gameover?save=true">Salvar jogo</a></li>
	</ul>
</div>

<div class="container-fluid">
	<div class="panel">
        <div class="panel-heading">
          <h3 class="panel-title">Questões</h3>
        </div>
        <div class="bs-example panel-body">	
			<div class="btn-group">
				<core:forEach items="${questoesList}" var="questao" >
					<button id="questao${questao.numero}" type="button" 
						class="btn btn-default" 
						onclick="selecionarQuestao(${questao.numero});">
						
						Questão ${questao.numero}
					</button>
				</core:forEach>
			</div>
		</div>  
	</div>
</div>

<div class="container-fluid">
	<div class="panel">
        <div class="panel-heading">
          <h3 class="panel-title">Questão</h3>
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
		<div id="alternativas" class="container theme-showcase">
		</div>
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$('#questao1').trigger("click");
});
function selecionarQuestao(qnum) {
	$.ajax({
		type : "POST",
		url : "/amcequiz/select",
		data : "qnum=" + qnum,
		success : function(response) {
			carregarQuestao();
			carregarAlternativas();
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
function carregarQuestao() {
	$.ajax({
		type : "POST",
		url : "/amcequiz/question",
		success : function(response) {
			$('#questao').html(response);
			MathJax.Hub.Queue(["Typeset",MathJax.Hub,"questao"]);
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
function carregarAlternativas() {
	$.ajax({
		type : "POST",
		url : "/amcequiz/alternatives",
		success : function(response) {
			$('#alternativas').html(response);
			MathJax.Hub.Queue(["Typeset",MathJax.Hub,"alternativas"]);
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
function submitResposta(alternativa) {
	$.ajax({
		type : "POST",
		url : "/amcequiz/answer",
		data : "op=" + alternativa,
		success : function(response) {
			//checkGameOver();
			carregarQuestao();
			carregarAlternativas();
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
//Deprecated 
function checkGameOver() {
	$.ajax({
		type : "POST",
		url : "/amcequiz/checkgameover",
		dataType : "JSON",
		success : function(response) {
			if (response.isGameOver = 'true') {
				window.location.replace("/amcequiz/gameover");
			} else {
				carregarQuestao();
				carregarAlternativas();
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
</script>