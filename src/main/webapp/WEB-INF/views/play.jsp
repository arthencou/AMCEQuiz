<%@include file="/WEB-INF/views/header.jsp"%>
<%@include file="/WEB-INF/views/include.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/play.css" rel="stylesheet">

<div class="container-fluid">	
	<ul class="nav nav-pills pull-right">
		<li>
			<label for="tempo">Tempo restante:</label>
			<span id="tempo" class="label label-info"></span>
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
			carregarQuestao();
			carregarAlternativas();
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
var tempoRestante = ${tempoMaximoJogo};
function relogioContador() {
	if (tempoRestante <= 0) {
		window.location.replace("/amcequiz/gameover?save=true");
	}
	else {
    	setTimeout("relogioContador()", 1000);
        tempoRestante -= 1000;
	}
	
    /*var $s = $(".segundo"),
          $m = $(".minuto"),
          $h = $(".hora");*/
    
	var ss, mm, hh;
	ss = parseInt(tempoRestante / 1000, 10);
	mm = parseInt(ss / 60, 10);
	hh = parseInt(mm / 60, 10);
	
	ss = ss - (mm * 60);
	mm = mm - (hh * 60);
                    
    /*$s.val(ss).trigger("change");
    $m.val(mm).trigger("change");
    $h.val(hh).trigger("change");*/
    
    $("#tempo").text(hh+':'+((mm<10)?('0'+mm):mm)+':'+((ss<10)?('0'+ss):ss));
    
    if (mm == 29 && ss == 59) {
    	$("#tempo").removeClass().addClass('label label-warning');
    } else if (mm == 9 && ss == 59) {
    	$("#tempo").removeClass().addClass('label label-danger');
    }
}
$(document).ready(function() {
	$('#questao1').trigger("click");
	relogioContador();
});
</script>