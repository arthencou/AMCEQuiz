package org.springframework.security.web.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import br.uel.amcequiz.util.CustomUserDetails;

public class CustomAuthenticationSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		super.onAuthenticationSuccess(request, response, authentication);

		Object principal = authentication.getPrincipal();

		request.setAttribute("loginFailure", false);
		request.getSession().setAttribute("usuario",
				((CustomUserDetails) principal).getUsuario());
	}
}