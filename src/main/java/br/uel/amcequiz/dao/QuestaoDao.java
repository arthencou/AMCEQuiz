package br.uel.amcequiz.dao;

import org.springframework.stereotype.Repository;

import br.uel.amcequiz.model.Questao;
import br.uel.amcequiz.util.HibernateUtils;

@Repository
public class QuestaoDao {

	public Questao findByJogoIdENum(Integer jogoId,
			Integer noQuestao) {
		return (Questao) HibernateUtils.getSessionFactory().openSession()
				.createQuery("from Questao where jogo.id = :jogoId and numero = :noQuestao ")
				.setInteger("jogoId", jogoId)
				.setInteger("noQuestao", noQuestao)
				.uniqueResult();
	}

}
