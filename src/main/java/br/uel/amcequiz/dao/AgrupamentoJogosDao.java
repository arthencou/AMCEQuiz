package br.uel.amcequiz.dao;

import org.springframework.stereotype.Repository;

import br.uel.amcequiz.model.AgrupamentoJogos;
import br.uel.amcequiz.util.HibernateUtils;

@Repository
public class AgrupamentoJogosDao {

	public void save(AgrupamentoJogos agrupamento) {
		HibernateUtils.getSessionFactory().getCurrentSession()
			.saveOrUpdate(agrupamento);
	}
	
	

}
