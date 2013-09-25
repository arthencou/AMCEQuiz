package br.uel.amcequiz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.uel.amcequiz.model.Jogo;
import br.uel.amcequiz.util.HibernateUtils;

@Repository
public class JogoDao {

	@SuppressWarnings("unchecked")
	public List<Jogo> findByUserId(Integer usuarioId) {
		return HibernateUtils.getCurrentSession()
				.createQuery("from (select from Jogo j join j.usuarios u where u.id in (:id)")
				.setInteger("id", usuarioId)
				.list();
	}

}
