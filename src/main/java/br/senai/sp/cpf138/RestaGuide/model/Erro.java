package br.senai.sp.cpf138.RestaGuide.model;

import lombok.Data;

@Data
public class Erro {

	private int statusCode;
	private String mensagem;
	private String exception;
}
