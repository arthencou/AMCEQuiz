package br.uel.amcequiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.uel.amcequiz.model.AgrupamentoJogos;
import br.uel.amcequiz.model.Jogo;
import br.uel.amcequiz.service.JogoManager;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	private JogoManager jogoManager;
	
	@Autowired
	public void setJogoManager(JogoManager jogoManager) {
		this.jogoManager = jogoManager;
	}
	
	@ModelAttribute("agrupamento")
	public AgrupamentoJogos getAgrupamento() {
		return new AgrupamentoJogos();
	}

	@ModelAttribute("jogo")
	public Jogo getJogo() {
		return new Jogo();
	}
	
	@RequestMapping("/newgame")
	public String newgame() {
		return "admin/newgame";
	}

	@RequestMapping(value = "/editjogo", method = RequestMethod.GET)
	public @ResponseBody String editJogo() {
		return "admin/editjogo";
	}
	
	@RequestMapping(value = "/editjogo", method = RequestMethod.POST)
	public String submitjogo(@ModelAttribute AgrupamentoJogos agrupamento, 
			@ModelAttribute Jogo jogo) {
		if (jogoManager.saveJogo(agrupamento, jogo)) {
			
		}
	}

	@RequestMapping("/editquestoes")
	public String editQuestoes() {
		return "admin/editquestoes";
	}

	@RequestMapping("/editpermissoes")
	public String editPermissoes() {
		return "admin/editpermissoes";
	}

}
