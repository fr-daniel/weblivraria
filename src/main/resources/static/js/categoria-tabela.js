Weblivraria.TabelaCategorias = (function() {

    function TabelaCategorias(autocomplete) {
        this.autocomplete = autocomplete;
        this.tabelaCategoriasContainer = $('.js-tabela-categorias-container');
        this.uuid = $('#uuid').val();
    }

    TabelaCategorias.prototype.iniciar = function() {
        console.log("entrei");
        this.autocomplete.on('categoria-selecionada', onCategoriaSelecionada.bind(this));
        onBindTabelaCategoria.call(this);
    }

    function onCategoriaAtualizaServer(html) {
        this.tabelaCategoriasContainer.html(html);
        onBindTabelaCategoria.call(this);
        console.log("passe");
    }

    function onCategoriaSelecionada(evento, categoria) {
        console.log("chamou");
        var resposta = $.ajax({
            url: 'cat/' + this.uuid + '/' + categoria.id,
            method: 'PUT',
            beforeSend: adicionarCsrfToken
        });

        resposta.done(onCategoriaAtualizaServer.bind(this));
    }

    function onExcluirCategoria(event) {
        event.preventDefault();
        var codigo = $(event.currentTarget).data('id-categoria-livro');
        var resposta = $.ajax({
            url: 'cat/' + this.uuid + '/' + codigo,
            method: 'DELETE',
            beforeSend: adicionarCsrfToken
        });

        resposta.done(onCategoriaAtualizaServer.bind(this));       
    }

    function onBindTabelaCategoria() {
        console.log("chamou bind");
        $('.js-btn-excluir-categoria-livro').on('click', onExcluirCategoria.bind(this));
        this.autocomplete.categoriasAdicionadas.clear();
        console.log(this.categoriasAdicionadas);
        $('.js-btn-excluir-categoria-livro').each(function(index, button) {
            let idCategoria = button.getAttribute("data-id-categoria-livro");
            console.log(idCategoria);
        	this.autocomplete.categoriasAdicionadas.add(parseInt(idCategoria));
        }.bind(this));
	}


    function adicionarCsrfToken(xhr) {
		var token = $('input[name=_csrf]').val();
		var header = $('input[name=_csrf_header]').val();
		xhr.setRequestHeader(header, token);
    }
    
    return TabelaCategorias;
})();

$(function(){

    var autocompleteCategoria = new Weblivraria.CategoriaAutocomplete();
    autocompleteCategoria.iniciar();

    var tabelaCategorias = new Weblivraria.TabelaCategorias(autocompleteCategoria);
    tabelaCategorias.iniciar();

});