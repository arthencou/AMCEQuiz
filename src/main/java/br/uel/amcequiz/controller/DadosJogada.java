package br.uel.amcequiz.controller;

import java.io.Serializable;

public class DadosJogada implements Serializable {
	
	private static final long serialVersionUID = -3476479517678949900L;
	
	/*public final static Integer NOT_ANSWERED = 0;
	public final static Integer TRUE = 1;
	public final static Integer FALSE = 2;*/
	
	private Integer jogoId;
	private Integer noQuestao;
	/*private Long timeStart;
	private Long timeFinish;*/
	private String opcao;
	private Boolean/*Integer*/ isCorrect;
	
	
	public DadosJogada() {
		jogoId = 0;
		noQuestao = 0;
		/*timeStart = 0L;;
		timeFinish = 0L;*/
		isCorrect = null;//NOT_ANSWERED;
		opcao = "";
	}
	
	public void setJogoId(Integer jogoId) {
		this.jogoId = jogoId;
	}
	
	public Integer getJogoId() {
		return jogoId;
	}
	
	public Integer getNoQuestao() {
		return noQuestao;
	}
	
	public void setNoQuestao(Integer noQuestao) {
		this.noQuestao = noQuestao;
	}
	
	/*public Long getTimeStart() {
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
	}*/
	
	public String getOpcao() {
		return opcao;
	}

	public void setOpcao(String opcao) {
		this.opcao = opcao;
	}
	
	public Boolean/*Integer*/ getIsCorrect() {
		return isCorrect;
	}
	
	public void setIsCorrect(Boolean/*Integer*/ isCorrect) {
		this.isCorrect = isCorrect;
	}

	public boolean isCorrect() {
		//return (isCorrect == TRUE)?true:false;
		if (isCorrect != null) {
			return isCorrect;
		} else {
			return false;
		}
	}
	
}
