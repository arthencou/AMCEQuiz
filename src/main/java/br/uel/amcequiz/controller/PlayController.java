package br.uel.amcequiz.controller;


import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.uel.amcequiz.service.JogoManager;
import br.uel.amcequiz.service.QuestaoManager;
import br.uel.amcequiz.model.Questao;
import br.uel.amcequiz.model.Usuario;

@Controller
public class PlayController {
	
	private QuestaoManager questaoManager;
	private JogoManager jogoManager;
	
	@Autowired
	public void setQuestaoManager(QuestaoManager questaoManager) {
		this.questaoManager = questaoManager;
	}
	
	@Autowired
	public void setJogoManager(JogoManager jogoManager) {
		this.jogoManager = jogoManager;
	}
	
	@SuppressWarnings("unchecked")
	private DadosJogada generateDadosJogada(Questao questao, HttpSession session) {
		Integer jogoId = (Integer) session.getAttribute("jogoId");
		
		DadosJogada dadosJogada = new DadosJogada();
		dadosJogada.setJogoId(jogoId);
		dadosJogada.setNoQuestao(questao.getNumero());
		dadosJogada.setTimeStart(System.currentTimeMillis());
		
		TreeMap<Integer, DadosJogada> jogadasDados = 
				(TreeMap<Integer, DadosJogada>) 
				session.getAttribute("jogadasDados");
		if (jogadasDados == null) {
			jogadasDados = new TreeMap<Integer, DadosJogada>();
			session.setAttribute("jogadasDados", jogadasDados);
		}
		jogadasDados.put(questao.getNumero(), dadosJogada);
		
		return dadosJogada;
	}
	
	@RequestMapping("/play")
	public ModelAndView play(@RequestParam String jogo, 
			HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		
		Integer jogoId = Integer.parseInt(jogo);
		
		Questao firstQuestion = 
				questaoManager.findByJogoIdENum(jogoId, 1);
		
		if (firstQuestion != null) {
			session.setAttribute("jogoId", jogoId);
			session.setAttribute("questao", firstQuestion);
			session.setAttribute("noQuestao", new Integer(1));
			generateDadosJogada(firstQuestion, session);
			return new ModelAndView("play", "questoesList", 
					questaoManager.findAllByJogoId(jogoId));
		} else {
			return new ModelAndView("redirect:/home");
		}
	}
	
	@RequestMapping(value = "/select", method = RequestMethod.POST)
	public @ResponseBody void select(@RequestParam Integer qnum, 
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.removeAttribute("questao");
		Integer jogoId = (Integer) session.getAttribute("jogoId");
		Questao questao = questaoManager.findByJogoIdENum(jogoId, qnum);
		session.setAttribute("questao", questao);
	}

	@RequestMapping(value = "/question", method = RequestMethod.POST)
	public ModelAndView question(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		Questao questao = (Questao) session.getAttribute("questao");
		
		return new ModelAndView("play/question", 
				"question", questao.getTexto());
	}

	@RequestMapping(value = "/alternatives", method = RequestMethod.POST)
	public ModelAndView alternatives(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		Questao questao = (Questao) session.getAttribute("questao");

		return new ModelAndView("play/alternatives", 
				"alternativasList", questao.getAlternativas());
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/answer", method = RequestMethod.POST)
	public @ResponseBody String answer(
			@RequestParam String op, HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		Integer noQuestao = (Integer) session.getAttribute("noQuestao");
		
		TreeMap<Integer, DadosJogada> jogadasDados = 
				(TreeMap<Integer, DadosJogada>) 
				session.getAttribute("jogadasDados");
		
		DadosJogada dadosJogada = jogadasDados.get(noQuestao); 
		dadosJogada.setTimeFinish(System.currentTimeMillis());
		
		Questao questao = (Questao) session.getAttribute("questao");
		
		String isCorrect = null;
		if (op.equals(questao.getResposta())) {
			dadosJogada.setIsCorrect(true);
			isCorrect = "{\"rightAns\":\"true\"}";
		} else {
			dadosJogada.setIsCorrect(false);
			isCorrect = "{\"rightAns\":\"false\"}";
		}

		Integer jogoId = (Integer) session.getAttribute("jogoId");
		noQuestao = new Integer(++noQuestao);
		questao = questaoManager.findByJogoIdENum(jogoId, noQuestao);

		session.removeAttribute("noQuestao");
		session.setAttribute("noQuestao", noQuestao);
		session.removeAttribute("questao");
		session.setAttribute("questao", questao);
		
		return isCorrect;
	}
	
	@RequestMapping(value = "/checkgameover", method = RequestMethod.POST)
	public @ResponseBody String checkGameOver(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		Questao questao = (Questao) session.getAttribute("questao");
		
		if (questao == null) {
			return "{\"isGameOver\":\"true\"}";
		} else {
			generateDadosJogada(questao, session);
			return "{\"isGameOver\":\"false\"}";
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/gameover", method = RequestMethod.GET)
	public ModelAndView gameOver(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		Integer jogoId = (Integer) session.getAttribute("jogoId");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		TreeMap<Integer, DadosJogada> jogadasDados =
				(TreeMap<Integer, DadosJogada>)
				session.getAttribute("jogadasDados");
		
		if (jogoId == null) {
			return new ModelAndView("redirect:/home");
		}
		
		jogoManager.saveDadosJogadas(jogoId, usuario.getId(), jogadasDados);
		
		session.removeAttribute("jogoId");
		session.removeAttribute("noQuestao");
		session.removeAttribute("questao");
		session.removeAttribute("jogadasDados");
		
		return new ModelAndView("gameover", "jogadasDados", jogadasDados);
	}

}
