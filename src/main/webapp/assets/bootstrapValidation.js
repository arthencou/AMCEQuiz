$(document).ready(function() {
	$("input,select,textarea").not("[type=submit]").jqBootstrapValidation();
	$("#inputCep").mask("99999-999");
});