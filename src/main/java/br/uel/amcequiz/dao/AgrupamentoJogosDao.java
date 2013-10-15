package br.uel.amcequiz.dao;

import java.util.List;

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

	public AgrupamentoJogos findById(Integer grupoId) {
		return (AgrupamentoJogos) 
				HibernateUtils.getSessionFactory().getCurrentSession()
				.createQuery("from AgrupamentoJogos where id= :id ")
				.setInteger("id", grupoId)
				.uniqueResult();
	}

	public AgrupamentoJogos findByNome(String nome) {
		return (AgrupamentoJogos) HibernateUtils.getSessionFactory().getCurrentSession()
				.createQuery("from AgrupamentoJogos where nome = :nome ")
				.setString("nome", nome)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> playersRankByGrupo(Integer grupoId) {
		return HibernateUtils.getSessionFactory().getCurrentSession()
				.createQuery(
						"select usuario.nome, sum(melhorNumeroAcertos) as mna, " +
							"sum(melhorTempo) as mt " +
						"from JogoUsuario where jogo.grupo.id = :grupoId " +
						"group by usuario.nome " +
						"order by mna desc, mt asc")
				.setInteger("grupoId", grupoId)
				.list();
	}
	
}
