package br.uel.amcequiz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.uel.amcequiz.model.AgrupamentoJogos;
import br.uel.amcequiz.model.Jogo;
import br.uel.amcequiz.service.AgrupamentoJogosManager;
import br.uel.amcequiz.service.JogoManager;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	private JogoManager jogoManager;
	private AgrupamentoJogosManager agrupamentoJogosManager;
	
	@Autowired
	public void setJogoManager(JogoManager jogoManager) {
		this.jogoManager = jogoManager;
	}

	@Autowired
	public void setAgrupamentJogosManager(
			AgrupamentoJogosManager agrupamentJogosManager) {
		this.agrupamentoJogosManager = agrupamentJogosManager;
	}
	
	@ModelAttribute("agrupamento")
	public AgrupamentoJogos getAgrupamento(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		AgrupamentoJogos agrupamento = 
				(AgrupamentoJogos) session.getAttribute("agrupamento");
		if (agrupamento == null) {
			agrupamento = new AgrupamentoJogos();
		}
		return agrupamento;
	}

	@ModelAttribute("jogo")
	public Jogo getJogo(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Jogo jogo = (Jogo) session.getAttribute("jogo");
		if (jogo == null) {
			jogo = new Jogo();
		}
		return jogo;
	}
	
	@RequestMapping("/newgame")
	public synchronized String newgame() {
		return "admin/newgame";
	}

	@RequestMapping(value = "/editjogo", method = RequestMethod.GET)
	public synchronized ModelAndView editJogo() {
		return new ModelAndView("admin/editjogo");
	}
	
	@RequestMapping(value = "/saveagrupamento", method = RequestMethod.POST)
	public synchronized @ResponseBody void saveAgrupamento(
			HttpServletRequest request,
			@ModelAttribute AgrupamentoJogos agrupamento)  {
		HttpSession session = request.getSession(false);
		AgrupamentoJogos aj = 
				agrupamentoJogosManager.findByNome(agrupamento.getNome());
		if (aj != null) {
			agrupamento = aj;
		}
		session.setAttribute("agrupamento", agrupamento);
	}
	
	@RequestMapping(value = "/savejogo", method = RequestMethod.POST)
	public synchronized @ResponseBody void saveJogo(
			HttpServletRequest request, @ModelAttribute Jogo jogo) {
		HttpSession session = request.getSession(false);
		AgrupamentoJogos agrupamento = 
				(AgrupamentoJogos) session.getAttribute("agrupamento");
		Jogo jogo_ = null;
		if (agrupamento != null) {
			jogo_ = jogoManager.findByNomeEGrupo(
					jogo.getNome(), agrupamento.getNome());
		} else {
			jogo_ = jogoManager.findByNome(jogo.getNome());
		}
		if (jogo_ != null) {
			jogo = jogo_;
		}
		session.setAttribute("jogo", jogo);
	}

	@RequestMapping(value = "/editquestoes", method = RequestMethod.GET)
	public synchronized ModelAndView editQuestoes() {
		return new ModelAndView("admin/editquestoes");
	}

	@RequestMapping(value = "/editpermissoes", method = RequestMethod.GET)
	public synchronized ModelAndView editPermissoes() {
		return new ModelAndView("admin/editpermissoes");
	}
	
	@RequestMapping("/submit")
	public synchronized String submit(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		AgrupamentoJogos agrupamento = 
				(AgrupamentoJogos) session.getAttribute("agrupamento");

		try {
			agrupamentoJogosManager.save(agrupamento);
			//jogo.setGrupo(agrupamento);
		} catch (Exception e) {}
		
		return null;
	}

}
