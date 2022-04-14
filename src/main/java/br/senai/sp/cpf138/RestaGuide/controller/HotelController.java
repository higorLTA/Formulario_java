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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cpf138.RestaGuide.model.Administrador;
import br.senai.sp.cpf138.RestaGuide.model.Hotel;
import br.senai.sp.cpf138.RestaGuide.model.TipoHotel;
import br.senai.sp.cpf138.RestaGuide.repository.HotelRepository;
import br.senai.sp.cpf138.RestaGuide.repository.RepositoryCad;
import br.senai.sp.cpf138.RestaGuide.util.FirebaseUtil;
import br.senai.sp.cpf138.RestaGuide.util.HashUtil;


@Controller
public class HotelController {
	
	@Autowired
	private RepositoryCad repTipo;
	
	@Autowired
	private HotelRepository repHosp;
	
	@Autowired
	private FirebaseUtil firebaseUtil;
	
	
	@RequestMapping("formHospdagens")
	public String form(Model model) {
		model.addAttribute("tipos", repHosp.findAll());
		return "hotel/Form";
		
	}
	
	@RequestMapping(value = "salvarHosp", method = RequestMethod.POST)
	public String salvarAdmin(Hotel hotel, @RequestParam("fileFotos")MultipartFile[] fileFotos) {
		System.out.println(fileFotos.length);
		
		//string para a url das fotos
		String fotos = hotel.getFoto();
		//percorrer cada arquivo que foi submetido no formulario
		for(MultipartFile arquivo : fileFotos) {
			//verificar se oarquivo esta vazio
			if(arquivo.getOriginalFilename().isEmpty()) {
				//vai para o proximo arquivo
				continue;				
			}
			//faz o upload para a nuvem e obtém a url gerada
			try {
				fotos += firebaseUtil.uploadFile(arquivo)+";";
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}			
		}	
		hotel.setFoto(fotos);
		
		repHosp.save(hotel);
		return "redirect:listaHosp/1";
	}
	
	@RequestMapping("listaHosp/{page}")
	public String listar(Model model, @PathVariable("page") int page ) {
		
				//cria um pageable com 6 elementos por pagina, ordenando os objetos
				PageRequest pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC,"nome"));
				
				//cria a pagina atual atravez do repository 
				Page<Hotel> pagina = repHosp.findAll(pageable);
				
				//descobrir o total de paginas 
				int totalPages = pagina.getTotalPages();
				
				//cria uma lista de inteiros para representar uma pagina
				List<Integer> pageNumbers = new ArrayList<Integer>();
				
				//preencher a lista com as páginas
				for(int i = 0; i < totalPages; i++ ) {
					pageNumbers.add(i+1);
				}
				
				//adiciona as variaveis na model
				model.addAttribute("hospedagem", pagina.getContent());
				model.addAttribute("paginaAtual", page);
				model.addAttribute("totalPaginas", totalPages);
				model.addAttribute("numPaginas", pageNumbers);
				
				//retorna para o HTML da lista
				return "hotel/ListaHosp";
			}
	
	@RequestMapping("alterarHosp")
	public String alterarCadrastro(Model model, Long id) {
		
		Hotel hotel = repHosp.findById(id).get();
		
		model.addAttribute("Hotel", hotel);
		
		return "forward:formHospdagens";
		
	}
	
	@RequestMapping("excluirHosp")
	public String excluirCadrastros(Long id) {
		Hotel hotel = repHosp.findById(id).get();
		if(hotel.getFoto().length() > 0) {
			for(String foto : hotel.verFotos()) {
				firebaseUtil.deletar(foto);
			}
		}
		repHosp.deleteById(id);
		return "redirect:listaHosp/1";
	}
	
	@RequestMapping("excluirFotoHosp")
	public String excluirFoto(Long idHotel, int numFoto, Model model) {
		// busca a hospedagem no banco
		Hotel hotel = repHosp.findById(idHotel).get();
		// pega a String da foto a ser excluida
		String fotoUrl = hotel.verFotos()[numFoto];
		// excluir do firebase
		firebaseUtil.deletar(fotoUrl);
		// "arranca" a foto da string fotos
		hotel.setFoto(hotel.getFoto().replace(fotoUrl+";",""));
		// salva no BD o objeto rest
		repHosp.save(hotel);
		//adciona o hotel da model
		model.addAttribute("Hotel", hotel);
		//encaminhar para o form

		return "forward:formHospdagens";	
		
	}
}