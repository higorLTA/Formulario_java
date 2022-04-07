package br.senai.sp.cpf138.RestaGuide.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cpf138.RestaGuide.model.Administrador;
import br.senai.sp.cpf138.RestaGuide.model.Hotel;
import br.senai.sp.cpf138.RestaGuide.repository.HotelRepository;
import br.senai.sp.cpf138.RestaGuide.repository.RepositoryCad;
import br.senai.sp.cpf138.RestaGuide.util.HashUtil;


@Controller
public class HotelController {
	
	@Autowired
	private HotelRepository reptipo;
	
	@RequestMapping("formhoteis")
	public String form(Model model) {
		model.addAttribute("tipos", reptipo.findAll());
		return "hotel/Form";
		
	}
	
	@RequestMapping(value = "salvarHosp", method = RequestMethod.POST)
	public String salvarAdmin(Hotel hotel, @RequestParam("fileFotos")MultipartFile[] fileFotos) {
		System.out.println(fileFotos.length);
		
		reptipo.save(hotel);
		return "redirect:formhoteis";
	}
}