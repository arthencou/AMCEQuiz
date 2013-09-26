<%@include file="/WEB-INF/views/header.jsp"%>
<div>
	<table>
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
