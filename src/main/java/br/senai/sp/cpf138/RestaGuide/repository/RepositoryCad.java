package br.senai.sp.cpf138.RestaGuide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.senai.sp.cpf138.RestaGuide.model.Hotel;
import br.senai.sp.cpf138.RestaGuide.model.TipoHotel;



public interface RepositoryCad extends PagingAndSortingRepository<TipoHotel, Long>{

		
		@Query("SELECT h FROM TipoHotel h WHERE h.palavraChave LIKE %:p%")
		public List<TipoHotel> procurarPalavraChave(@Param("p") String palavraChave);
		
}
