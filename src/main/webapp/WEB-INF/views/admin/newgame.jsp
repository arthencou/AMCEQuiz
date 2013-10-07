<%@include file="/WEB-INF/views/include.jsp"%>
<%@include file="/WEB-INF/views/header.jsp"%>

<ul class="nav nav-tabs">
  <li id="navJogo" class="active"><a href="#" onclick="gotoJogo();">Jogo</a></li>
  <li id="navQuestoes"><a href="#" onclick="gotoQuestoes();">Quest�es</a></li>
  <li id="navPermissoes"><a href="#" onclick="gotoPermissoes();">Permiss�es</a></li>
</ul>

<div id="conteudo">
	Teste
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>
<script>
var navAtual = 'navJogo'; 
function gotoJogo() {
	$.ajax({
		type : "GET",
		url : "/amcequiz/admin/editjogo",
		success : function(response) {
			$('#'+navAtual).removeClass();
			navAtual = 'navJogo';
			$('#navJogo').removeClass().addClass('active');
			$("#conteudo").html(response);
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
function gotoQuestoes() {
	$.ajax({
		type : "GET",
		url : "/amcequiz/admin/editquestoes",
		success : function(response) {
			$('#'+navAtual).removeClass();
			navAtual = 'navQuestoes';
			$('#navQuestoes').removeClass().addClass('active');
			$("#conteudo").html(response);
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
function gotoPermissoes() {
	$.ajax({
		type : "GET",
		url : "/amcequiz/admin/editpermissoes",
		success : function(response) {
			$('#'+navAtual).removeClass();
			navAtual = 'navPermissoes';
			$('#navPermissoes').removeClass().addClass('active');
			$("#conteudo").html(response);
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
$(document).ready(function() {
	gotoJogo();
});
</script>