package br.uel.amcequiz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.uel.amcequiz.model.JogoUsuario;
import br.uel.amcequiz.util.HibernateUtils;

@Repository
public class JogoUsuarioDao {

	public void save(JogoUsuario jogoUsuario) {
		
		System.out.printf("\n\n\tJogo: %s; Usuario: %s;\n", jogoUsuario.getJogo().getNome(), jogoUsuario.getUsuario().getNome());
		
		HibernateUtils.getSessionFactory().getCurrentSession()
		.saveOrUpdate(jogoUsuario);
	}

	public JogoUsuario findJogoUsuario(String usuarioNome, String jogoNome) {
		return (JogoUsuario) HibernateUtils.getSessionFactory().getCurrentSession()
				.createQuery("from JogoUsuario where usuario.nome = :uNome " +
						"and jogo.nome = :jNome ")
				.setString("uNome", usuarioNome)
				.setString("jNome", jogoNome)
				.uniqueResult();
	}

	public void delete(JogoUsuario ju) {
		HibernateUtils.getSessionFactory().getCurrentSession().delete(ju);
	}

	@SuppressWarnings("unchecked")
	public List<JogoUsuario> findJogoUsuariosExUl(String jogoNome, String ulNome) {
		return HibernateUtils.getSessionFactory().getCurrentSession()
				.createQuery("from JogoUsuario where jogo.nome = :jogoNome " +
						"and usuario.nome != :ulNome ")
				.setString("jogoNome", jogoNome)
				.setString("ulNome", ulNome)
				.list();
	}
	
}
