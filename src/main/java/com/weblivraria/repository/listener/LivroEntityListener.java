package com.weblivraria.repository.listener;

import javax.persistence.PostLoad;

import com.weblivraria.SistemApplication;
import com.weblivraria.model.Livro;
import com.weblivraria.storage.FotoStorage;

public class LivroEntityListener {

	@PostLoad
	public void postLoad(final Livro livro) {
		FotoStorage fotoStorage = SistemApplication.getBean(FotoStorage.class);
		if(livro.temFoto()) {
			livro.setUrlFoto(fotoStorage.getUrl(livro.getFotoOuMock()));
			livro.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + livro.getFotoOuMock()));
		} else {
			livro.setUrlFoto("/img/" + livro.getFotoOuMock());
			livro.setUrlThumbnailFoto("/img/thumb." + livro.getFotoOuMock());
		}
	}
	
}
