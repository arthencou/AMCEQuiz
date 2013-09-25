package br.uel.amcequiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlayController {
	
	@RequestMapping("/play")
	public String play(@RequestParam String jogo, Model model) {
		model.addAttribute("jogoId", Integer.parseInt(jogo));
		return "play";
	}

}
