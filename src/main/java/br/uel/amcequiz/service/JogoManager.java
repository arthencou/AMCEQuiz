package br.uel.amcequiz.service;

import br.uel.amcequiz.controller.DadosJogada;
import br.uel.amcequiz.dao.JogoDao;
import br.uel.amcequiz.model.Jogo;
import br.uel.amcequiz.model.JogosUsuarios;

import java.util.List;
import java.util.Map;
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
	public List<Jogo> findByUserId(Integer id) {
		return jogoDao.findByUserId(id);
	}

	@Transactional
	public void saveDadosJogadas(Integer jogoId, Integer usuarioId, 
			TreeMap<Integer, DadosJogada> jogadasDados) {
		Integer noAcertos = 0;
		Long tempoJogada = new Long(0);
		
		DadosJogada dadosJogada = null;
		for (Map.Entry<Integer, DadosJogada> entry : jogadasDados.entrySet()) {
			dadosJogada = entry.getValue();
			if (dadosJogada.getIsCorrect()) {
				noAcertos++;
			}
			tempoJogada += (dadosJogada.getTimeFinish() 
					- dadosJogada.getTimeStart());
		}
		
		if (dadosJogada != null) {
			JogosUsuarios jogoUsuario = 
					jogoDao.getJogoUsuario(jogoId, usuarioId);
			Integer melhorNoAcertosAtual = jogoUsuario.getMelhorNumeroAcertos();
			Long melhorTempoAtual = jogoUsuario.getMelhorTempo();
			if (noAcertos >= melhorNoAcertosAtual &&
					tempoJogada <= melhorTempoAtual) {
				if (noAcertos > melhorNoAcertosAtual) {
					jogoUsuario.setMelhorNumeroAcertos(noAcertos);
				}
				if (tempoJogada < melhorTempoAtual) {
					jogoUsuario.setMelhorTempo(tempoJogada);
				}
				jogoDao.saveJogoUsuario(jogoUsuario);
			}
		}
	}

}
