package br.uel.amcequiz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nome")
	private String nome;

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getLogin() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSenha() {
		// TODO Auto-generated method stub
		return null;
	}

	public Usuario getPerfil() {
		// TODO Auto-generated method stub
		return null;
	}
}
