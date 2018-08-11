package com.weblivraria.repository.listener;

import javax.persistence.PostLoad;

import com.weblivraria.SistemApplication;
import com.weblivraria.model.Autor;
import com.weblivraria.storage.FotoStorage;

public class AutorEntityListener {

	@PostLoad
	public void postLoad(final Autor autor) {
		FotoStorage fotoStorage = SistemApplication.getBean(FotoStorage.class);
		if(autor.temFoto()) {
			autor.setUrlFoto(fotoStorage.getUrl(autor.getFotoOuMock()));
			autor.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + autor.getFotoOuMock()));
		} else {
			autor.setUrlFoto("/img/" + autor.getFotoOuMock());
			autor.setUrlThumbnailFoto("/img/thumb." + autor.getFotoOuMock());
		}
	}
	
}
