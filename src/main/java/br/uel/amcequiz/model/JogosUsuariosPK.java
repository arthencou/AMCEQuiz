package br.uel.amcequiz.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;


@Component
public class JogosUsuariosPK implements Serializable {

	private static final long serialVersionUID = 4494266779617481312L;
	
	
	protected Usuario usuario;
	protected Jogo jogo;
	

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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof JogosUsuariosPK) {
			JogosUsuariosPK l = (JogosUsuariosPK) o;
			return (usuario.equals(l.getUsuario()) &&
					jogo.equals(l.getJogo()));
		} else {
			return false;
		}
	}

}
