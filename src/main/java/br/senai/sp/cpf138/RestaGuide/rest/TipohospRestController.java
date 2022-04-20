package br.senai.sp.cpf138.RestaGuide.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import br.senai.sp.cpf138.RestaGuide.annotation.Publico;

import br.senai.sp.cpf138.RestaGuide.model.TipoHotel;
import br.senai.sp.cpf138.RestaGuide.repository.RepositoryCad;

@RequestMapping("/api/hotel")
@RestController
public class TipohospRestController {
	
	@Autowired
	private RepositoryCad  CadRepository;
	
	//Buscando uma outra lista da aplicação 
		@Publico
		@RequestMapping(value="/tipo", method = RequestMethod.GET)
		public Iterable<TipoHotel> getHospCad(){
			return CadRepository.findAll();
		}
		

}
