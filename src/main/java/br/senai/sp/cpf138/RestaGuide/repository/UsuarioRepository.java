package br.senai.sp.cpf138.RestaGuide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cpf138.RestaGuide.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {
	

}
