<%@include file="/WEB-INF/views/include.jsp"%>
<%@include file="/WEB-INF/views/header.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/play.css" rel="stylesheet">


<core:choose>
	<core:when test="${saveSuccessful == true}">
		<script>gotoQuestoes();</script>
	</core:when>
	<core:otherwise>
		<form:form class="form-horizontal" method="post" modelAttribute="agrupamento">
			<label for="inputNomeGrupo">Nome do grupo</label>
			<form:input id="inputNomeGrupo" path="nome" type="text" required="false"></form:input>
		</form:form>
		
		<form:form class="form-horizontal" method="post" modelAttribute="jogo">
			<label for="inputNomeJogo">Nome do jogo</label>
			<form:input id="inputNomeJogo" path="nome" type="text" required="true"></form:input>
			<label for="inputTempoJogo">Tempo máximo da partida</label>
			<form:input id="inputTempoJogo" path="nome" type="text" required="false"></form:input>
		</form:form>
		
		<button class="btn btn-default" onclick="submitJogo();">Prosseguir</button>
	</core:otherwise>
</core:choose>

<%@include file="/WEB-INF/views/footer.jsp"%>