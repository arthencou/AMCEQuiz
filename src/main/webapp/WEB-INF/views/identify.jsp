<%@include file="/WEB-INF/views/header.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/identify.css"
	rel="stylesheet">

<form:form id="formLogin" modelAttribute="usuario" class="form-signin">
	<!-- <h2 class="form-signin-heading">AMCE</h2> -->
      <div class="input-group">
        
  		<span class="input-group-addon glyphicon glyphicon-user"></span>
  		<form:input type="text" class="form-control" placeholder="Insira seu nome" autofocus="" id="inputNome" path="nome" value="${usuario.nome}"/>
	  </div>        
      <button id="loginButton" class="btn btn-lg btn-primary btn-block" type="submit" data-placement="bottom">Jogar</button>
</form:form>

<%@include file="/WEB-INF/views/footer.jsp"%>