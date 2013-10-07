package br.uel.amcequiz.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.uel.amcequiz.service.JogoManager;
import br.uel.amcequiz.model.Usuario;

@Controller
public class HomeController {
	
	private JogoManager jogoManager;
	
	@Autowired
	public void setJogoManager(JogoManager jogoManager) {
		this.jogoManager = jogoManager;
	}

	@ModelAttribute("usuario") Usuario getUsuario() {
		return new Usuario();
	}
	
	/*@RequestMapping(value = "/", method = RequestMethod.GET)
	public String identify(Model model) {
		return "identify";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String submitLogin(@ModelAttribute("usuario") Usuario usuarioForm,
			Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		
		Usuario usuario = usuarioManager.logar(usuarioForm.getNome());
		if (usuario != null) {
			session.setAttribute("usuario", usuario);
			return "redirect:home";
		} else {
			return "redirect:/";
		}
	}*/
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		session.removeAttribute("jogoId");
		session.removeAttribute("inicioJogo");
		session.removeAttribute("noQuestao");
		session.removeAttribute("questao");
		session.removeAttribute("jogadasDados");
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		return new ModelAndView("home", 
				"gamesList", jogoManager.findByUserId(usuario.getId()));
	}
	
}
