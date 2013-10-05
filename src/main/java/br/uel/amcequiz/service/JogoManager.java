package br.uel.amcequiz.service;

import br.uel.amcequiz.controller.DadosJogada;
import br.uel.amcequiz.dao.JogoDao;
import br.uel.amcequiz.model.Jogo;
import br.uel.amcequiz.model.JogosUsuarios;
import br.uel.amcequiz.model.Questao;

import java.util.List;
import java.util.TreeMap;

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
	public Jogo findById(Integer jogoId) {
		return jogoDao.findById(jogoId);
	}

	@Transactional
	public List<Jogo> findByUserId(Integer usuarioId) {
		return jogoDao.findByUserId(usuarioId);
	}

	@Transactional
	public void saveDadosJogadas(Integer jogoId, Integer usuarioId, 
			TreeMap<Integer, DadosJogada> jogadasDados, Long tempoTotalJogo,
			List<Questao> questoesList) {
		Integer noAcertos = 0;
		DadosJogada dadosJogada = null;
		/*for (Map.Entry<Integer, DadosJogada> entry : jogadasDados.entrySet()) {
			dadosJogada = entry.getValue();
			if (dadosJogada.isCorrect()) {
				noAcertos++;
			}
		}*/
		for (Questao questao : questoesList) {
			Integer noQuestao = questao.getNumero();
			dadosJogada = jogadasDados.get(noQuestao);
			if (dadosJogada == null) {
				dadosJogada = new DadosJogada();
				dadosJogada.setNoQuestao(noQuestao);
				jogadasDados.put(noQuestao, dadosJogada);
			} else if (dadosJogada.isCorrect()) {
				noAcertos++;
			}
		}
		
		if (dadosJogada != null) {
			JogosUsuarios jogoUsuario = 
					jogoDao.getJogoUsuario(jogoId, usuarioId);
			Integer melhorNoAcertosAtual = jogoUsuario.getMelhorNumeroAcertos();
			Long melhorTempoAtual = jogoUsuario.getMelhorTempo();
			jogoUsuario.decrementarPartidas();
			if (noAcertos >= melhorNoAcertosAtual &&
					tempoTotalJogo <= melhorTempoAtual) {
				jogoUsuario.setMelhorNumeroAcertos(noAcertos);
				jogoUsuario.setMelhorTempo(tempoTotalJogo);
				jogoDao.saveJogoUsuario(jogoUsuario);
			}
		}
	}

	@Transactional
	public boolean isPlayableByUser(Integer usuarioId, Integer jogoId) {
		JogosUsuarios jogoUsuario = jogoDao.getJogoUsuario(jogoId, usuarioId);
		if (jogoUsuario != null &&
				jogoUsuario.getQtddPartidasDisponiveis() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
