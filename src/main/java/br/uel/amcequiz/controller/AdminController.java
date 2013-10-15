package br.uel.amcequiz.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.uel.amcequiz.model.AgrupamentoJogos;
import br.uel.amcequiz.model.Jogo;
import br.uel.amcequiz.model.JogoUsuario;
import br.uel.amcequiz.model.Questao;
import br.uel.amcequiz.model.Usuario;
import br.uel.amcequiz.service.AgrupamentoJogosManager;
import br.uel.amcequiz.service.JogoManager;
import br.uel.amcequiz.service.QuestaoManager;
import br.uel.amcequiz.service.UsuarioManager;
import br.uel.amcequiz.util.SpringSecurityUtils;

@Controller
@Lazy
@RequestMapping(value = "/admin")
public class AdminController {
	
	private JogoManager jogoManager;
	private AgrupamentoJogosManager agrupamentoJogosManager;
	private QuestaoManager questaoManager;
	private UsuarioManager usuarioManager;
	
	@Autowired
	public void setJogoManager(JogoManager jogoManager) {
		this.jogoManager = jogoManager;
	}

	@Autowired
	public void setAgrupamentJogosManager(
			AgrupamentoJogosManager agrupamentJogosManager) {
		this.agrupamentoJogosManager = agrupamentJogosManager;
	}
	
	@Autowired
	public void setQuestaoManager(QuestaoManager questaoManager) {
		this.questaoManager = questaoManager;
	}
	
	@Autowired
	public void setUsuarioManager(UsuarioManager usuarioManager) {
		this.usuarioManager = usuarioManager;
	}
	
	@ModelAttribute("agrupamento")
	public synchronized AgrupamentoJogos getAgrupamento(
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		AgrupamentoJogos agrupamento = 
				(AgrupamentoJogos) session.getAttribute("agrupamento");
		if (agrupamento == null) {
			agrupamento = new AgrupamentoJogos();
		}
		return agrupamento;
	}

	@ModelAttribute("jogo")
	public synchronized Jogo getJogo(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Jogo jogo = (Jogo) session.getAttribute("jogo");
		if (jogo == null) {
			jogo = new Jogo();
			session.setAttribute("jogo", jogo);
		}
		return jogo;
	}
	
	@SuppressWarnings("unchecked")
	@ModelAttribute("questao")
	public synchronized Questao getQuestao(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Questao questaoAtual = (Questao) session.getAttribute("questaoAtual");
		if (questaoAtual == null) {
			questaoAtual = new Questao();
			ArrayList<Questao> questoesList = (ArrayList<Questao>)
					session.getAttribute("questoesList");
			if (questoesList == null) {
				questoesList = new ArrayList<Questao>();
				session.setAttribute("questoesList", questoesList);
			}
			questaoAtual.setJogo((Jogo)session.getAttribute("jogo"));
			questaoAtual.setNumero(1);
			questaoAtual.setResposta("a");
			questoesList.add(questaoAtual);
			session.setAttribute("questaoAtual", questaoAtual);
			novaAlternativa(request);
		}
		return questaoAtual;
	}

	@RequestMapping(value = "/newgame", method = RequestMethod.GET)
	public synchronized String newgame(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		getAgrupamento(request);
		getJogo(request);
		getQuestao(request);
		session.setAttribute(
				"jogoUsuarios", new TreeMap<String, JogoUsuario>());
		return "admin/newgame";
	}
	
	@RequestMapping(value = "/editgame", method = RequestMethod.GET)
	public synchronized String editgame(
			HttpServletRequest request, @RequestParam String gameid) {
		HttpSession session = request.getSession(false);
		
		Jogo jogo = jogoManager.findById(Integer.parseInt(gameid));
		if (jogo != null && jogoManager.isEditableByUser(jogo.getId(),
				SpringSecurityUtils.usuarioLogado().getId())) {
			session.setAttribute("agrupamento", jogo.getGrupo());
			session.setAttribute("jogo", jogo);
			
			ArrayList<Questao> qlist = (ArrayList<Questao>) 
					questaoManager.findAllByJogoId(jogo.getId());
			session.setAttribute("questoesList", qlist);
			session.setAttribute("questaoAtual", qlist.get(0));
			
			TreeMap<String, JogoUsuario> jogoUsuarios = 
					jogoManager.findJogoUsuariosExUl(jogo.getNome(),
							SpringSecurityUtils.usuarioLogado().getNome());
			if (jogoUsuarios == null) {
				jogoUsuarios = new TreeMap<String, JogoUsuario>();
			}
			session.setAttribute("jogoUsuarios", jogoUsuarios);
			
			return "admin/newgame";
		}
		
		return "redirect:/home";
	}
	
	
	/*Edição - identificação do jogo*/
	
	@RequestMapping(value = "/editjogo", method = RequestMethod.GET)
	public synchronized ModelAndView editJogo(HttpServletRequest request) {
		return new ModelAndView("admin/editjogo");
	}
	
