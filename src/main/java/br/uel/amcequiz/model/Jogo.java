package br.uel.amcequiz.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "jogo")
public class Jogo implements Serializable {

	private static final long serialVersionUID = -3145048044596826628L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "tempo_maximo")
	@NotNull
	private Long tempoMaximo;
	
	@ManyToOne
	@JoinColumn(name = "grupo_id")
	private AgrupamentoJogos grupo;
	
	/*@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "jogo_usuario",
			joinColumns = {
					@JoinColumn(name = "usuario_id")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "jogo_id")
			}
	)
	private Set<Usuario> usuarios;*/
	
	public Jogo() {
		tempoMaximo = 9223372036854775807L;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getTempoMaximo() {
		return tempoMaximo;
	}

	public void setTempoMaximo(Long tempoMaximo) {
		this.tempoMaximo = tempoMaximo;
	}

	public AgrupamentoJogos getGrupo() {
		return grupo;
	}

	public void setGrupo(AgrupamentoJogos grupo) {
		this.grupo = grupo;
	}

	/*public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}*/

}
