package com.Prueba.Wood.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.Prueba.Wood.domain.Usuario;


public interface UsuarioDAO {
	
	public List<Usuario>findAll() throws DataAccessException;
	
	public void insert(Usuario usuario) throws DataAccessException;
}
