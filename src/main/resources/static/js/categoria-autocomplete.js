Weblivraria = Weblivraria || {};

Weblivraria.CategoriaAutocomplete = (function(){

    function CategoriaAutocomplete() {
        this.categoriaInput = $('#tituloCategoriaInput');
        this.categoriasAdicionadas = new Set();
        this.emitter = $({});
                
        this.on = this.emitter.on.bind(this.emitter);
    }

    CategoriaAutocomplete.prototype.iniciar = function() {
        var options = {
            url: titulo => '/admin/categorias?titulo=' + titulo,
            getValue: 'titulo',
            theme: "bootstrap",
            minCharNumber: 2,
            requestDelay: 300,
            ajaxSettings: {
                contentType: 'application/json'
            },
            template: {
                type: 'custom',
                method: function(titulo, categoria) {
                    if(!this.categoriasAdicionadas.has(categoria.id)){
                        return titulo;
                    }
                }.bind(this)
            },
            list: {
                onChooseEvent: function() {
                    let categoriaData = this.categoriaInput.getSelectedItemData();
                    this.emitter.trigger('categoria-selecionada', categoriaData);
                    this.categoriaInput.val('');
                }.bind(this)
            }
        };

        this.categoriaInput.easyAutocomplete(options);
        $(".easy-autocomplete").removeAttr("style");
    }

    return CategoriaAutocomplete;
})();