<%@include file="/WEB-INF/views/header.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/home.css" rel="stylesheet">

<div class="container-fluid">
	<div class="panel">
        <div class="panel-heading">
          <h3 class="panel-title"><strong>Jogos</strong></h3>
        </div>
        <table class="table table-striped">
				<core:forEach items="${gamesList}" var="jogo">
					<tr>
						<td><core:out value="${jogo.nome}" /></td>
						<td><a href="play?jogo=${jogo.id}">Jogar</a></td>
					</tr>
				</core:forEach>
		</table>
      </div>
</div>

<%@include file="/WEB-INF/views/footer.jsp"%>
