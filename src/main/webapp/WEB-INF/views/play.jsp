<%@include file="/WEB-INF/views/header.jsp"%>
<%@include file="/WEB-INF/views/questions/qheader.jsp"%>

<div id="questao">
</div>

<p>Alternativas:</p>
<div id="alternativas">
</div>

<%@include file="/WEB-INF/views/questions/qfooter.jsp"%>
<%@include file="/WEB-INF/views/footer.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	carregarQuestao();
	carregarAlternativas();
});
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
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
	$.ajax({
		type : "POST",
		url : "/amcequiz/checkgameover",
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