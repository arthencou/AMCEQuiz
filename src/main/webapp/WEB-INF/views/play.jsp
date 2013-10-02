<%@include file="/WEB-INF/views/header.jsp"%>
<%@include file="/WEB-INF/views/include.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/play.css" rel="stylesheet">

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
	selecionarQuestao(1);
	carregarQuestao();
	carregarAlternativas();
});
function selecionarQuestao(qnum) {
	$.ajax({
		type : "POST",
		url : "/amcequiz/select",
		data : "qnum=" + qnum,
		success : function(response) {
			carregarQuestao();
			carregarAlternativas();
			$('#questao'+qnum).attr('class', 'active');
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
		dataType : "JSON",
		success : function(response) {
			if (response.rightAns == 'true') {
				alert('Resposta correta');
			} else {
				alert('Resposta incorreta');
			}
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