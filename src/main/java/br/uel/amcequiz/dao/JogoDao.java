package br.uel.amcequiz.dao;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import br.uel.amcequiz.model.JogoUsuario;
import br.uel.amcequiz.model.Jogo;
import br.uel.amcequiz.util.HibernateUtils;

@Repository
public class JogoDao {

	public void save(Jogo jogo) throws ConstraintViolationException {
		HibernateUtils.getSessionFactory().getCurrentSession()
		.saveOrUpdate(jogo);
	}

	public Jogo findById(Integer jogoId) {
		return (Jogo) HibernateUtils.getSessionFactory().getCurrentSession()
				.createQuery("from Jogo where id = :jogoId ")
				.setInteger("jogoId", jogoId)
				.uniqueResult();
	}

	public Jogo findByNome(String nome) {
		return (Jogo) HibernateUtils.getSessionFactory().getCurrentSession()
				.createQuery("from Jogo where nome = :nome ")
				.setString("nome", nome)
				.uniqueResult();
	}

	public Jogo findByNomeEGrupo(String nomeJogo, String nomeGrupo) {
		return (Jogo) HibernateUtils.getSessionFactory().getCurrentSession()
				.createQuery("from Jogo where nome = :nomeJogo " +
						"and grupo.nome = :nomeGrupo ")
				.setString("nomeJogo", nomeJogo)
				.setString("nomeGrupo", nomeGrupo)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Jogo> findByUserId(Integer usuarioId) {
		return HibernateUtils.getSessionFactory().getCurrentSession()
				/*.createQuery("select j from Jogo j join j.usuarios u where u.id in(:id )")*/
				.createQuery("select j from JogoUsuario ju join ju.jogo j " +
						"join ju.usuario u where u.id = :id " +
						"and ju.qtddPartidasDisponiveis != :zero " +
						"order by j.grupo.nome, j.nome")
				.setInteger("id", usuarioId)
				.setInteger("zero", 0)
				.list();
	}

	public JogoUsuario getJogoUsuario(Integer jogoId, Integer usuarioId) {
		return (JogoUsuario) HibernateUtils.getSessionFactory().getCurrentSession()
				.createQuery("from JogoUsuario where jogo.id = :jogoId " +
						"and usuario.id = :usuarioId ")
				.setInteger("jogoId", jogoId)
				.setInteger("usuarioId", usuarioId)
				.uniqueResult();
	}

	public void saveJogoUsuario(JogoUsuario jogoUsuario) {
		HibernateUtils.getSessionFactory().getCurrentSession()/*.saveOrUpdate(jogoUsuario);*/
		.createSQLQuery("UPDATE jogo_usuario "
				+ "SET melhor_tempo = :melhorTempo , "
					+ "melhor_numero_acertos = :melhorNumeroAcertos "
				+ "WHERE usuario_id = :usuarioId AND jogo_id = :jogoId")
		.setLong("melhorTempo", jogoUsuario.getMelhorTempo())
		.setInteger("melhorNumeroAcertos", jogoUsuario.getMelhorNumeroAcertos())
		.setInteger("usuarioId", jogoUsuario.getUsuario().getId())
		.setInteger("jogoId", jogoUsuario.getJogo().getId())
		.executeUpdate();
	}

	public JogoUsuario findUsuarioEditaJogo(
			Integer usuarioId, Integer jogoId) {
		return (JogoUsuario) HibernateUtils.getSessionFactory().getCurrentSession()
				.createQuery("from JogoUsuario where usuario.id = :usuarioId " +
						"and jogo.id = :jogoId and podeEditar = true")
				.setInteger("usuarioId", usuarioId)
				.setInteger("jogoId", jogoId)
				.uniqueResult();
	}

}
