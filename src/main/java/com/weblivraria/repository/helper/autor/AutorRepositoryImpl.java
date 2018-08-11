package com.weblivraria.repository.helper.autor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.weblivraria.dto.AutorDTO;
import com.weblivraria.model.Autor;
import com.weblivraria.repository.filter.AutorFilter;
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

public class AutorRepositoryImpl implements AutorRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Page<Autor> filtrar(AutorFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Autor.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<Autor>(criteria.list(), pageable, total(filtro));
	}

	@Override
	public Page<Autor> filtrar(Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Autor.class);

		paginacaoUtil.preparar(criteria, pageable);

		return new PageImpl<Autor>(criteria.list(), pageable, total(new AutorFilter()));
	}
	
	public Long total(AutorFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Autor.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	public void adicionarFiltro(AutorFilter filtro, Criteria criteria) {
		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getId())) {
				criteria.add(Restrictions.eq("id", filtro.getId()));
			}

			if(!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}

			if(!StringUtils.isEmpty(filtro.getSobrenome())) {
				criteria.add(Restrictions.ilike("sobrenome", filtro.getSobrenome(), MatchMode.ANYWHERE));
			}

			if(!StringUtils.isEmpty(filtro.getPaisOrigem())) {
				criteria.add(Restrictions.ilike("paisOrigem", filtro.getPaisOrigem(), MatchMode.ANYWHERE));
			}
		}
	}
	
	@Override
	public List<AutorDTO> porNomeOuSobrenome(String nomeOuSobrenome) {
		String jpql = "select new com.weblivraria.dto.AutorDTO(id, nome, sobrenome, foto, paisOrigem) "
		+ "from com.weblivraria.model.Autor where lower(nome) like lower(:nomeOuSobrenome) or lower(sobrenome) like lower(:nomeOuSobrenome)";

		List<AutorDTO> autoresFiltrados = manager.createQuery(jpql, AutorDTO.class)
			.setParameter("nomeOuSobrenome", "%" + nomeOuSobrenome + "%")
			.getResultList();

		return autoresFiltrados;
	}

}