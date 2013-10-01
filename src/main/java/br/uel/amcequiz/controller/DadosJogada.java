package br.uel.amcequiz.controller;

public class DadosJogada {
	
	private Integer noQuestao;
	private Long timeStart;
	private Long timeFinish;
	private Boolean isCorrect;
	
	
	public Integer getNoQuestao() {
		return noQuestao;
	}
	public void setNoQuestao(Integer noQuestao) {
		this.noQuestao = noQuestao;
	}
	public Long getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Long timeStart) {
		this.timeStart = timeStart;
	}
	public Long getTimeFinish() {
		return timeFinish;
	}
	public void setTimeFinish(Long timeFinish) {
		this.timeFinish = timeFinish;
	}
	public Boolean getIsCorrect() {
		return isCorrect;
	}
	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
}
