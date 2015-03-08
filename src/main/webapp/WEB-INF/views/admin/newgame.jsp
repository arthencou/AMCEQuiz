<%@include file="/WEB-INF/views/include.jsp"%>
<%@include file="/WEB-INF/views/header.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/editjogo.css" rel="stylesheet">

<ul class="nav nav-tabs">
	<li id="navJogo" class="active"><a href="#" onclick="saveChanges('gotoJogo','');">Jogo</a></li>
	<li id="navQuestoes"><a href="#" onclick="saveChanges('gotoQuestoes','');">Questões</a></li>
	<li id="navPermissoes"><a href="#" onclick="saveChanges('gotoPermissoes','');">Permissões</a></li>
	<li id="navDescartar" class="pull-right"><a href="#" onclick="discardChanges();">Descartar alterações</a></li>
	<li id="navSalvar" class="pull-right"><a href="#" onclick="saveChanges('persistChanges','');">Salvar</a></li>
</ul>

<div id="conteudo">
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>
<script>
var navAtual = 'navJogo'; 
function gotoJogo() {
	$.ajax({
		type : "GET",
		url : "${pageContext.request.contextPath}/admin/editjogo",
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
		url : "${pageContext.request.contextPath}/admin/editquestoes",
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
		url : "${pageContext.request.contextPath}/admin/editpermissoes",
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
function discardChanges() {
	$.ajax({
		type : "POST",
		url : "${pageContext.request.contextPath}/admin/flushgamechanges",
		success : function(response) {
			window.location.replace("${pageContext.request.contextPath}/home");
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
function persistChanges() {
	$.ajax({
		type : "POST",
		url : "${pageContext.request.contextPath}/admin/persistgamechanges",
		dataType : "json",
		success : function(response) {
			if (response.successful == 'true') {
				window.location.replace("${pageContext.request.contextPath}/home");
			} else {
				alert('Não foi possível salvar todos os dados');
			}
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