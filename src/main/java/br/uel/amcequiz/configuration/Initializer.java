package br.uel.amcequiz.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Initializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(AppConfig.class);

		container.addListener(new ContextLoaderListener(context));
		container.setInitParameter("defaultHtmlEscape", "true");

		Dynamic dispatcher = container.addServlet("dispatcher",
				new DispatcherServlet(context));
		dispatcher.addMapping("/");
		dispatcher.setLoadOnStartup(1);
	}
}