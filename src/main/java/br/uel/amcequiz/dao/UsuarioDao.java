package br.uel.amcequiz.dao;


import org.springframework.stereotype.Repository;

import br.uel.amcequiz.model.Usuario;
import br.uel.amcequiz.util.HibernateUtils;

@Repository
public class UsuarioDao {

	public Usuario findByNome(String nome) {
		return (Usuario) HibernateUtils.getSessionFactory().openSession()
				.createQuery("from Usuario where nome = :nome ")
				.setString("nome", nome)
				.uniqueResult();
	}
	
}