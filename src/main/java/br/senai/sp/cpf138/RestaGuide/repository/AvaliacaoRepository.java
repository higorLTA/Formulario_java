package br.senai.sp.cpf138.RestaGuide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cpf138.RestaGuide.model.Avaliacao;
import br.senai.sp.cpf138.RestaGuide.model.Hotel;


public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long>{

	public List<Avaliacao> findByHotelId(Long idhotel);
}
