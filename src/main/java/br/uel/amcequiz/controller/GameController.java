package br.uel.amcequiz.controller;


import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.uel.amcequiz.service.JogoManager;
import br.uel.amcequiz.service.QuestaoManager;
import br.uel.amcequiz.model.Jogo;
import br.uel.amcequiz.model.Questao;
import br.uel.amcequiz.model.Usuario;

@Controller
@RequestMapping(value = "/game")
public class GameController {
	
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
	private synchronized DadosJogada generateDadosJogada(Questao questao, 
			HttpSession session) {
		Integer jogoId = (Integer) session.getAttribute("jogoId");
		
		DadosJogada dadosJogada = new DadosJogada();
		dadosJogada.setJogoId(jogoId);
		dadosJogada.setNoQuestao(questao.getNumero());
		//dadosJogada.setTimeStart(System.currentTimeMillis());
		
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
	
	@RequestMapping("/set")
	public synchronized String set(@RequestParam String game, 
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "redirect:/accessDenied";
		}
		
		Integer jogoId = Integer.parseInt(game);
		if (!jogoManager.isPlayableByUser(
				((Usuario)session.getAttribute("usuario")).getId(), jogoId) ){
			return "redirect:/accessDenied";
		}
		
		Questao firstQuestion = 
				questaoManager.findByJogoIdENum(jogoId, 1);
		
		if (firstQuestion != null) {
			session.setAttribute("jogoId", jogoId);
			session.setAttribute("questao", firstQuestion);
			session.setAttribute("noQuestao", 1L);
			generateDadosJogada(firstQuestion, session);
			
			return "game/goodluck";
		} else {
			return "redirect:/home";
		}
	}
	
	@RequestMapping("/play")
	public synchronized ModelAndView play(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ModelAndView("redirect:/accessDenied");
		}
		
		Integer jogoId = (Integer) session.getAttribute("jogoId");
		if (jogoId == null) {
			return new ModelAndView("redirect:/home");
		}
			
		ModelAndView modelAndView =  new ModelAndView("game/play");
		modelAndView.addObject("questoesList", 
				questaoManager.findAllByJogoId(jogoId));
		modelAndView.addObject("tempoMaximoJogo", 
				((Jogo) jogoManager.findById(jogoId)).getTempoMaximo());

		session.setAttribute("inicioJogo", System.currentTimeMillis());
		
		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectQuestion", method = RequestMethod.POST)
	public synchronized @ResponseBody void select(@RequestParam Integer qnum, 
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute("questao");
		Integer jogoId = (Integer) session.getAttribute("jogoId");
		
		Questao questao = questaoManager.findByJogoIdENum(jogoId, qnum);
		
		session.setAttribute("questao", questao);
		session.setAttribute("noQuestao", qnum);

		TreeMap<Integer, DadosJogada> jogadasDados = 
				(TreeMap<Integer, DadosJogada>) 
				session.getAttribute("jogadasDados");
		DadosJogada dadosJogada = jogadasDados.get(questao.getNumero());
		if (dadosJogada == null) {
			generateDadosJogada(questao, session);
		}
	}

	@RequestMapping(value = "/question", method = RequestMethod.POST)
	public synchronized ModelAndView question(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ModelAndView("redirect:/accessDenied");
		}
		
		Questao questao = (Questao) session.getAttribute("questao");
		
		return new ModelAndView("game/question", 
				"question", questao.getTexto());
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/alternatives", method = RequestMethod.POST)
	public synchronized ModelAndView alternatives(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ModelAndView("redirect:/accessDenied");
		}
		
		Questao questao = (Questao) session.getAttribute("questao");
		TreeMap<Integer, DadosJogada> jogadasDados = 
				(TreeMap<Integer, DadosJogada>) 
				session.getAttribute("jogadasDados");
		DadosJogada dadosJogada = jogadasDados.get(questao.getNumero());

		ModelAndView model = new ModelAndView("game/alternatives");
		model.addObject("alternativasList", questao.getAlternativas());
		if (dadosJogada.getIsCorrect() != null) {
			model.addObject("altSel", dadosJogada.getOpcao());
		}
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/answerQuestion", method = RequestMethod.POST)
	public synchronized @ResponseBody void answer(
			@RequestParam String op, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}

		Integer noQuestao = (Integer) session.getAttribute("noQuestao");
		
		TreeMap<Integer, DadosJogada> jogadasDados = 
				(TreeMap<Integer, DadosJogada>) 
				session.getAttribute("jogadasDados");
		
		DadosJogada dadosJogada = jogadasDados.get(noQuestao); 
		//dadosJogada.setTimeFinish(System.currentTimeMillis());
		
		Questao questao = (Questao) session.getAttribute("questao");
		
		if (op.equals(questao.getResposta())) {
			dadosJogada.setIsCorrect(new Boolean(true));
		} else {
			dadosJogada.setIsCorrect(new Boolean(false));
		}
		dadosJogada.setOpcao(op);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/gameover", method = RequestMethod.GET)
	public synchronized ModelAndView gameOver(HttpServletRequest request,
			@RequestParam(required = true) String save) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ModelAndView("redirect:/accessDenied");
		}
		
		Integer jogoId = (Integer) session.getAttribute("jogoId");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		TreeMap<Integer, DadosJogada> jogadasDados =
				(TreeMap<Integer, DadosJogada>)
				session.getAttribute("jogadasDados");
		Long inicioJogo = (Long) session.getAttribute("inicioJogo");
		Long finalJogo = System.currentTimeMillis();
		
		if (jogoId == null) {
			return new ModelAndView("redirect:/home");
		}
		
		ModelAndView model = new ModelAndView("game/gameover");

		Boolean save_ = null;
		Long tempoTotalJogo = finalJogo - inicioJogo;
		if (save != null && save.equals("true")) {
			if (jogoManager.saveDadosJogadas(
					jogoId, usuario.getId(), jogadasDados,
					tempoTotalJogo, questaoManager.findAllByJogoId(jogoId))) {
				save_ = true;
			} else {
				save_ = false;
			}
		}
		
		session.removeAttribute("jogoId");
		session.removeAttribute("inicioJogo");
		session.removeAttribute("noQuestao");
		session.removeAttribute("questao");
		session.removeAttribute("jogadasDados");

		model.addObject("save", save_);
		model.addObject("jogadasDados", jogadasDados);
		model.addObject("tempoTotalJogo", new Long(tempoTotalJogo / 1000));
		return model;
	}

}
