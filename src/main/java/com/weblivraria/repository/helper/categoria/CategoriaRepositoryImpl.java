package com.weblivraria.repository.helper.categoria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.weblivraria.dto.CategoriaDTO;
import com.weblivraria.model.Categoria;
import com.weblivraria.repository.filter.CategoriaFilter;
import com.weblivraria.repository.helper.paginacao.PaginacaoUtil;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

public class CategoriaRepositoryImpl implements CategoriaRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public Page<Categoria> filtrar(CategoriaFilter categoriaFilter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Categoria.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(categoriaFilter, criteria);
		
		return new PageImpl<Categoria>(criteria.list(), pageable, total(categoriaFilter));
	}

	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public Page<Categoria> filtrar(Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Categoria.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		return new PageImpl<Categoria>(criteria.list(), pageable, total());
	}

	public Long total(CategoriaFilter categoriaFilter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Categoria.class);
		adicionarFiltro(categoriaFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	public Long total() {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Categoria.class);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	public void adicionarFiltro(CategoriaFilter categoriaFilter, Criteria criteria) {
		if(categoriaFilter != null) {		
			if(!StringUtils.isEmpty(categoriaFilter.getTitulo()))
				criteria.add(Restrictions.ilike("titulo", categoriaFilter.getTitulo(), MatchMode.ANYWHERE));
		}
	}
	
	@Override
	public List<CategoriaDTO> porTitulo(String titulo) {
		String jpql = "select new com.weblivraria.dto.CategoriaDTO(id, titulo, idUrl) "
		+ "from com.weblivraria.model.Categoria where lower(titulo) like lower(:titulo2)";

		List<CategoriaDTO> categoriasFiltradas = manager.createQuery(jpql, CategoriaDTO.class)
			.setParameter("titulo2", "%" + titulo + "%")
			.getResultList();

		return categoriasFiltradas;
	}

}
