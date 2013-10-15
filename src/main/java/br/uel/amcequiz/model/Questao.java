package br.uel.amcequiz.model;

import java.io.Serializable;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.stereotype.Component;

@Component
@Entity
@TypeDef(name = "json", typeClass = JsonAttribute.class)
@Table(name = "questao")
public class Questao implements Serializable {
	
	private static final long serialVersionUID = 7853767021822179319L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "jogo_id")
	@NotNull
	private Jogo jogo;
	
	@Column(name = "numero")
	@NotNull
	private Integer numero;
	
	@Column(name = "texto")
	private String texto = "";

	@Type(type = "json")
	@Column(name = "alternativas", columnDefinition = "text")
	private TreeMap<String, String> alternativas = new TreeMap<String, String>();
	
	@Column(name = "resposta")
	@NotNull
	private String resposta = "";

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public TreeMap<String, String> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(TreeMap<String, String> alternativas) {
		this.alternativas = alternativas;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	
}
