<%@include file="/WEB-INF/views/include.jsp"%>
<div class="header">
	<div class="container-fluid">
		<ul class="nav nav-pills pull-right">
			<li><span class="nome-usuario">
				<span class="negrito">${sessionScope.usuario.nome}</span>
				<security:authorize access="hasRole('ROLE_ADMIN')">
					(Monitor/Professor)
				</security:authorize>
				<security:authorize access="hasRole('ROLE_ALUNO')">
					(Aluno)
				</security:authorize>
			</span></li>
			<li><a id="sair" href="${pageContext.request.contextPath}/logout">Sair</a></li>
			<li class="active"><a id="home" href="${pageContext.request.contextPath}/home">Jogos</a></li>
		</ul>
	</div>
</div>
<script>
$('#sair').tooltip({
	animation:'true',
	placement:'top',
	title:'Encerra a sessão com o Quiz, iniciada com sua entrada, e permite que outra pessoa entre para jogar.',
	trigger:'hover focus'
});
</script>
