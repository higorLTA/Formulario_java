package br.senai.sp.cpf138.RestaGuide.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class indexController {

	@RequestMapping(value = "indexHosp", method = RequestMethod.GET)
	public String formcadastro() {
		return "index";
	}
}
