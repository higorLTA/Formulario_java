package br.senai.sp.cpf138.RestaGuide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cpf138.RestaGuide.model.Hotel;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long>{
	public List<Hotel> findByTipoId(Long idTipo);
	public List<Hotel> findByEstacionamento(boolean estacionamento);
	public List<Hotel> findByAtracao(boolean atracao);

}
