package com.Prueba.Wood.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.Prueba.Wood.domain.Usuario;


@Repository
public class UsuarioDaoImpl implements UsuarioDAO {

	
	@PersistenceContext(unitName="Wood")
	private EntityManager entityManager;
	
	@Override
	public List<Usuario> findAll() throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("select * from public.USUARIO");
		
		Query query = entityManager.createNativeQuery(sb.toString(), Usuario.class);
		List<Usuario> resulset = query.getResultList();
		return resulset;
		
	}

	@Override
	@Transactional
	public void insert(Usuario usuario) throws DataAccessException {
		// TODO Auto-generated method stub
		entityManager.persist(usuario);
		
	}

}
