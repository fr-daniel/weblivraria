package com.weblivraria.repository.helper.livro;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.weblivraria.model.Livro;
import com.weblivraria.repository.filter.LivroFilter;
import com.weblivraria.repository.helper.paginacao.PaginacaoUtil;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

public class LivroRepositoryImpl implements LivroRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Page<Livro> filtrar(LivroFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Livro.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<Livro>(criteria.list(), pageable, total(filtro));
	}

	@Override
	public Page<Livro> filtrar(Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Livro.class);

		paginacaoUtil.preparar(criteria, pageable);

		return new PageImpl<Livro>(criteria.list(), pageable, total(new LivroFilter()));
	}
	
	public Long total(LivroFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Livro.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	public void adicionarFiltro(LivroFilter filtro, Criteria criteria) {
		criteria.createAlias("editora", "e");

		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getId())) {
				criteria.add(Restrictions.eq("id", filtro.getId()));
			}

			if(!StringUtils.isEmpty(filtro.getTitulo())) {
				criteria.add(Restrictions.ilike("titulo", filtro.getTitulo(), MatchMode.ANYWHERE));
			}

			if(!StringUtils.isEmpty(filtro.getAnoLancamento())) {
				criteria.add(Restrictions.eq("anoLancamento", filtro.getAnoLancamento()));
			}
			
			if(!StringUtils.isEmpty(filtro.getIsbn())) {
				criteria.add(Restrictions.eq("isbn", filtro.getIsbn()));
			}

			if (!StringUtils.isEmpty(filtro.getEditora())) {
				criteria.add(Restrictions.ilike("e.nome", filtro.getEditora(), MatchMode.ANYWHERE));
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Livro> livroSemOferta() {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Livro.class);
		criteria.createAlias("ofertas", "o", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("o.quantidadeEstoque", 0));
		criteria.add(Restrictions.eq("o.ativo", true));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Livro> filtrar(LivroFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Livro.class);
		
		adicionarFiltro(filtro, criteria);
		
		return criteria.list();
	}
	
}