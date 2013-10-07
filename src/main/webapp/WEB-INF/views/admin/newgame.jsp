<%@include file="/WEB-INF/views/include.jsp"%>
<%@include file="/WEB-INF/views/header.jsp"%>

<ul class="nav nav-tabs">
  <li id="navJogo" class="active"><a href="#" onclick="gotoJogo();">Jogo</a></li>
  <li id="navQuestoes"><a href="#" onclick="gotoQuestoes();">Questões</a></li>
  <li id="navPermissoes"><a href="#" onclick="gotoPermissoes();">Permissões</a></li>
</ul>

<div id="conteudo">
	Teste
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>
<script>
var navAtual = 'navJogo'; 
function gotoJogo() {
	var agrupamento = $('#agrupamento').serialize();
	var jogo = $('#jogo').serialize();
	$.ajax({
		type : "POST",
		url : "/amcequiz/admin/editjogo",
		data : agrupamento + jogo,
		success : function(response) {
			$('#'+navAtual).removeClass();
			navAtual = 'navJogo';
			$('#'+navAtual).removeClass().addClass('active');
			$("#conteudo").html(response);
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
function gotoQuestoes() {
	$('#'+navAtual).removeClass();
	navAtual = 'navQuestoes';
	$('#'+navAtual).removeClass().addClass('active');
	$("#conteudo").html('Questoes')
}
function gotoPermissoes() {
	$('#'+navAtual).removeClass();
	navAtual = 'navPermissoes';
	$('#'+navAtual).removeClass().addClass('active');
	$("#conteudo").html('Permissoes')
}
</script>