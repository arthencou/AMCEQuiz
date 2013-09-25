package br.uel.amcequiz.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.uel.amcequiz.manager.JogoManager;
import br.uel.amcequiz.manager.UsuarioManager;
import br.uel.amcequiz.model.Usuario;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private UsuarioManager usuarioManager;
	private JogoManager jogoManager;
	
	@Autowired
	public void setUsuarioManager(UsuarioManager usuarioManager) {
		this.usuarioManager = usuarioManager;
	}
	
	@Autowired
	public void setJogoManager(JogoManager jogoManager) {
		this.jogoManager = jogoManager;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String identify(Model model) {
		return "identify";
	}
	
	@ModelAttribute() Usuario getUsuario() {
		return new Usuario();
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String submitLogin(@ModelAttribute("usuario") Usuario usuario,
			Model model) {
		if (usuarioManager.logar(usuario)) {
			model.addAttribute("gamesList", jogoManager.findByUserId(usuario.getId()));
			return "redirect:/home";
		} else {
			return "redirect:/";
		}
	}
	
}
