<%@include file="/WEB-INF/views/include.jsp"%>
<%@include file="/WEB-INF/views/header.jsp"%>

<%@include file="/WEB-INF/views/barra.jsp"%>

<script type="text/x-mathjax-config">
  MathJax.Hub.Config({tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]}});
</script>
<script type="text/javascript">
function timeFormat(time) {
	var ss, mm, hh;
	ss = parseInt(time / 1000, 10);
	mm = parseInt(ss / 60, 10);
	hh = parseInt(mm / 60, 10);
	
	ss = ss - (mm * 60);
	mm = mm - (hh * 60);
	
	return ret = hh+'h '+((mm<10)?('0'+mm):mm)+'m '+((ss<10)?('0'+ss):ss)+'s';
}
</script>

<table class="table">
	<thead>
		<tr>
			<th>Posição</th>
			<th>Jogador</th>
			<th>Total de acertos</th>
			<th>Tempo total</th>
		</tr>
	</thead>
	<tbody>
		<core:set var="pos" value="${0}" />
		<core:forEach items="${playersRank}" var="jogada">
			<core:if test="${!lastPoints.equals(jogada[1]) || !lastTime.equals(jogada[2])}">
				<core:set var="pos" value="${pos+1}" />
			</core:if>
			<tr>
				<td><core:out value="${pos}" /></td>
				<td><core:out value="${jogada[0]}" /></td>
				<td><core:out value="${jogada[1]}" /></td>
				<td>
					<core:choose>
						<core:when test="${jogada[2].equals(172800000)}">
							<core:out value="$\infty$" />
						</core:when>
						<core:otherwise><script>
							document.write(timeFormat(${jogada[2]}));</script>
						</core:otherwise>
					</core:choose>
				</td>
			</tr>
			<core:set var="lastPoints" value="${jogada[1]}" />
			<core:set var="lastTime" value="${jogada[2]}" />
		</core:forEach>
	</tbody>
</table>

<core:set var="pos" value="${0}" />
<core:forEach items="${playersRank}" var="jogada">
</core:forEach>

<%@include file="/WEB-INF/views/footer.jsp"%>
<script type="text/javascript" src="/${pageContext.request.contextPath}/assets/mathjax/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>