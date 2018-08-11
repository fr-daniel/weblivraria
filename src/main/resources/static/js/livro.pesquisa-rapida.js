var Weblivraria = Weblivraria || {};

Weblivraria.PesquisaRapidaLivros = (function() {

    function PesquisaRapidaLivros() {
        this.pesquisaRapidaLivroModal = $('#pesquisaRapidaLivros');
        this.nomeInput = $('#tituloLivroModal');
        this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-livros-btn');
        console.log(this.pesquisaRapidaBtn);

        this.containerTabelaPesquisa = $('#containerTabelaPesquisaRapidaLivros');
        this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-livro').html();
        this.template = Handlebars.compile(this.htmlTabelaPesquisa);

    }

    PesquisaRapidaLivros.prototype.iniciar = function() {
        this.pesquisaRapidaBtn.on('click', onPesquisaRapidaClicada.bind(this));
        this.pesquisaRapidaLivroModal.on('shown.bs.modal', onModalShow.bind(this));
    }

    function onModalShow() {
        this.nomeInput.focus();
    }
    
    function onPesquisaRapidaClicada(event) {
        event.preventDefault();
        $.ajax({
            url: '/admin/livros',
            method: 'GET',
            contentType: 'application/json',
            data: {
                titulo: this.nomeInput.val()
            },
            success: onPesquisaConcluida.bind(this),
            error: onErroPesquisa
        });
    }

    function onPesquisaConcluida(resultado) {
        $('.js-mensagem-erro').addClass('hidden');

        var html = this.template({lista: resultado});
        this.containerTabelaPesquisa.html(html);

        var tabelaPesquisaRapida = new Weblivraria.TabelaLivroPesquisaRapida(this.pesquisaRapidaLivroModal);
        tabelaPesquisaRapida.iniciar();
    }

    function onErroPesquisa() {
        $('.js-mensagem-erro').removeClass('hidden');
    }

    return PesquisaRapidaLivros;
})();

Weblivraria.TabelaLivroPesquisaRapida = (function() {
    
    function TabelaLivroPesquisaRapida(modal) {
        this.modalEditora = modal;
        this.editora = $('.js-livro-pesquisa-rapida');
    }

    TabelaLivroPesquisaRapida.prototype.iniciar = function() {
        this.editora.on('click', onLivroSelecionado.bind(this));
    }

    function onLivroSelecionado(evento) {
        var livroSelecionado = $(evento.currentTarget);
        this.modalEditora.modal('hide');
        $('#tituloLivroVal').val(livroSelecionado.data('titulo'));
        $('#codigoLivroVal').val(livroSelecionado.data('codigo'));
    }


    return TabelaLivroPesquisaRapida;
})();

$(function() {
    var pesquisaRapidaLivros = new Weblivraria.PesquisaRapidaLivros();
    pesquisaRapidaLivros.iniciar(); 
});
