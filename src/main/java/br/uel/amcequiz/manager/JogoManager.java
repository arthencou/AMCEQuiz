package br.uel.amcequiz.manager;

import br.uel.amcequiz.dao.JogoDao;
import br.uel.amcequiz.model.Jogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JogoManager {
	
	private JogoDao jogoDao;
	
	@Autowired
	public void setGameDao(JogoDao jogoDao) {
		this.jogoDao = jogoDao;
	}

	@Transactional
	public List<Jogo> findByUserId(Integer id) {
		return jogoDao.findByUserId(id);
	}

}
