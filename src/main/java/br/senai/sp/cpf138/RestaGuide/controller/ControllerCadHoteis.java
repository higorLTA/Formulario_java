package br.senai.sp.cpf138.RestaGuide.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cpf138.RestaGuide.model.Administrador;
import br.senai.sp.cpf138.RestaGuide.model.TipoHotel;
import br.senai.sp.cpf138.RestaGuide.repository.RepositoryCad;


@Controller
public class ControllerCadHoteis {
	
	@Autowired
	private RepositoryCad repository;
	
	@RequestMapping(value = "CadHoteis", method = RequestMethod.GET)
	public String formcadastro() {
		return "hotel/FormCadHotel";
		
	}
	
	@RequestMapping(value = "SalvarHotel", method = RequestMethod.POST)
	public String salvarHotel(@Valid TipoHotel tipoH, BindingResult result, RedirectAttributes attr) {
		
		//verifica se holve erro na validação do objeto		
		if(result.hasErrors()) {
		//envia mensagem de erro via requisição
		attr.addFlashAttribute("mensagemErro", "Verifique os campos");
		return "redirect:CadHoteis";
					
			}
				
		try {
			//salva o Hotel
			repository.save(tipoH);
			attr.addFlashAttribute("mensagemSucesso", "Switch cadastrada com sucesso. ID"+tipoH.getId());
			return "redirect:listaSwitchs/1";
					
		}catch(Exception e) {
			attr.addFlashAttribute("mensagemErro", "houve um erro ao cadastrar a Switch escolhida:"+e.getMessage());
					
			}
		
		return"redirect:listaSwitchs/1"; 
	}
	
	@RequestMapping("listaSwitchs/{page}")
	public String listar(Model model, @PathVariable("page") int page ) {
		
				//cria um pageable com 6 elementos por pagina, ordenando os objetos
				PageRequest pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC,"nome"));
				
				//cria a pagina atual atravez do repository 
				Page<TipoHotel> pagina = repository.findAll(pageable);
				
				//descobrir o total de paginas 
				int totalPages = pagina.getTotalPages();
				
				//cria uma lista de inteiros para representar uma pagina
				List<Integer> pageNumbers = new ArrayList<Integer>();
				
				//preencher a lista com as páginas
				for(int i = 0; i < totalPages; i++ ) {
					pageNumbers.add(i+1);
				}
				
				//adiciona as variaveis na model
				model.addAttribute("switchs", pagina.getContent());
				model.addAttribute("paginaAtual", page);
				model.addAttribute("totalPaginas", totalPages);
				model.addAttribute("numPaginas", pageNumbers);
				
				//retorna para o HTML da lista
				return "hotel/listaCad";
			}
	
	@RequestMapping("alterarSwitchs")
	public String alterarCadrastro(Model model, Long id) {
		
		TipoHotel tipoHotel = repository.findById(id).get();
		
		model.addAttribute("TipoHotel", tipoHotel);
		
		return "forward:CadHoteis";
		
	}
	
	@RequestMapping("excluirSwitchs")
	public String excluirCadrastros(Long id) {
		repository.deleteById(id);
		return "redirect:listaSwitchs/1";
	}
	
	@RequestMapping("buscarPalavraChave")
	public String buscarPelaChave(String palavraChave, Model model) {
	
	 
		model.addAttribute("switchs", repository.procurarPalavraChave(palavraChave));
		
		if (palavraChave == null) {
		model.addAttribute("msg", "Switch não encontrada.");
		
		}
		 
		
		return "hotel/listaCad";
		
	}

	}


