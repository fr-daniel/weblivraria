package com.weblivraria.repository.helper.editora;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.weblivraria.model.Editora;
import com.weblivraria.repository.filter.EditoraFilter;
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

public class EditoraRepositoryImpl implements EditoraRepositoryQueries{
	
	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Page<Editora> filtrar(EditoraFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Editora.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<Editora>(criteria.list(), pageable, total(filtro));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Page<Editora> filtrar(Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Editora.class);

		paginacaoUtil.preparar(criteria, pageable);

		return new PageImpl<>(criteria.list(), pageable, total(new EditoraFilter()));
	}

	public Long total(EditoraFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Editora.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		System.out.println(criteria.uniqueResult());
		return (Long) criteria.uniqueResult();
	}

	public void adicionarFiltro(EditoraFilter filtro, Criteria criteria) {
		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getId())) {
				criteria.add(Restrictions.eq("id", filtro.getId()));
			}

			if(!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
		}
	}
	
}
