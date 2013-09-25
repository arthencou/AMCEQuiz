	<core:forEach items="${alternativasList}" var="alternativa">
		<a href="#" onclick="submitResp(${alternativa.key})">
			<core:out value="${alternativa.value}" />
		</a>
	</core:forEach>
	<script type="text/javascript">
	function submitResposta(alternativa) {
		$.ajax({
			type : "POST",
			url : "amcequiz/answer",
			data : "op=" + alternativa,
			dataType : "JSON",
			success : function(response) {
				if (response.rightAns == 'false') {
					alert("Resposta incorreta");
				}
				carregarQuestao();
				carregarAlternativas();	
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}
	</script>