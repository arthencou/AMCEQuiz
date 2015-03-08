<%@include file="/WEB-INF/views/include.jsp"%>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<link href="${pageContext.request.contextPath}/assets/css/editquestoes.css" rel="stylesheet">
<script type="text/x-mathjax-config">
  MathJax.Hub.Config({tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]}});
</script>

<div class="panel">
  <div class="panel-heading">
    <h3 class="panel-title negrito">Questões</h3>
  </div>
  <div class="panel-body">
    <div class="btn-group">
      <core:set var="contQ" value="${0}" />
      <core:forEach items="${questoesList}" var="q">
        <core:set var="contQ" value="${contQ+1}" />
        <div class="btn-group">
          <button id="questao${contQ}" type="button" class="btn btn-default" 
            onclick="salvarQuestao('selecionarQuestao','${contQ}');">
            Questão ${contQ}
          </button>
          <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
            <span class="caret"></span>
          </button>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#" onclick="removerQuestao(${contQ})">Remover</a></li>
          </ul>
        </div>
      </core:forEach>
      <button id="novaQuestao" type="button" class="btn btn-primary"
        onclick="salvarQuestao('novaQuestao','');">
        Nova
      </button>
    </div>
  </div>
</div>

<form:form modelAttribute="questao">

<div class="container-fluid">
  <div id="painelQuestoes" class="panel">
    <div class="panel-heading">
      <h3 class="panel-title negrito">Questão ${questao.numero} - Enunciado</h3>
    </div>
    <div class="panel-body">
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>Enunciado da questão (em HTML e LaTeX):</th>
            <th>Enunciado renderizado:</th>
          </tr>
        </thead>
        <tbody><tr>
          <td>
            <div id="questaoCode">
              <form:textarea id="questaoTextArea" placeholder="Digite aqui o enunciado da questão"
                onchange="questaoTextoMudado();" onkeyup="questaoTextoMudado();"
                path="texto" value="${questao.texto}"/>
            </div>
          </td>
          <td>
            <div id="questaoRendered">
            </div>
          </td>
        </tr></tbody>
      </table>
    </div>
  </div>
</div>

<div class="container-fluid">
  <div class="panel">
    <div class="panel-heading">
      <h3 class="panel-title negrito">Questão ${questao.numero} - Alternativas</h3>
    </div>
    <div class="panel-body">
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>Texto (em HTML e LaTeX):</th>
            <th>Texto renderizado:</th>
          </tr>
        </thead>
        <tbody>
          <core:set var="ContA" value="${0}"/>
          <script>
            function alternativaTextoMudado(letra) {
              var texto = $('#textareaAlternativa-'+letra).val();
              $('#altRender-'+letra).html(texto);
            }
          </script>
          <core:forEach items="${questao.alternativas}" var="alternativa"><tr>
            <td>
              <div class="input-group">
                <span class="input-group-addon">
                  <form:radiobutton path="resposta" value="${alternativa.key}"/>
                  <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                      ${alternativa.key} <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                      <li><a onclick="salvarQuestao('removerAlternativa','(${alternativa.key}');" 
                        href="#" >Remover</a></li>
                    </ul>
                  </div>
                </span>
                <form:textarea path="alternativas['${alternativa.key}']" value="${alternativa.value}"
                  class="field span12 alternativaTextArea" 
                  id="textareaAlternativa-${alternativa.key}" 
                  placeholder="Texto da alternativa '${alternativa.key}'"
                  onchange="alternativaTextoMudado('${alternativa.key}');"
                  onkeyup="alternativaTextoMudado('${alternativa.key}');"/>
              </div>
            </td>
            <td>
              <div id="altRender-${alternativa.key}" class="alternativa-rendered"></div>
            </td></tr>
            <core:set var="ContA" value="${ContA+1}"/>
            <script>alternativaTextoMudado('${alternativa.key}');</script>
          </core:forEach>
        </tbody>
      </table>
      <button type="button" class="btn btn-default" onclick="salvarQuestao('novaAlternativa','');">
        Nova alternativa
      </button>
      
      
    </div>
  </div>
</div>

</form:form>

<button class="btn btn-info pull-right" onclick="saveChanges('gotoPermissoes','');">
  Prosseguir
</button>

<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/mathjax/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
<script>MathJax.Hub.Queue(["Typeset",MathJax.Hub,"questaoRendered"]);</script>
<script>
function selecionarQuestao(qnum) {
  $.ajax({
    type : "POST",
    url : "${pageContext.request.contextPath}/admin/setquestao",
    data : "qnum=" + qnum,
    success : function(response) {
      gotoQuestoes();
    },
    error : function(e) {
      alert('Falha ao selecionar questão'+qnum+'! Error: ' + e);
    }
  });
}
function questaoTextoMudado() {
  //var qtx = $('#questaoTextArea').val();
  $('#questaoRendered').html($('#questaoTextArea').val());
  MathJax.Hub.Queue(["Typeset",MathJax.Hub,"questaoRendered"]);
}
function salvarQuestao(procedimento, args) {
  var questao = $("#questao").serialize();
  $.ajax({
    type : "POST",
    url : "${pageContext.request.contextPath}/admin/savequestao",
    data : questao,
    success : function() {
      window[procedimento](args);
    },
    error : function(e) {
      alert('Falha ao salvar questão atual! Error: ' + e);
    }
  });
}
function novaQuestao() {
  $.ajax({
    type : "POST",
    url : "${pageContext.request.contextPath}/admin/newquestao",
    success : function() {
      gotoQuestoes();
    },
    error : function(e) {
      alert('Falha ao criar nova questão! Error: ' + e);
    }
  });
}
function removerQuestao(qnum) {
  var contQ = ${contQ};
  if (contQ == 1) {
    alert('O jogo precisa ter ao menos uma questão!');
    return;
  }
  $.ajax({
    type : "POST",
    url : "${pageContext.request.contextPath}/admin/deletequestao",
    data : "qnum="+qnum,
    success : function() {
      gotoQuestoes();
    },
    error : function(e) {
      alert('Falha ao remover questão! Error: ' + e);
    }
  });
}
function novaAlternativa() {
  $.ajax({
    type : "POST",
    url : "${pageContext.request.contextPath}/admin/newalternativa",
    success : function() {
      gotoQuestoes();
    },
    error : function(e) {
      alert('Falha ao criar nova alternativa! Error: ' + e);
    }
  });
}
function removerAlternativa(letra) {
  var ContA = ${ContA};
  if (ContA == 1) {
    alert('A questão precisa ter alternativas!');
    return;
  }
  $.ajax({
    type : "POST",
    url : "${pageContext.request.contextPath}/admin/deletealternativa",
    data : "letra="+letra,
    success : function() {
      gotoQuestoes();
    },
    error : function(e) {
      alert('Falha ao remover alternativa '+letra+'! Error: ' + e);
    }
  });
}
function saveChanges(procedimento, args) {
  salvarQuestao(procedimento, args);
}
$(document).ready(function() {
  questaoTextoMudado();
  //$('#questaoRendered').html('TESTE');
});
</script>