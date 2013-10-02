package br.uel.amcequiz.service;

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
	
	@Transactional(readOnly = true)
	public Usuario logar(String nome) {
		return usuarioDao.findByNome(nome);
	}

}
