<%@include file="/WEB-INF/views/include.jsp"%>
<%@include file="/WEB-INF/views/header.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/fundo-degrade.css" rel="stylesheet">

<%@include file="/WEB-INF/views/barra.jsp"%>

<div class="container-fluid">
	<div class="panel">
        <div class="panel-heading">
          <h3 class="panel-title"><strong>Jogos</strong></h3>
        </div>
			<security:authorize access="hasRole('ROLE_ADMIN')">
				<p class="navbar-text">
					<a href="admin/newgame" class="navbar-link">Novo jogo</a>
				</p>
			</security:authorize>
        <table class="table table-striped">
			<core:forEach items="${gamesList}" var="jogo">
				<tr>
					<td>
						<core:if test="${jogo.grupo != null}">${jogo.grupo.nome} -</core:if>
						${jogo.nome}
					</td>
					<security:authorize access="hasRole('ROLE_ADMIN')">
						<td><a href="admin/edit?jogo=${jogo}">Editar</a></td>
					</security:authorize>
					<td><a href="game/set?game=${jogo.id}">Jogar</a></td>
				</tr>
			</core:forEach>
		</table>
      </div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>
