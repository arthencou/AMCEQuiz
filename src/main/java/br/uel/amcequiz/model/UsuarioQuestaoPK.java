package br.uel.amcequiz.model;

import java.io.Serializable;

public class UsuarioQuestaoPK implements Serializable {
	
	private static final long serialVersionUID = -4278924977000937544L;
	
	protected Usuario usuario;
	protected Questao questao;
	
	
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof JogoUsuarioPK) {
			UsuarioQuestaoPK l = (UsuarioQuestaoPK) o;
			return (usuario.equals(l.getUsuario()) &&
					questao.equals(l.getQuestao()));
		} else {
			return false;
		}
	}

}
