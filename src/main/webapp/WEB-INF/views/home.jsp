<%@include file="/WEB-INF/views/header.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/home.css" rel="stylesheet">
<div>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Jogo</th>
				<th>Play</th>
			</tr>
		</thead>
		<tbody>
			<core:forEach items="${gamesList}" var="jogo">
				<tr>
					<td><core:out value="${jogo.nome}" /></td>
					<td><a href="play?jogo=${jogo.id}">Jogar</a></td>
				</tr>
			</core:forEach>
		</tbody>
	</table>
</div>
<%@include file="/WEB-INF/views/footer.jsp"%>
