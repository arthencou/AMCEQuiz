package br.uel.amcequiz.util;

import org.springframework.security.core.context.SecurityContextHolder;

import br.uel.amcequiz.model.Usuario;

public class SpringSecurityUtils {

	public static Usuario usuarioLogado() {
		return ((CustomUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUsuario();
	}
	
}
