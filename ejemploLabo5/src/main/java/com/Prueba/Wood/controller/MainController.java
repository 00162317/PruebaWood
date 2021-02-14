package com.Prueba.Wood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Prueba.Wood.dao.UsuarioDAO;
import com.Prueba.Wood.domain.Usuario;





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
	
	@RequestMapping("/ingresar")
	public ModelAndView ingresar(@ModelAttribute Usuario usuario) {
		ModelAndView mav = new ModelAndView();
		usuarioDAO.insert(usuario);
		mav.addObject("usuario", usuario);
		mav.setViewName("respuesta2");
		return mav;
	}
}
