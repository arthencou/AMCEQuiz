package br.uel.amcequiz.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.uel.amcequiz.manager.JogoManager;
import br.uel.amcequiz.manager.UsuarioManager;
import br.uel.amcequiz.model.Usuario;

@Controller
public class HomeController {
	
	private UsuarioManager usuarioManager;
	private JogoManager jogoManager;
	
	@Autowired
	public void setUsuarioManager(UsuarioManager usuarioManager) {
		this.usuarioManager = usuarioManager;
	}

	@ModelAttribute("usuario") Usuario getUsuario() {
		return new Usuario();
	}
	
	@Autowired
	public void setJogoManager(JogoManager jogoManager) {
		this.jogoManager = jogoManager;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String identify(Model model) {
		return "identify";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String submitLogin(@ModelAttribute("usuario") Usuario usuarioForm,
			Model model) {
		Usuario usuario = usuarioManager.logar(usuarioForm.getNome());
		if (usuario != null) {
			model.addAttribute("gamesList", jogoManager.findByUserId(usuario.getId()));
			return "home";
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
}
