package br.uel.amcequiz.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uel.amcequiz.dao.QuestaoDao;
import br.uel.amcequiz.model.Questao;

@Service
public class QuestaoManager {
	
	private QuestaoDao questaoDao;
	
	@Autowired
	public void setQuestaoDao(QuestaoDao questaoDao) {
		this.questaoDao = questaoDao;
	}

	@Transactional
	public Questao findByJogoIdENum(Integer jogoId,
			Integer noQuestao) {
		return questaoDao.findByJogoIdENum(jogoId, noQuestao);
	}

	public List<Questao> findAllByJogoId(Integer jogoId) {
		return questaoDao.findAllByJogoId(jogoId);
	}

}
