package br.uel.amcequiz.controller;


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

import br.uel.amcequiz.manager.QuestaoManager;
import br.uel.amcequiz.model.Questao;

@Controller
public class PlayController {
	
	private QuestaoManager questaoManager;
	
	@Autowired
	public void setQuestaoManager(QuestaoManager questaoManager) {
		this.questaoManager = questaoManager;
	}
	
	@RequestMapping("/play")
	public String play(@RequestParam String jogo, 
			HttpServletRequest request, Model model) {
		Integer jogoId = Integer.parseInt(jogo);
		HttpSession session = request.getSession(false);
		
		Questao firstQuestion = 
				questaoManager.findByJogoIdENum(jogoId, 1);
		
		session.setAttribute("jogoId", jogoId);
		session.setAttribute("questao", firstQuestion);
		session.setAttribute("noQuestao", new Integer(1));
		
		return "play";
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
	
	@RequestMapping(value = "/answer", method = RequestMethod.POST)
	public @ResponseBody String answer(
			@RequestParam String op, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		Questao questao = (Questao) session.getAttribute("questao");
		
		String isCorrect = null;
		if (op.equals(questao.getResposta())) {
			isCorrect = "{\"rightAns\":\"true\"}";
		} else {
			isCorrect = "{\"rightAns\":\"false\"}";
		}
		
		Integer jogoId = (Integer) session.getAttribute("jogoId");
		
		Integer noQuestao = (Integer) session.getAttribute("noQuestao");
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
			return "{\"isGameOver\":\"false\"}";
		}
	}
	
	@RequestMapping(value = "/gameover", method = RequestMethod.GET)
	public ModelAndView gameOver(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		
		
		session.removeAttribute("jogoId");
		session.removeAttribute("noQuestao");
		session.removeAttribute("questao");
		
		return new ModelAndView("redirect:/home");
	}

}
