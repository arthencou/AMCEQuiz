package br.uel.amcequiz.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uel.amcequiz.dao.UsuarioDao;
import br.uel.amcequiz.model.Usuario;

@Service
public class UsuarioManager {
	
	private UsuarioDao usuarioDao;
	
	@Autowired
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
	@Transactional
	public boolean logar(Usuario usuario) {
		if (usuarioDao.findByLogin(usuario.getLogin()) != null) {
			return true;
		} else {
			return false;
		}
	}

}
