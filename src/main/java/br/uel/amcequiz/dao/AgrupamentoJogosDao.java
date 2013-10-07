package br.uel.amcequiz.dao;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import br.uel.amcequiz.model.AgrupamentoJogos;
import br.uel.amcequiz.util.HibernateUtils;

@Repository
public class AgrupamentoJogosDao {

	public void save(AgrupamentoJogos agrupamento) 
			throws ConstraintViolationException {
		HibernateUtils.getSessionFactory().getCurrentSession()
			.saveOrUpdate(agrupamento);
	}

	public AgrupamentoJogos findByNome(String nome) {
		return (AgrupamentoJogos) HibernateUtils.getSessionFactory().getCurrentSession()
				.createQuery("from AgrupamentoJogos where nome = :nome ")
				.setString("nome", nome)
				.uniqueResult();
	}
	
}
