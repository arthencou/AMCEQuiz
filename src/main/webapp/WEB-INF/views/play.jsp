<%@include file="/WEB-INF/views/header.jsp"%>
<div id="questao">
</div>

<div id="alternativas">
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>
<script>
function carregarQuestao() {
	$.ajax({
		type : "POST",
		url : "amcequiz/question",
		success : function(response) {
			$('questao').html(response);
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
function carregarAlternativas() {
	$.ajax({
		type : "POST",
		url : "amcequiz/alternatives",
		success : function(response) {
			$('alternativas').html(response);
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
$(document).ready(function() {
	carregarQuestao();
	carregarAlternativas();
});
</script>