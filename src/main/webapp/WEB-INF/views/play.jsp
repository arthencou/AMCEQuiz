<%@include file="/WEB-INF/views/include.jsp"%>
<%@include file="/WEB-INF/views/header.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/play.css" rel="stylesheet">

<div class="header">
	<ul class="nav nav-pills pull-right">
		<li>
			<label class="ctempo" for="tempo">Tempo restante:</label>
			<span class="ctempo" id="tempo" class="label label-info"></span>
		</li>
		<li><a href="/amcequiz/gameover">Desistir do jogo</a></li>
		<li class="active"><a href="/amcequiz/gameover?save=true">Salvar jogo</a></li>
	</ul>
</div>

<div class="container-fluid">
	<div class="panel">
        <div class="panel-heading">
          <h3 class="panel-title">Questões</h3>
        </div>
        <div class="panel-body">	
			<div class="btn-group">
				<core:forEach items="${questoesList}" var="questao" >
					<button id="questao${questao.numero}" type="button" 
						class="btn btn-default" 
						onclick="selecionarQuestao(${questao.numero});">
						
						Questão ${questao.numero}
					</button>
				</core:forEach>
			</div>
			<div class="btn-group pull-right">
				<button type="button" class="btn btn-default"
					onclick="selecionarQuestao(questaoAtual+1);">
					
					Próxima
				</button>
			</div>
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
		<div id="alternativas" class="container theme-showcase">
		</div>
	</div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>
<script type="text/javascript">
var questaoAtual = 0;
var totalQuestoes = ${questoesList.size()}
function selecionarQuestao(qnum) {
	if (qnum > totalQuestoes) {
		qnum = 1;
	}
	$.ajax({
		type : "POST",
		url : "/amcequiz/select",
		data : "qnum=" + qnum,
		success : function(response) {
			questaoAtual = qnum;
			$('#questaoTitle').text('Questão '+qnum);
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
			carregarQuestao();
			carregarAlternativas();
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
var tempoMaximo = ${tempoMaximoJogo};
var tempoRestante = tempoMaximo;
function relogioContador() {
	if (tempoRestante <= 0) {
		window.location.replace("/amcequiz/gameover?save=true");
	}
	else {
    	setTimeout("relogioContador()", 1000);
        tempoRestante -= 1000;
	}
    
	var ss, mm, hh;
	ss = parseInt(tempoRestante / 1000, 10);
	mm = parseInt(ss / 60, 10);
	hh = parseInt(mm / 60, 10);
	
	ss = ss - (mm * 60);
	mm = mm - (hh * 60);
    
    $("#tempo").text(hh+':'+((mm<10)?('0'+mm):mm)+':'+((ss<10)?('0'+ss):ss));
    
    if (mm <= 29) {
    	if (mm <= 9) {
	    	$("#tempo").removeClass().addClass('label label-danger');
	    } else {
    		$("#tempo").removeClass().addClass('label label-warning');
	    }
    }
}
$(document).ready(function() {
	$('#questao1').trigger("click");
	relogioContador();
});
</script>