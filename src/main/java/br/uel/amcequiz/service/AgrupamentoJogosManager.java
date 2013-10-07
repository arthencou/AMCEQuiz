package br.uel.amcequiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uel.amcequiz.dao.AgrupamentoJogosDao;
import br.uel.amcequiz.model.AgrupamentoJogos;

@Service
public class AgrupamentoJogosManager {

	private AgrupamentoJogosDao agrupamentoJogosDao;

	@Autowired
	public void setAgrupamentoJogos (AgrupamentoJogosDao agrupamentoJogosDao) {
		this.agrupamentoJogosDao = agrupamentoJogosDao;
	}
	
	@Transactional
	public void save(AgrupamentoJogos agrupamento) 
			throws Exception {
		AgrupamentoJogos ag = 
				agrupamentoJogosDao.findByNome(agrupamento.getNome());
		if (ag == null) {
			agrupamentoJogosDao.save(agrupamento);
		} else {
			agrupamento.setId(ag.getId());
			agrupamento.setNome(ag.getNome());
		}
	}
	
	@Transactional
	public AgrupamentoJogos findByNome(String nome) {
		return agrupamentoJogosDao.findByNome(nome);
	}

}
