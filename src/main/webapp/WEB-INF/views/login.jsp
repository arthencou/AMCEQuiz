<%@include file="/WEB-INF/views/header.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/login.css"
	rel="stylesheet">

<form id="formLogin" class="form-signin" action="j_spring_security_check" method="post">
	<!-- <h2 class="form-signin-heading">AMCE</h2> -->
	<div class="input-group">
		<span class="input-group-addon glyphicon glyphicon-user"></span>
		<input id="j_username" name="j_username" placeholder="Insira seu nome" class="form-control" type="text" value=""/>
		<input id="j_password" name="j_password" placeholder="Insira sua senha" class="form-control"
		<core:choose>
			<core:when test="${badCredentials == true}">type="password" value=""</core:when>
			<core:otherwise>type="hidden" value="123"</core:otherwise>
		</core:choose> />
		
	</div>
	<button id="loginButton" class="btn btn-lg btn-primary btn-block" type="submit" data-placement="bottom">Entrar</button>
</form>

<%@include file="/WEB-INF/views/footer.jsp"%>
