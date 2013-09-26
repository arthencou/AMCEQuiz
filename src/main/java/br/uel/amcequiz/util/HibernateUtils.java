package br.uel.amcequiz.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Este bean é um container para tornar acessível a criação de 
 * sessões com a camada de dados.
 */
@Component
public class HibernateUtils {

	private static SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory _sessionFactory) {
		sessionFactory = _sessionFactory;
	}

	/**
	 * 
	 * @return O bean de sessionFactory da aplicação.
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static void openSession() {
		sessionFactory.openSession();
	}

	/**
	 * 
	 * @return Uma sessão com a camada de persistência.
	 */
	public static Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
}