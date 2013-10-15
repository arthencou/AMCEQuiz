package br.uel.amcequiz.service;

import java.util.List;

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
	public void save(AgrupamentoJogos agrupamento) {
		agrupamentoJogosDao.save(agrupamento);
	}

	@Transactional
	public AgrupamentoJogos findById(Integer grupoId) {
		return agrupamentoJogosDao.findById(grupoId);
	}
	
	@Transactional
	public AgrupamentoJogos findByNome(String nome) {
		return agrupamentoJogosDao.findByNome(nome);
	}

	@Transactional
	public List<Object[]> playersRankByGrupo(Integer grupoId) {
		return agrupamentoJogosDao.playersRankByGrupo(grupoId);
	}

}
