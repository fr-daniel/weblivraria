package com.weblivraria.session;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.weblivraria.model.Autor;

class TabelaAutoresLivro {
    
    private String uuid;
    private List<Autor> autores = new ArrayList<>();
    
    public TabelaAutoresLivro(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
        return uuid;
    }

    public void adicionarAutor(Autor autor) {
        autores.add(autor);
    }

    public void excluirAutor(Autor autor) {
        int indice = IntStream.range(0, autores.size())
            .filter(i -> autores.get(i).equals(autor))
            .findAny().getAsInt();
        autores.remove(indice);
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public int total() {
        return this.autores.size();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TabelaAutoresLivro other = (TabelaAutoresLivro) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
    
    

}