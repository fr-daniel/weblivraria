Weblivraria.TabelaAutores = (function() {

    function TabelaAutores(autocomplete) {
        this.autocomplete = autocomplete;
        this.tabelaAutoresContainer = $('.js-tabela-autores-container');
        this.buttonExcluirAutorLivro = $('.js-btn-excluir-autor-livro');
        this.uuid = $('#uuid').val();
    }

    TabelaAutores.prototype.iniciar = function() {
        this.autocomplete.on('autor-selecionado', onAutorSelecionado.bind(this));
        this.buttonExcluirAutorLivro.on('click', onExcluirAutorLivro);
    }

    function onAutorAtualizadoServer(html) {
        this.tabelaAutoresContainer.html(html);
        $('.js-btn-excluir-autor-livro').on('click', onExcluirAutorLivro.bind(this));
    }

    function onAutorSelecionado(evento, autor) {
        var resposta = $.ajax({
            url: 'autor/' + this.uuid + '/' + autor.id,
            method: 'PUT',
            beforeSend: adicionarCsrfToken
        });

        resposta.done(onAutorAtualizadoServer.bind(this));
    }

    function onExcluirAutorLivro(event) {
        event.preventDefault();
        var codigo = $(event.currentTarget).data('id-autor-livro');
        var resposta = $.ajax({
            url: 'autor/' + this.uuid + '/' + codigo,
            method: 'DELETE',
            beforeSend: adicionarCsrfToken
        });

        resposta.done(onAutorAtualizadoServer.bind(this));       
    }

    function adicionarCsrfToken(xhr) {
		var token = $('input[name=_csrf]').val();
		var header = $('input[name=_csrf_header]').val();
		xhr.setRequestHeader(header, token);
    }
    
    return TabelaAutores;
})();

$(function(){

    var autocomplete = new Weblivraria.Autocomplete();
    autocomplete.iniciar();

    var tabelasAutores = new Weblivraria.TabelaAutores(autocomplete);
    tabelasAutores.iniciar();

});