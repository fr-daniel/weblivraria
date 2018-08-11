package com.weblivraria.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * PageWraooer
 */
public class PageWrapper<T> {

    private Page<T> page;
    private UriComponentsBuilder uBuilder;

    public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest) {
        this.page = page;
        String queryString = httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "";
        String httpUrl = httpServletRequest.getRequestURL().append(queryString).toString().replaceAll("\\+", "%20");
        this.uBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
    }
    
    public List<T> getConteudo() {
        return page.getContent();
    }

    public boolean isVazia() {
        return page.getContent().isEmpty();
    }

    public int getAtual() {
        return page.getNumber();
    }

    public boolean isPrimeira() {
        return page.isFirst();
    }

    public boolean isUltima() {
        return page.isLast();
    }

    public int getTotal() {
        return page.getTotalPages();
    }

    public String urlParaPagina(int pagina) {
        return uBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
    }

}