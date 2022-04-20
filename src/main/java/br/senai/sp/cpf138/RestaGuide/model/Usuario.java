package br.senai.sp.cpf138.RestaGuide.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.senai.sp.cpf138.RestaGuide.util.HashUtil;

public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(unique = true)
	private String email;
	private String senha;
	
	public void setSenha(String senha) {
		this.senha =HashUtil.hash256(senha);
	}

}
