package br.uel.amcequiz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccessController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String identify() {
		return "redirect:login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session != null) {
			if (session.getAttribute("usuario") != null) {
				return "redirect:home";
			}
		}

		return "login";
	}
	
	@RequestMapping(value = "/loginFailure", method = RequestMethod.GET)
	public String loginFailure(HttpServletRequest request) {
		if (request.getSession().getAttribute("badCredentials") != null) {
			System.out.println("\n Encontrou!!!\n");
			request.getSession().removeAttribute("badCredentials");
			request.setAttribute("badCredentials", true);
		}
		return login(request);
	}
	
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDenied() {
		return "redirect:home";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		return "redirect:login";
	}
	
}
