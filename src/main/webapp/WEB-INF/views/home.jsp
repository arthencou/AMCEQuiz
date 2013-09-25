<%@include file="/WEB-INF/views/header.jsp"%>
<div>
	<p>Menu Lateral</p>
	<a href="play">Jogar</a>
	<a href="edit">Criar/editar jogo</a>
</div>

<div>
	<table>
		<thead>
			<tr>
				<th>Jogo</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<core:forEach  items="gamesList" var="game">
				<tr>
					<td>${game.nome}</td>
					<td><a href="play?jogo=${game.id}"></a></td>
				</tr>
			</core:forEach>
		</tbody>
	</table>
</div>
<%@include file="/WEB-INF/views/footer.jsp"%>
