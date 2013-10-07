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
@IdClass(JogoUsuarioPK.class)
@Table(name = "jogo_usuario")
public class JogoUsuario {

	@Id
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@NotNull
	private Usuario usuario;

	@Id
	@ManyToOne
	@JoinColumn(name = "jogo_id")
	@NotNull
	private Jogo jogo;
	
	@Column(name = "melhor_tempo")
	private Long melhorTempo;
	
	@Column(name = "melhor_numero_acertos")
	private Integer melhorNumeroAcertos;
	
	@Column(name = "qtdd_partidas_disponiveis")
	@NotNull
	private Integer qtddPartidasDisponiveis;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public Long getMelhorTempo() {
		return melhorTempo;
	}

	public void setMelhorTempo(Long melhorTempo) {
		this.melhorTempo = melhorTempo;
	}

	public Integer getMelhorNumeroAcertos() {
		return melhorNumeroAcertos;
	}

	public void setMelhorNumeroAcertos(Integer melhorNumeroAcertos) {
		this.melhorNumeroAcertos = melhorNumeroAcertos;
	}

	public Integer getQtddPartidasDisponiveis() {
		return qtddPartidasDisponiveis;
	}

	public void setQtddPartidasDisponiveis(Integer qtddPartidasDisponiveis) {
		this.qtddPartidasDisponiveis = qtddPartidasDisponiveis;
	}

	public void decrementarPartidas() {
		if (qtddPartidasDisponiveis > 0) {
			qtddPartidasDisponiveis--;
		}
	}
	
}
