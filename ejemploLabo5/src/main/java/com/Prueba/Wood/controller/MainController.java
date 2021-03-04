package com.Prueba.Wood.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Prueba.Wood.dao.StripeService;
import com.Prueba.Wood.dao.UsuarioDAO;
import com.Prueba.Wood.domain.ChargeRequest;
import com.Prueba.Wood.domain.ChargeRequest.Currency;
import com.Prueba.Wood.domain.Usuario;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;





@Controller
@Service
public class MainController {
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	Usuario e = new Usuario();
	
	
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}
	
	//-------------------------------------------------------------------------------------------
	
	/*
	Cuenta de STRIPE: Correo: 00162317@uca.edu.sv
	Contraseña: xiaomiroberto20-
	
	LINKS VERGONES
	https://www.baeldung.com/java-stripe-api
	https://github.com/stripe/elements-examples
	https://stripe.com/docs/stripe-js*/
	
	@RequestMapping("/form")
	public ModelAndView form() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("form");
		return mav;
	}

	@Autowired
    private StripeService paymentsService;

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model)
      throws StripeException, InvalidAlgorithmParameterException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(Currency.EUR);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
	//-------------------------------------------------------------------------------------------
	
	
	@RequestMapping("/respuesta1")
	public ModelAndView respuesta1() {
		ModelAndView mav = new ModelAndView();

		List<Usuario> usuarios = null;
		try {
			usuarios = usuarioDAO.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("usuarios", usuarios);

		System.out.print(e.getNombre());
		mav.setViewName("respuesta1");
		return mav;
	}
	
	@RequestMapping("/respuesta2")
	public ModelAndView respuesta2(@ModelAttribute Usuario usuario) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("respuesta2");
		return mav;
	}
	
/*	@RequestMapping("/ingresar")
	public ModelAndView ingresar(@ModelAttribute Usuario usuario) {
		ModelAndView mav = new ModelAndView();
		usuarioDAO.insert(usuario);
		mav.addObject("usuario", usuario);
		mav.setViewName("respuesta2");
		return mav;
	}*/
	
	
	@PostMapping("/ingresar")
	public String uploadFile(@RequestParam("file") MultipartFile file, @ModelAttribute Usuario usuario, RedirectAttributes attributes)
			throws IOException {

		if (file == null || file.isEmpty()) {
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo");
			return "redirect:/index";
		}

		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.dir"));
		builder.append(File.separator);
		builder.append("src");
		builder.append(File.separator);
		builder.append(file.getOriginalFilename());
		
		System.out.println(builder);

		byte[] fileBytes = file.getBytes();
		Path path = Paths.get(builder.toString());
		Files.write(path, fileBytes);
		usuario.setImg(file.getOriginalFilename().toString());
		usuarioDAO.insert(usuario);
		
		
		attributes.addFlashAttribute("message", "Archivo cargado correctamente ["+builder.toString()+"]");

		return "redirect:/status";
	}
	@GetMapping("/status")
	public String status() {
		return "status";
	}
	
	
}