	@RequestMapping(value = "/saveagrupamento", method = RequestMethod.POST)
	public synchronized @ResponseBody void saveAgrupamento(
			HttpServletRequest request,
			@ModelAttribute AgrupamentoJogos agrupamento)  {
		HttpSession session = request.getSession(false);
		AgrupamentoJogos aj = 
				agrupamentoJogosManager.findByNome(agrupamento.getNome());
		if (aj != null) {
			agrupamento = aj;
		}
		session.setAttribute("agrupamento", agrupamento);
	}
	
	@RequestMapping(value = "/savejogo", method = RequestMethod.POST)
	public synchronized @ResponseBody void saveJogo(
			HttpServletRequest request, @ModelAttribute Jogo jogo) {
		HttpSession session = request.getSession(false);
		AgrupamentoJogos agrupamento = 
				(AgrupamentoJogos) session.getAttribute("agrupamento");
		Jogo jogo_ = null;
		if (agrupamento != null && agrupamento.getChecked()) {
			jogo_ = jogoManager.findByNomeEGrupo(
					jogo.getNome(), agrupamento.getNome());
		} else {
			jogo_ = jogoManager.findByNome(jogo.getNome());
		}
		if (jogo_ != null) {
			jogo = jogo_;
		}
		jogo.setGrupo(agrupamento);
		session.setAttribute("jogo", jogo);
	}
	
	
	/*Edição - questões*/

