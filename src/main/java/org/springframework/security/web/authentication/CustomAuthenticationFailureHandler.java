package org.springframework.security.web.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(
			HttpServletRequest request, HttpServletResponse response, 
			AuthenticationException exception) 
					throws IOException, ServletException {
		super.onAuthenticationFailure(request, response, exception);

		if (exception.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
			request.getSession().setAttribute("usernameNotFound", true);
		} else if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
			request.getSession().setAttribute("badCredentials", true);
		}
	}

}