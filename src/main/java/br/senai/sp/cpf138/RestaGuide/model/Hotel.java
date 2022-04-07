package br.senai.sp.cpf138.RestaGuide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
public class Hotel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nome;
	@NotEmpty
	private String descricao;
	@NotEmpty
	private String cep;
	@NotEmpty
	private String endereco;
	@NotEmpty
	private String cidade;
	private boolean atracao;
	@NotEmpty
	private String telefone;
	private boolean estacionamento;
	@Column(columnDefinition = "TEXT")
	private String foto;
	@ManyToOne
	private TipoHotel tipo;
		

}
