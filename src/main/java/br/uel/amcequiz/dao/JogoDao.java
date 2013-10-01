package br.uel.amcequiz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.uel.amcequiz.model.JogosUsuarios;
import br.uel.amcequiz.model.Jogo;
import br.uel.amcequiz.util.HibernateUtils;

@Repository
public class JogoDao {

	@SuppressWarnings("unchecked")
	public List<Jogo> findByUserId(Integer usuarioId) {
		return HibernateUtils.getSessionFactory().openSession()
				.createQuery("select j from Jogo j join j.usuarios u where u.id = :id ")
				.setInteger("id", usuarioId)
				.list();
	}

	public JogosUsuarios getJogoUsuario(Integer jogoId, Integer usuarioId) {
		return (JogosUsuarios) HibernateUtils.getSessionFactory().openSession()
				.createQuery("from JogosUsuarios where jogo.id = :jogoId " +
						"and usuario.id = :usuarioId ")
				.setInteger("jogoId", jogoId)
				.setInteger("usuarioId", usuarioId)
				.uniqueResult();
	}

	public void saveJogoUsuario(JogosUsuarios jogoUsuario) {
		
		System.out.println("\n dadosJogada\n" +
				"\t melhor tempo: "+jogoUsuario.getMelhorTempo()+"\n"+
				"\t melhor numero acertos: "+jogoUsuario.getMelhorNumeroAcertos()+"\n");
		
		HibernateUtils.getSessionFactory().openSession().saveOrUpdate(jogoUsuario);
	}

}
