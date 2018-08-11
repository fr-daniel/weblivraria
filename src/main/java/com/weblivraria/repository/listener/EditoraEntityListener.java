package com.weblivraria.repository.listener;

import javax.persistence.PostLoad;

import com.weblivraria.SistemApplication;
import com.weblivraria.model.Editora;
import com.weblivraria.storage.FotoStorage;

public class EditoraEntityListener {

	@PostLoad
	public void postLoad(final Editora editora) {
		FotoStorage fotoStorage = SistemApplication.getBean(FotoStorage.class);
		if(editora.temFoto()) {
			editora.setUrlFoto(fotoStorage.getUrl(editora.getFotoOuMock()));
			editora.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + editora.getFotoOuMock()));
		} else {
			editora.setUrlFoto("/img/" + editora.getFotoOuMock());
			editora.setUrlThumbnailFoto("/img/thumb." + editora.getFotoOuMock());
		}
	}
	
}
