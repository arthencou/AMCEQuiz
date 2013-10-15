package br.uel.amcequiz.service;

import br.uel.amcequiz.controller.DadosJogada;
import br.uel.amcequiz.dao.AgrupamentoJogosDao;
import br.uel.amcequiz.dao.JogoDao;
import br.uel.amcequiz.dao.JogoUsuarioDao;
import br.uel.amcequiz.dao.QuestaoDao;
import br.uel.amcequiz.model.AgrupamentoJogos;
import br.uel.amcequiz.model.Jogo;
import br.uel.amcequiz.model.JogoUsuario;
import br.uel.amcequiz.model.Questao;
import br.uel.amcequiz.model.Usuario;
import br.uel.amcequiz.model.UsuarioQuestao;

import java.util.ArrayList;
import java.util.Collection;
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
	private JogoUsuarioDao jogoUsuarioDao;
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
	public void setJogoUsuarioDao(JogoUsuarioDao jogoUsuarioDao) {
		this.jogoUsuarioDao = jogoUsuarioDao;
	}

	@Autowired
	public void setAgrupamentoJogos (AgrupamentoJogosDao agrupamentoJogosDao) {
		this.agrupamentoJogosDao = agrupamentoJogosDao;
	}

	@Transactional
	public void save(Jogo jogo) 
			throws ConstraintViolationException {
		jogoDao.save(jogo);
	}

	@Transactional
	public Jogo findById(Integer jogoId) {
		return jogoDao.findById(jogoId);
	}

	@Transactional
	public Jogo findByNome(String nome) {
		return jogoDao.findByNome(nome);
	}

	@Transactional
	public Jogo findByNomeEGrupo(String nomeJogo, String nomeGrupo) {
		return jogoDao.findByNomeEGrupo(nomeJogo, nomeGrupo);
	}

	@Transactional
	public List<Jogo> findByUserId(Integer usuarioId) {
		return jogoDao.findByUserId(usuarioId);
	}
	
	@Transactional
	public JogoUsuario findJogoUsuario(String usuarioNome, String jogoNome) {
		return jogoUsuarioDao.findJogoUsuario(usuarioNome, jogoNome);
	}

	/**
	 * Procura todos os usu�rios relacionados a um jogo, com exce��o
	 * de um determinado usu�rio.
	 * @param jogoNome: Nome do jogo.
	 * @param ulNome: Usu�rio que n�o ser� inclu�do no resultado.
	 * @return Um mapa de Usu�rios e relacionamentos com um jogo
	 * excetuando o usu�rio informado.
	 */
	@Transactional
	public TreeMap<String, JogoUsuario> findJogoUsuariosExUl(
			String jogoNome, String ulNome) {
		List<JogoUsuario> jogoUsuarios = 
				jogoUsuarioDao.findJogoUsuariosExUl(jogoNome, ulNome);
		TreeMap<String, JogoUsuario> jogoUsuariosMap = 
				new TreeMap<String, JogoUsuario>();
		for(JogoUsuario ju : jogoUsuarios) {
			jogoUsuariosMap.put(ju.getUsuario().getNome(), ju);
		}
		return jogoUsuariosMap;
	}

	@Transactional
	public void remover(JogoUsuario ju) {
		jogoUsuarioDao.delete(ju);
	}

	/**
	 * Avalia os dados do jogo rec�m terminado para decidir
	 * se o posicionamento do usu�rio no ranking deve ou n�o consider�-los.
	 * @param jogoId: id do jogo na camada de persist�ncia.
	 * @param usuarioId: id do usu�rio na camada de persist�ncia.
	 * @param jogadasDados: os dados da jogada.
	 * @param tempoTotalJogo: tempo total de jogo.
	 * @param questoesList: lista de quest�es do jogo.
	 * @return Verdadeiro, caso a jogada entre no ranking, ou Falso, caso 
	 * contr�rio. 
	 */
	@Transactional
	public boolean saveDadosJogadas(Integer jogoId, Integer usuarioId, 
			TreeMap<Integer, DadosJogada> jogadasDados, Long tempoTotalJogo,
			List<Questao> questoesList) {
		Integer noAcertos = 0;
		DadosJogada dadosJogada = null;
		
		/*Contando o total de acertos na �ltima jogada*/
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
			
			/*Condi��o para atualizar seus dados no ranking*/
			if (noAcertos >= melhorNoAcertosAtual) {
				if (!(noAcertos == melhorNoAcertosAtual 
						&& tempoTotalJogo > melhorTempoAtual)) {
					/*Atualizando os dados de jogada.*/
					jogoUsuario.setMelhorNumeroAcertos(noAcertos);
					jogoUsuario.setMelhorTempo(tempoTotalJogo);
					jogoDao.saveJogoUsuario(jogoUsuario);
					
					/*Armazenando os acertos por quest�o (do jogador).*/
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
	 * Informa se um determinado usu�rio pode jogar um determinado jogo.
	 * @param usuarioId: o id do usu�rio na camada de persist�ncia.
	 * @param jogoId: o id do jogo na camada de persist�ncia.
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

	/**
	 * Informa se um determinado usu�rio pode editar um determinado jogo.
	 * @param jogoId: o id do jogo em quest�o.
	 * @param usuarioId: o id do usu�rio em quest�o.
	 * @return Verdadeiro ou Falso.
	 */
	@Transactional
	public boolean isEditableByUser(Integer jogoId, Integer usuarioId) {
		JogoUsuario jogoUsuario = 
				jogoDao.findUsuarioEditaJogo(usuarioId, jogoId);
		if (jogoUsuario != null) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public void saveGameInfo(AgrupamentoJogos agrupamento, Jogo jogo,
			ArrayList<Questao> questoesList, 
			Collection<JogoUsuario> jogoUsuarios) throws Exception {
		this.agrupamentoJogosDao.save(agrupamento);
		jogoDao.save(jogo);
		for (Questao questao : questoesList) {
			questaoDao.save(questao);
		}
		for (JogoUsuario ju : jogoUsuarios) {
			jogoUsuarioDao.save(ju);
		}
	}

}
