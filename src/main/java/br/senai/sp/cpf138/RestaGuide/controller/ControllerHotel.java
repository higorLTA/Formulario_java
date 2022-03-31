package br.senai.sp.cpf138.RestaGuide.controller;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Binding;
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
import br.senai.sp.cpf138.RestaGuide.repository.AdmRepository;
import br.senai.sp.cpf138.RestaGuide.util.HashUtil;



@Controller
public class ControllerHotel {
	
	@Autowired
	private AdmRepository repository;
	
	
	@RequestMapping(value = "formHotel", method = RequestMethod.GET)
	public String formHotel() {
		return "hotel/FormHotel";
		
	}
	
	
	//request para salvar o administrador
	@RequestMapping(value = "salvarAdministrador", method = RequestMethod.POST)
	public String salvarAdmin(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {
		
		//verifica se holve erro na validação do objeto		
		if(result.hasErrors()) {
			//envia mensagem de erro via requisição
			attr.addFlashAttribute("mensagemErro", "Verifique os campos");
			return "redirect:formHotel";
			
		}
		
		//verifica se esta sendo feita uma alteração ao invés de uma inserção
				boolean alteracao = admin.getId() != null ? true : false;
				
				//verifica se a senha esta vazia
				if(admin.getSenha().equals(HashUtil.hash256(""))) {
					
				//se nao for alteração, eu defino a primeira parte do email como senha
				if(!alteracao) {
					
				//EXTRAI A PARTE DO EMAIL ANTES DO @
				//substring pega um trecho da String começando pela posição 0, no caso a primeira letra, o index of faz com que o trecho(subString) pegue so ate o @, definido pelo index of
				String parte = admin.getEmail().substring(0, admin.getEmail().indexOf("@"));
				
				//define a senha do admin
				admin.setSenha(parte);
				}else {
				}

				
				}
			
		try {
			//salva o administrador
			repository.save(admin);
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadrstrado com sucesso. ID"+admin.getId());
			return "redirect:formHotel";
			
		}catch(Exception e) {
			attr.addFlashAttribute("mensagemErro", "houve um erro ao cadrastrar o administrador:"+e.getMessage());
			
		}
		return "redirect:formHotel";
		
			
	}
	
	
	
	//request mapping para listar , informando a pagina desejada
	@RequestMapping("listaHotel/{page}")
	public String listar(Model model, @PathVariable("page") int page ) {
		
		//cria um pageable com 6 elementos por pagina, ordenando os objetos
		PageRequest pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC,"nome"));
		
		//cria a pagina atual atravz do repository 
		Page<Administrador> pagina = repository.findAll(pageable);
		
		//descobrir o total de paginas 
		int totalPages = pagina.getTotalPages();
		
		//cria uma lista de inteiros para representar uma pagina
		List<Integer> pageNumbers = new ArrayList<Integer>();
		
		//preencher a lista com as páginas
		for(int i = 0; i < totalPages; i++ ) {
			pageNumbers.add(i+1);
		}
		
		//adiciona as variaveis na model
		model.addAttribute("admins", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);
		
		//retorna para o HTML da lista
		return "hotel/lista";
	}
	@RequestMapping("alterarCadastros")
	public String alterarCadrastro(Model model, Long id) {
		
		//Busca o id do cliente no banco
		Administrador administrador = repository.findById(id).get();
		
		model.addAttribute("administrador", administrador);
		
		//forward manda uma nova requisição(passa novos dados)
		return "forward:formHotel";
	}
	
	@RequestMapping("excluirCads")
	public String excluirCadrastros(Long id) {
		repository.deleteById(id);
		return "redirect:listaHotel/1";
	}
	
	
}

