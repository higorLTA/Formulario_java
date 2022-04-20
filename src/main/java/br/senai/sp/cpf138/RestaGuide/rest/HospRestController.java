package br.senai.sp.cpf138.RestaGuide.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cpf138.RestaGuide.annotation.Publico;
import br.senai.sp.cpf138.RestaGuide.model.Hotel;
import br.senai.sp.cpf138.RestaGuide.repository.HotelRepository;
import br.senai.sp.cpf138.RestaGuide.repository.RepositoryCad;

@RequestMapping("/api/hotel")
@RestController
public class HospRestController {

	@Autowired
	private HotelRepository repository;
	
	@Publico
	@RequestMapping(value="", method = RequestMethod.GET)
	public Iterable<Hotel> getHospedagens(){
		return repository.findAll();
	}
	
	//Metodo para buscar a lista pelo id 
	@Publico
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Hotel> findHospedagem(@PathVariable("id") Long idHospedagem ){
		//busca a hospedagem
		Optional<Hotel> hospedagem = repository.findById(idHospedagem);
		if(hospedagem.isPresent()) {
			return ResponseEntity.ok(hospedagem.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@RequestMapping(value = "/tipo/{idTipo}", method = RequestMethod.GET )
	public Iterable<Hotel> getTipoHospedagem(@PathVariable("idTipo") Long idTipo){
		
	return repository.findByTipoId(idTipo);
	}
	
	@RequestMapping(value = "/boolean/{estacionamento}", method = RequestMethod.GET)
	public Iterable<Hotel> getEstacionamento(@PathVariable("estacionamento") boolean estacionamento){
		
		return repository.findByEstacionamento(estacionamento);
	}
	
	@RequestMapping(value = "/boolean/{atracao}", method = RequestMethod.GET)
	public Iterable<Hotel> getAtracao(@PathVariable("atracao") boolean atracao){
		
		return repository.findByAtracao(atracao);
	}
	
	
}
