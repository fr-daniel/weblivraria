package com.weblivraria.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

@MappedSuperclass
public abstract class AbstractModelFoto {

    private String foto;

    @Column(name = "content_type")
	private String contentType;

    @Transient
	private boolean novaFoto;

	@Transient
	private String urlFoto;

	@Transient
	private String urlThumbnailFoto;

    public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
    }
    
    public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
    }
    
    public String getFotoOuMock() {
		return !StringUtils.isEmpty(foto) ? foto : "sem-foto.jpg";
	}

	public boolean temFoto() {
		return !StringUtils.isEmpty(this.foto);
	}

	public boolean isNovaFoto() {
		return novaFoto;
	}

	public void setNovaFoto(boolean novaFoto) {
		this.novaFoto = novaFoto;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public String getUrlThumbnailFoto() {
		return urlThumbnailFoto;
	}

	public void setUrlThumbnailFoto(String urlThumbnailFoto) {
		this.urlThumbnailFoto = urlThumbnailFoto;
    }
    
}