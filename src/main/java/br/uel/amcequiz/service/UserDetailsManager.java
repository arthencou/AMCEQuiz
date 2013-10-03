package br.uel.amcequiz.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uel.amcequiz.dao.UsuarioDao;
import br.uel.amcequiz.model.Usuario;
import br.uel.amcequiz.util.CustomUserDetails;

@Service
public class UserDetailsManager implements UserDetailsService {

	private UsuarioDao usuarioDao;

	@Autowired
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByNome(login);
		if (usuario == null) {
			usuario = usuarioDao.findByLogin(login);
		}

		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		return new CustomUserDetails(usuario.getLogin(), usuario.getSenha(), 
				enabled, accountNonExpired, credentialsNonExpired, 
				accountNonLocked, getAuthorities(new Long(0)));
	}

	public Collection<GrantedAuthority> getAuthorities(Long role) {
		List<GrantedAuthority> authorityList = getGrantedAuthorities(getRoles(role));

		return authorityList;
	}

	public List<String> getRoles(Long role) {
		List<String> roles = new ArrayList<String>();

		if (role.intValue() == 0) {
			roles.add("ROLE_ALUNO");
		} else if (role.intValue() == 1) {
			roles.add("ROLE_ADMIN");
		}

		return roles;
	}

	public static List<GrantedAuthority> getGrantedAuthorities(
			List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return authorities;
	}
}