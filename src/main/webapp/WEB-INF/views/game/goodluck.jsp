<%@include file="/WEB-INF/views/header.jsp"%>
<link href="${pageContext.request.contextPath}/assets/css/fundo-degrade.css" rel="stylesheet">

<table>
	<tbody>
		<tr>
			<td>
				<img src="/amcequiz/assets/images/mascote.png" /></td>
			<td>
				<h1 class="text-center">Boa sorte!</h1>
			</td>
		</tr>
	</tbody>
</table>

<%@include file="/WEB-INF/views/footer.jsp"%>
<script>
$(document).ready(function() {
	setTimeout("redirectPlay()", 3500);
});
function redirectPlay() {
	window.location.replace("/amcequiz/game/play");
}
</script>