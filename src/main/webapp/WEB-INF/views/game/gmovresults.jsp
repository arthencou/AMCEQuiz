<%@include file="/WEB-INF/views/include.jsp"%>
				<core:set var="totalAcertos" value="${0}" />
				<core:forEach items="${jogadasDados}" var="jogada">
					<ul>
						<li>Questão ${jogada.key}:
							<core:choose>
								<core:when test="${jogada.value.isCorrect() == true}">
									<span class="azul">correta</span>
									<core:set var="totalAcertos" value="${totalAcertos+1}" />
								</core:when>
								<core:when test="${jogada.value.isCorrect == null}">
									<span class="negrito">não respondida</span>
								</core:when>
								<core:otherwise>
									<span class="vermelho">incorreta</span>
								</core:otherwise>
							</core:choose>
						</li>
					</ul>
				</core:forEach>