package br.uel.amcequiz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
@Entity
@IdClass(UsuarioQuestaoPK.class)
@Table(name = "usuario_questao")
public class UsuarioQuestao {
	
	@Id
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@NotNull
	private Usuario usuario;

	@Id
	@ManyToOne
	@JoinColumn(name = "questao_id")
	@NotNull
	private Questao questao;
	
	@Column(name = "is_correta_jogada_ranking")
	private Boolean isCorretaJogadaRanking;
	
	public UsuarioQuestao() {
		
	}

	public UsuarioQuestao(Usuario usuario, Questao questao, Boolean isCorreta) {
		this.usuario = usuario;
		this.questao = questao;
		this.isCorretaJogadaRanking = isCorreta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public Boolean getIsCorretaJogadaRanking() {
		return isCorretaJogadaRanking;
	}

	public void setIsCorretaJogadaRanking(Boolean isCorretaJogadaRanking) {
		this.isCorretaJogadaRanking = isCorretaJogadaRanking;
	}
	
	public Boolean isQuestaoCorreta() {
		return isCorretaJogadaRanking;
	}
	
}
