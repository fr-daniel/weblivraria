package com.weblivraria.repository.helper.oferta;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.weblivraria.model.Oferta;
import com.weblivraria.repository.filter.OfertaFilter;
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

public class OfertaRepositoryImpl implements OfertaRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Page<Oferta> filtrar(OfertaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Oferta.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<Oferta>(criteria.list(), pageable, total(filtro));
	}

	@Override
	public Page<Oferta> filtrar(Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Oferta.class);

		paginacaoUtil.preparar(criteria, pageable);

		return new PageImpl<Oferta>(criteria.list(), pageable, total(new OfertaFilter()));
	}
	
	public Long total(OfertaFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Oferta.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	public void adicionarFiltro(OfertaFilter filtro, Criteria criteria) {
		criteria.createAlias("livro", "l");

		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getId())) {
				criteria.add(Restrictions.eq("id", filtro.getId()));
			}

			if (filtro.getAtivo() != null) {
				criteria.add(Restrictions.eq("ativo", filtro.getAtivo()));
			}

			if (filtro.getPrecoMin() != null) {
				criteria.add(Restrictions.ge("preco", filtro.getPrecoMin()));
			}
			
			if (filtro.getPrecoMax() != null) {
				criteria.add(Restrictions.le("preco", filtro.getPrecoMax()));
			}

			if (filtro.getEstoqueMin() != null) {
				criteria.add(Restrictions.ge("quantidadeEstoque", filtro.getEstoqueMin()));
			}
			
			if (filtro.getEstoqueMax() != null) {
				criteria.add(Restrictions.le("quantidadeEstoque", filtro.getEstoqueMax()));
			}
						
			if (!StringUtils.isEmpty(filtro.getLivro())) {
				criteria.add(Restrictions.ilike("l.titulo", filtro.getLivro(), MatchMode.ANYWHERE));
			}
		}
	}
	
	@Override
	public List<Oferta> ultimasOfertas(Integer limit) {
		TypedQuery query = manager.createQuery("select o from Oferta o WHERE o.ativo = true ORDER BY o.createTimestamp DESC", Oferta.class);

		query.setMaxResults(limit);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Oferta> filtrar(OfertaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Oferta.class);
		adicionarFiltro(filtro, criteria);	
		return criteria.list();
	}
	
}