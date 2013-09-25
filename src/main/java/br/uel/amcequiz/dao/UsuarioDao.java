package br.uel.amcequiz.dao;


import org.springframework.stereotype.Repository;

import br.uel.amcequiz.model.Usuario;
import br.uel.amcequiz.util.HibernateUtils;

@Repository
public class UsuarioDao {

	public Usuario findByLogin(String login) {
		return (Usuario) HibernateUtils.getCurrentSession()
				.createQuery("from Usuario where login = :login)")
				.setString("login", login)
				.uniqueResult();
	}
	
}