package com.weblivraria.session;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.weblivraria.model.Categoria;

class TabelaCategoriasLivro {
    
    private String uuid;
    private List<Categoria> categorias = new ArrayList<>();
    
    public TabelaCategoriasLivro(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
        return uuid;
    }

    public void adicionarCategoria(Categoria categoria) {
        categorias.add(categoria);
    }

    public void excluirCategoria(Categoria categoria) {
        int indice = IntStream.range(0, categorias.size())
            .filter(i -> categorias.get(i).equals(categoria))
            .findAny().getAsInt();
        categorias.remove(indice);
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public int total() {
        return this.categorias.size();
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
		TabelaCategoriasLivro other = (TabelaCategoriasLivro) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
    
    

}