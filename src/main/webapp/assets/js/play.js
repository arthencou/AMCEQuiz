var questaoAtual = 0;
var totalQuestoes = ${questoesList.size()};
function selecionarQuestao(qnum) {
	if (qnum > totalQuestoes) {
		qnum = 1;
	} else if (qnum < 1) {
		qnum = totalQuestoes;
	}
	$.ajax({
		type : "POST",
		url : "/amcequiz/game/selectQuestion",
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
function carregarQuestao() {
	$.ajax({
		type : "POST",
		url : "/amcequiz/game/question",
		success : function(response) {
			$('#questao').html(response);
			MathJax.Hub.Queue(["Typeset",MathJax.Hub,"questao"]);
		},
		error : function(e) {
			alert('Falha ao carregar texto da questão! Error: ' + e);
		}
	});
}
function carregarAlternativas() {
	$.ajax({
		type : "POST",
		url : "/amcequiz/game/alternatives",
		success : function(response) {
			$('#alternativas').html(response);
			MathJax.Hub.Queue(["Typeset",MathJax.Hub,"alternativas"]);
		},
		error : function(e) {
			alert('Falha ao carregar alternativas! Error: ' + e);
		}
	});
}
function submitResposta(alternativa) {
	$.ajax({
		type : "POST",
		url : "/amcequiz/game/answerQuestion",
		data : "op=" + alternativa,
		success : function(response) {
			$('#proxima').trigger("click");
			carregarQuestao();
			carregarAlternativas();
		},
		error : function(e) {
			alert('Falha ao submeter resposta! Error: ' + e);
		}
	});
}
var tempoMaximo = ${tempoMaximoJogo};
var tempoRestante = tempoMaximo;
function relogioContador() {
	if (tempoRestante <= 0) {
		window.location.replace("/amcequiz/game/gameover?save=true");
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
    
    if (tempoRestante < 0.1*tempoMaximo) {
    	$("#tempo").removeClass().addClass('label label-danger');
    } else if (tempoRestante < 0.2*tempoMaximo) {
    	$("#tempo").removeClass().addClass('label label-warning');
    } else {
    	$("#tempo").removeClass().addClass('label label-info');
    }
}
$(document).ready(function() {
	$('#questao1').trigger("click");
	relogioContador();
});