<%@include file="/WEB-INF/views/header.jsp"%>
<div>
	<p>Identificação</p>
</div>
<div>
	<form:form id="formLogin" modelAttribute="usuario">
		<label for="inputNome">Nome:</label>
		<form:input id="inputNome" path="nome" value="${usuario.nome}"/>
		<button type="submit" data-placement="bottom">Ok</button>
	</form:form>
</div>
<%@include file="/WEB-INF/views/footer.jsp"%>