	private final String[] alfabeto = {"a","b","c","d","e","f","g","h","i","j","k"};
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editquestoes", method = RequestMethod.GET)
	public synchronized ModelAndView editQuestoes(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ModelAndView model = new ModelAndView("admin/editquestoes");
		
		ArrayList<Questao> questoesList = (ArrayList<Questao>) 
				session.getAttribute("questoesList");
		model.addObject("questoesList", questoesList);
		
		model.addObject("alfabeto", alfabeto);
		
		return model;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setquestao", method = RequestMethod.POST)
	public synchronized @ResponseBody void setQuestao(
			HttpServletRequest request, @RequestParam String qnum) {
		HttpSession session = request.getSession();
		
		ArrayList<Questao> questoesList = (ArrayList<Questao>) 
				session.getAttribute("questoesList");
		
		Integer numero = Integer.parseInt(qnum);
		Questao questaoAtual = questoesList.get(numero-1);
		session.setAttribute("questaoAtual", questaoAtual);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/savequestao", method = RequestMethod.POST)
	public synchronized @ResponseBody void saveQuestao(
			HttpServletRequest request, 
			@ModelAttribute("questao") Questao questaoAtual) {
		HttpSession session = request.getSession();
		
		ArrayList<Questao> questoesList = (ArrayList<Questao>) 
				session.getAttribute("questoesList");
		
		int index = questaoAtual.getNumero()-1;
		questoesList.remove(index);
		questoesList.add(index, questaoAtual);
		
		session.setAttribute("questaoAtual", questaoAtual);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/newquestao", method = RequestMethod.POST)
	public synchronized @ResponseBody void novaQuestao(
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		ArrayList<Questao> questoesList = (ArrayList<Questao>) 
				session.getAttribute("questoesList");
		
		Questao questaoAtual = new Questao();
		questaoAtual.setJogo((Jogo)session.getAttribute("jogo"));
		questaoAtual.setResposta("a");
		questaoAtual.setNumero(questoesList.size()+1);
		questoesList.add(questaoAtual);
		session.setAttribute("questaoAtual", questaoAtual);
		
		novaAlternativa(request);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletequestao", method = RequestMethod.POST)
	public synchronized @ResponseBody void deleteQuestao(
			HttpServletRequest request, @RequestParam String qnum) {
		HttpSession session = request.getSession();
		Integer numero = Integer.parseInt(qnum);
		
		ArrayList<Questao> questoesList = (ArrayList<Questao>) 
				session.getAttribute("questoesList");
		questoesList.remove(numero-1);
		for (int i=0; i<questoesList.size(); i++) {
			Questao q = questoesList.get(i);
			q.setNumero(i+1);
		}
		
		session.setAttribute("questaoAtual", questoesList.get(0));
	}
	
	@RequestMapping(value = "/newalternativa", method = RequestMethod.POST)
	public synchronized @ResponseBody void novaAlternativa(
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		Questao questaoAtual = (Questao) session.getAttribute("questaoAtual");
		
		int letra = questaoAtual.getAlternativas().size();
		questaoAtual.getAlternativas().put(alfabeto[letra], "");
	}
	
	@RequestMapping(value = "/deletealternativa", method = RequestMethod.POST)
	public synchronized @ResponseBody void deleteAlternativa(
			HttpServletRequest request, @RequestParam String letra) {
		HttpSession session = request.getSession();
		
		Questao questaoAtual = (Questao) session.getAttribute("questaoAtual");
		
		TreeMap<String, String> alternativas = questaoAtual.getAlternativas();
		alternativas.remove(letra);
		
		int i=0;
		TreeMap<String, String> alternativas_ = new TreeMap<String, String>(); 
		for (Map.Entry<String, String> entry : alternativas.entrySet()) {
			alternativas_.put(alfabeto[i], entry.getValue());
		}
		
		questaoAtual.setAlternativas(alternativas_);
	}
	
	
	/*Edição - permissões*/

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editpermissoes", method = RequestMethod.GET)
	public synchronized ModelAndView editPermissoes(
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		TreeMap<String, JogoUsuario> jogoUsuarios = 
				(TreeMap<String, JogoUsuario>) 
				session.getAttribute("jogoUsuarios");
		
		ArrayList<JogoUsuario> jus = 
				new ArrayList<JogoUsuario>(jogoUsuarios.values());
		
		ModelAndView model = new ModelAndView("admin/editpermissoes");
		model.addObject("jogousuarios", jus);
		
		return model;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/newusuario", method = RequestMethod.POST)
	public synchronized @ResponseBody void novoUsuario(
			HttpServletRequest request, @RequestParam String nome) {
		HttpSession session = request.getSession(false);
		
		Usuario usuario = usuarioManager.findByNome(nome);
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setNome(nome);
		}
		Jogo jogo = (Jogo) session.getAttribute("jogo");
		JogoUsuario jogoUsuario = 
				jogoManager.findJogoUsuario(nome, jogo.getNome());
		if (jogoUsuario == null) {
			jogoUsuario = new JogoUsuario();
			jogoUsuario.setJogo(jogo);
			jogoUsuario.setUsuario(usuario);
		}

		TreeMap<String, JogoUsuario> jogoUsuarios = 
				(TreeMap<String, JogoUsuario>) 
				session.getAttribute("jogoUsuarios");
		jogoUsuarios.put(nome, jogoUsuario);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteusuario", method = RequestMethod.POST)
	public synchronized @ResponseBody void excluirUsuario(
			HttpServletRequest request, @RequestParam String nome) {
		HttpSession session = request.getSession(false);
		TreeMap<String, JogoUsuario> jogoUsuarios = 
				(TreeMap<String, JogoUsuario>) 
				session.getAttribute("jogoUsuarios");
		JogoUsuario ju = jogoUsuarios.get(nome);
		if (ju != null && ju.getUsuario().getId() != null) {
			jogoManager.remover(ju);
		}
		jogoUsuarios.remove(nome);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveusuario", method = RequestMethod.POST)
	public synchronized @ResponseBody void salvarUsuario(
			HttpServletRequest request, @RequestParam String nome,
			@RequestParam String partidas, @RequestParam String podeEditar) {
		HttpSession session = request.getSession(false);
		
		excluirUsuario(request, nome);
		Usuario u = usuarioManager.findByNome(nome);
		if (u == null) {
			u = new Usuario();
			u.setNome(nome);
		}
		JogoUsuario ju = new JogoUsuario();
		ju.setUsuario(u);
		ju.setJogo((Jogo)session.getAttribute("jogo"));

		TreeMap<String, JogoUsuario> jogoUsuarios = 
				(TreeMap<String, JogoUsuario>)
				session.getAttribute("jogoUsuarios");
		jogoUsuarios.put(nome, ju);
	}
	
	
	/*Edição - submissão*/
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/persistgamechanges")
	public synchronized @ResponseBody String persistChanges(
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		Jogo jogo = (Jogo) session.getAttribute("jogo");

		//Criando um relacionamento entre o usuário logado e seu novo jogo.
		Usuario u = SpringSecurityUtils.usuarioLogado();
		JogoUsuario ju = 
				jogoManager.findJogoUsuario(u.getNome(), jogo.getNome());
		if (ju == null) {
			ju = new JogoUsuario();
			ju.setJogo((Jogo) session.getAttribute("jogo"));
			ju.setUsuario(u);
			ju.setPodeEditar(true);
		}
		TreeMap<String, JogoUsuario> jogoUsuarios = 
				(TreeMap<String, JogoUsuario>) 
				session.getAttribute("jogoUsuarios");
		jogoUsuarios.put(u.getNome(), ju);

		try {
			jogoManager.saveGameInfo(
					(AgrupamentoJogos) session.getAttribute("agrupamento"),
					jogo,
					(ArrayList<Questao>)session.getAttribute("questoesList"),
					jogoUsuarios.values() );
		} catch (Exception e) {
			e.printStackTrace();
			return "{successful:'false'}";
		}

		flushChanges(request);
		return "{successful:'true'}";
	}

	@RequestMapping("/flushgamechanges")
	public synchronized @ResponseBody void flushChanges(
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.removeAttribute("jogo");
		session.removeAttribute("agrupamento");
		session.removeAttribute("questaoAtual");
		session.removeAttribute("questoesList");
		session.removeAttribute("jogoUsuarios");
	}
	
	
	/*Ranking - view*/
	
	@RequestMapping("/ranking")
	public synchronized ModelAndView ranking(HttpServletRequest request, 
			@RequestParam String grupoid) {
		Integer grupoId = Integer.parseInt(grupoid);
		AgrupamentoJogos grupo = agrupamentoJogosManager.findById(grupoId);
		if (grupo != null) {
			List<Object[]> playersRank = agrupamentoJogosManager
					.playersRankByGrupo(grupoId);
			
			return new ModelAndView("admin/ranking", "playersRank", playersRank);
		}
		
		return new ModelAndView("redirect:/home");
	}

}
