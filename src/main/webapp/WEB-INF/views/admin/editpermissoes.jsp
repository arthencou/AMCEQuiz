<%@include file="/WEB-INF/views/include.jsp"%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="container-fluid">
			<core:set var="i" value="${0}" />
			<core:forEach items="${jogousuarios}" var="jogousuario">
			<div class="row">
				<div class="col-xs-4">
					<input class="form-control"
						id="usuarioNome-${i}"
						value="${jogousuario.usuario.nome}" 
						type="text"
						onclick="excluirUsuario(${i},'','');" 
						onblur="salvarUsuario(${i},'gotoPermissoes','');"
						pattern="[A-Z][a-z]*" />
				</div>
				<div class="col-xs-2">
					<input class="form-control"
						id="usuarioPartidas-${i}"
						value="${jogousuario.qtddPartidasDisponiveis}" 
						type="number"
						onblur="salvarUsuario(${i},'gotoPermissoes','');" />
				</div>
				<core:if test="${jogousuario.usuario.isAdmin()}">
					<input class="form-control"
						id="usuarioPodeEditar-${i}"
						value="${jogousuario.podeEditar}" 
						type="checkbox"
						onblur="salvarUsuario(${i},'gotoPermissoes','');" />
				</core:if>
				<button
					type="button" class="btn btn-default"
					onclick="excluirUsuario(${i},'gotoPermissoes','');">
						Excluir
				</button>
			</div>
			<core:set var="i" value="${i+1}" />
			</core:forEach>
		</div>
		
		<div class="container">
			<div class="row">
				<div class="list-group">
					<div class="col-xs-4">
						<label for="novoUsuarioNome">Adicionar usu�rio</label>
						<input class="form-control"
							id="novoUsuarioNome"
							type="text" 
							autofocus="autofocus"
							placeholder="Nome"
							onkeypress="onEnterKeyPress(event,'novoUsuario','');"/>
					</div>
					<button class="btn btn-default"
						type="button" 
						onclick="novoUsuario();">
							Criar
					</button>
				</div>
			</div>
		</div>
		
		<button class="btn btn-success pull-right" 
			onclick="saveChanges('persistChanges','');">
				Finalizar
		</button>
	</div>
</div>


<script>
function validarNome(nome) {
	if (nome.trim() === "") {
		return false;
	}
	//if ( !(/[\p{Lu}{1}\p{Ll}*][\s[\p{Lu}{1}\p{Ll}*]]*/.test(nome)) )
		//return false;
	return true;
}
function novoUsuario() {
	var nome = $('#novoUsuarioNome').val();
	if (validarNome(nome)) {
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/admin/newusuario",
			data : "nome="+nome,
			success : function() {
				gotoPermissoes();
			},
			error : function(e) {
				alert('Falha ao criar usuario! Error: ' + e);
			}
		});
	} else {
		alert(nome+': nome inv�lido');
	}
}
function excluirUsuario(index, procedimento, args) {
	var nome = $('#usuarioNome-'+index).val();
	if (validarNome(nome)) {
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/admin/deleteusuario",
			data : "nome="+nome,
			success : function() {
				window[procedimento](args);
			},
			error : function(e) {
				alert('Falha ao excluir usuario! Error: ' + e);
			}
		});
	}
}
function onEnterKeyPress(e, procedimento, args) {
	if (e.keyCode == 13) {
		window[procedimento](args);
	}
}
function salvarUsuario(index, procedimento, args) {
	var nome = $('#usuarioNome-'+index).val();
	if (validarNome(nome)) {
		var partidas = $('#usuarioPartidas-'+index).val();
		var podeEditar = $('#usuarioPodeEditar-'+index).val();
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/admin/saveusuario",
			data : "nome="+nome+"&partidas="+partidas+"&podeEditar="+podeEditar,
			success : function() {
				window[procedimento](args);
			},
			error : function(e) {
				alert('Falha ao salvar usuario! Error: ' + e);
			}
		});
	}
}
function alterarUsuario(index, procedimento, args) {
	
}
function saveChanges(procedimento, args) {
	window[procedimento](args);
}
</script>