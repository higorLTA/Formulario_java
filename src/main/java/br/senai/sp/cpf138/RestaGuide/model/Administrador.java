package br.senai.sp.cpf138.RestaGuide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import br.senai.sp.cpf138.RestaGuide.util.HashUtil;
import lombok.Data;


//para gerar o get e o set
@Data

// para mapear como uma entidade JPA
@Entity
public class Administrador {
	
	//torna p id uma chave primaria
	//deixa o id auto incremento
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//fala que o email e senha não pode ser vasio no banco
	@NotEmpty
	private String nome;
	@NotEmpty	
	@Column(unique = true)
	private String email;
	
	@NotEmpty
	private String senha;


	//metodo para setar a senha aplicando o hash
	public void setSenha(String senha) {
		
		//aplica o hash e seta a senha no objeto
		this.senha = HashUtil.hash256(senha);
		
	}
	
	//método para "setar" a senha sem aplicar o hash
	public void setSenhaComHash(String hash) {
	//"seta" o hash na senha
	this.senha = hash;
	}
}
