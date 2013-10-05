<%@include file="/WEB-INF/views/include.jsp"%>
<%@include file="/WEB-INF/views/game/qheader.jsp"%>

	<core:if test="${altSel != null}">
		<p>Opção <b>${altSel}</b> selecionada. 
		Para selecionar outra, basta clicar sobre o item.</p>
	</core:if>
	<div class="row">
		<div class="list-group">
			<core:forEach items="${alternativasList}" var="alternativa">
				<a href="#" class="list-group-item
					<core:if test="${altSel == alternativa.key}"> active</core:if>"
					onclick="submitResposta('${alternativa.key}')" >
					
					<core:out value="${alternativa.key}) ${alternativa.value}" />
				</a>
			</core:forEach>
		</div>
	</div>
	
<%@include file="/WEB-INF/views/game/qfooter.jsp"%>
<core:if test="${altSel != null}">
	<script>
	$(document).ready(function() {
		$('#questaoTitle').text('Questao '+questaoAtual+' - Alternativa selecionada: ${altSel}');
	});
	</script>
</core:if>