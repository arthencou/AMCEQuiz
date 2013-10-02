<%@include file="/WEB-INF/views/header.jsp"%>
<%@include file="/WEB-INF/views/questions/qheader.jsp"%>

<div class="header">
	<ul class="nav nav-pills">
		<li id="questao${questao.numero}">
			<core:forEach items="${questoesList}" var="questao">
				<a href="#"
					onclick="selecionarQuestao(${questao.numero});" >
					<core:out value="Questão ${questao.numero}" ></core:out>
				</a>
			</core:forEach>
		</li>
	</ul>
</div>

<div id="questao" class="container-fluid">
</div>

<p>Alternativas:</p>
<div id="alternativas" class="container-fluid">
</div>

<%@include file="/WEB-INF/views/questions/qfooter.jsp"%>
<%@include file="/WEB-INF/views/footer.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
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
			checkGameOver();
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
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