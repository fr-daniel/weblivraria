var Weblivraria = Weblivraria || {};

Weblivraria.PesquisaRapidaEditoras = (function() {

    function PesquisaRapidaEditoras() {
        this.pesquisaRapidaEditoraModal = $('#pesquisaRapidaEditoras');
        this.nomeInput = $('#nomeEditoraModal');
        this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-editoras-btn');

        this.containerTabelaPesquisa = $('#containerTabelaPesquisaRapidaEditoras');
        this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-editora').html();
        this.template = Handlebars.compile(this.htmlTabelaPesquisa);

    }

    PesquisaRapidaEditoras.prototype.iniciar = function() {
        this.pesquisaRapidaBtn.on('click', onPesquisaRapidaClicada.bind(this));
        this.pesquisaRapidaEditoraModal.on('shown.bs.modal', onModalShow.bind(this));
    }

    function onModalShow() {
        this.nomeInput.focus();
    }
    
    function onPesquisaRapidaClicada(event) {
        event.preventDefault();
        $.ajax({
            url: '/admin/editoras',
            method: 'GET',
            contentType: 'application/json',
            data: {
                nome: this.nomeInput.val()
            },
            success: onPesquisaConcluida.bind(this),
            error: onErroPesquisa
        });
    }

    function onPesquisaConcluida(resultado) {
        $('.js-mensagem-erro').addClass('hidden');

        var html = this.template({lista: resultado});
        this.containerTabelaPesquisa.html(html);

        var tabelaPesquisaRapida = new Weblivraria.TabelaEditoraPesquisaRapida(this.pesquisaRapidaEditoraModal);
        tabelaPesquisaRapida.iniciar();
    }

    function onErroPesquisa() {
        $('.js-mensagem-erro').removeClass('hidden');
    }

    return PesquisaRapidaEditoras;
})();

Weblivraria.TabelaEditoraPesquisaRapida = (function() {
    
    function TabelaEditoraPesquisaRapida(modal) {
        this.modalEditora = modal;
        this.editora = $('.js-editora-pesquisa-rapida');
    }

    TabelaEditoraPesquisaRapida.prototype.iniciar = function() {
        this.editora.on('click', onEditoraSelecionada.bind(this));
    }

    function onEditoraSelecionada(evento) {
        var editoraSelecionada = $(evento.currentTarget);
        this.modalEditora.modal('hide');
        $('#nomeEditoraVal').val(editoraSelecionada.data('nome'));
        $('#codigoEditoraVal').val(editoraSelecionada.data('codigo'));
    }


    return TabelaEditoraPesquisaRapida;
})();

$(function() {
    var pesquisaRapidaEditoras = new Weblivraria.PesquisaRapidaEditoras();
    pesquisaRapidaEditoras.iniciar(); 
});
