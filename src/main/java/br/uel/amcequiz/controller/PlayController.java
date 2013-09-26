package br.uel.amcequiz.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlayController {
	
	@RequestMapping("/play")
	public String play(@RequestParam String jogo, Model model) {
		model.addAttribute("jogoId", Integer.parseInt(jogo));
		return "play";
	}

	@RequestMapping(value = "/question", method = RequestMethod.POST)
	public ModelAndView question() {
		ModelAndView model = new ModelAndView("play/question");
		model.addObject("question", new String("TESTE"));
		return model;
	}
	
	@RequestMapping(value = "/alternatives", method = RequestMethod.POST)
	public ModelAndView alternatives() {
		ModelAndView model = new ModelAndView("play/alternatives");
		HashMap<String, String> alternativas = new HashMap<String, String>();
		alternativas.put("a", "alternativa 1");
		alternativas.put("b", "alternativa 2");
		model.addObject("alternativasList", alternativas);
		return model;
	}

}
