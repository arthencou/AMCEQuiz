package br.uel.amcequiz.service;

import br.uel.amcequiz.controller.DadosJogada;
import br.uel.amcequiz.dao.AgrupamentoJogosDao;
import br.uel.amcequiz.dao.JogoDao;
import br.uel.amcequiz.dao.QuestaoDao;
import br.uel.amcequiz.model.AgrupamentoJogos;
import br.uel.amcequiz.model.Jogo;
import br.uel.amcequiz.model.JogoUsuario;
import br.uel.amcequiz.model.Questao;
import br.uel.amcequiz.model.Usuario;
import br.uel.amcequiz.model.UsuarioQuestao;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JogoManager {
	
	private JogoDao jogoDao;
	private QuestaoDao questaoDao;
	private AgrupamentoJogosDao agrupamentoJogosDao;
	
	@Autowired
	public void setGameDao(JogoDao jogoDao) {
		this.jogoDao = jogoDao;
	}
	
	@Autowired
	public void setQuestaoDao(QuestaoDao questaoDao) {
		this.questaoDao = questaoDao;
	}
	
	@Autowired
	public void setAgrupamentoJogos (AgrupamentoJogosDao agrupamentoJogosDao) {
		this.agrupamentoJogosDao = agrupamentoJogosDao;
	}

	@Transactional
	public Jogo findById(Integer jogoId) {
		return jogoDao.findById(jogoId);
	}

	@Transactional
	public List<Jogo> findByUserId(Integer usuarioId) {
		return jogoDao.findByUserId(usuarioId);
	}

	/**
	 * Avalia os dados do jogo recém terminado para decidir
	 * se o posicionamento do usuário no ranking deve ou não considerá-los.
	 * @param jogoId: id do jogo na camada de persistência.
	 * @param usuarioId: id do usuário na camada de persistência.
	 * @param jogadasDados: os dados da jogada.
	 * @param tempoTotalJogo: tempo total de jogo.
	 * @param questoesList: lista de questões do jogo.
	 * @return Verdadeiro, caso a jogada entre no ranking, ou Falso, caso 
	 * contrário. 
	 */
	@Transactional
	public boolean saveDadosJogadas(Integer jogoId, Integer usuarioId, 
			TreeMap<Integer, DadosJogada> jogadasDados, Long tempoTotalJogo,
			List<Questao> questoesList) {
		Integer noAcertos = 0;
		DadosJogada dadosJogada = null;
		
		/*Contando o total de acertos na última jogada*/
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
			JogoUsuario jogoUsuario = 
					jogoDao.getJogoUsuario(jogoId, usuarioId);
			Integer melhorNoAcertosAtual = 
					jogoUsuario.getMelhorNumeroAcertos();
			Long melhorTempoAtual = jogoUsuario.getMelhorTempo();
			jogoUsuario.decrementarPartidas();
			
			/*Condição para atualizar seus dados no ranking*/
			if (noAcertos >= melhorNoAcertosAtual) {
				if (!(noAcertos == melhorNoAcertosAtual 
						&& tempoTotalJogo > melhorTempoAtual)) {
					/*Atualizando os dados de jogada.*/
					jogoUsuario.setMelhorNumeroAcertos(noAcertos);
					jogoUsuario.setMelhorTempo(tempoTotalJogo);
					jogoDao.saveJogoUsuario(jogoUsuario);
					
					/*Armazenando os acertos por questão (do jogador).*/
					for (Map.Entry<Integer, DadosJogada> entry
							: jogadasDados.entrySet()) {
						DadosJogada dj = entry.getValue();
						Questao questao = questoesList.get(dj.getNoQuestao()-1);
						Boolean isCorrect = dj.getIsCorrect();
						UsuarioQuestao usuarioQuestao = 
								new UsuarioQuestao(new Usuario(usuarioId), 
										questao, isCorrect);
						questaoDao.saveUsuarioQuestao(usuarioQuestao);
					}
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * Informa se um determinado usuário pode jogar um determinado jogo.
	 * @param usuarioId: o id do usuário na camada de persistência.
	 * @param jogoId: o id do jogo na camada de persistência.
	 * @return Verdadeiro ou Falso.
	 */
	@Transactional
	public boolean isPlayableByUser(Integer usuarioId, Integer jogoId) {
		JogoUsuario jogoUsuario = jogoDao.getJogoUsuario(jogoId, usuarioId);
		if (jogoUsuario != null &&
				jogoUsuario.getQtddPartidasDisponiveis() != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public boolean saveJogo(AgrupamentoJogos agrupamento, Jogo jogo) {
		try {
			agrupamentoJogosDao.save(agrupamento);
			jogo.setGrupo(agrupamento);
			jogoDao.save(jogo);
			
			return true;
		} catch (ConstraintViolationException e) {
			return false;
		}
	}

}
