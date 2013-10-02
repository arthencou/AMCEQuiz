<%@include file="/WEB-INF/views/include.jsp"%>
<%@include file="/WEB-INF/views/questions/qheader.jsp"%>

	<core:if test="${altSel != null}">
		<p>Você já respondeu essa questão</p>
	</core:if>
	<div class="row">
		<div class="col-sm-4">
			<ul class="list-group">
				<core:forEach items="${alternativasList}" var="alternativa">
					<li class="list-group-item
						<core:if test="${altSel != null}">active</core:if>" >
						
						<a href="#" id="alternativa${alternativa.key}"
							<core:if test="${altSel == null}">
								onclick="submitResposta('${alternativa.key}')"
							</core:if> >
							
							<core:out value="${alternativa.key}) ${alternativa.value}" />
						</a>
					</li>
				</core:forEach>
			</ul>
		</div>
	</div>
	
<%@include file="/WEB-INF/views/questions/qfooter.jsp"%>