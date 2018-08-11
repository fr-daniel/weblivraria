Weblivraria = Weblivraria || {};

Weblivraria.Autocomplete = (function(){

    function Autocomplete() {
        this.nomeOrSobrenomeInput = $('#nomeOrSobrenomeAutorInput');
        this.autoresAdicionados = new Set();
        this.emitter = $({});
        
        $(".btn-rm-autor").each(function(index, button) {
        	let idAutor = button.getAttribute("data-autor-id");
        	this.autoresAdicionados.add(parseInt(idAutor));
        }.bind(this));
                
        this.on = this.emitter.on.bind(this.emitter);
    }

    Autocomplete.prototype.iniciar = function() {
        var options = {
            url: nomeOuSobrenome => '/admin/autores?nomeOuSobrenome=' + nomeOuSobrenome,
            getValue: 'nomeCompleto',
            theme: "bootstrap",
            minCharNumber: 2,
            requestDelay: 300,
            ajaxSettings: {
                contentType: 'application/json'
            },
            template: {
                type: 'custom',
                method: function(nomeCompleto, autor) {
                    if(!this.autoresAdicionados.has(autor.id)){
                        var foto = autor.foto ? "/fotos/thumb/" + autor.foto : '/img/thumb.sem-foto.jpg'
                        return "<img src='" + foto + "' /> " + nomeCompleto;
                    }
                }.bind(this)
            },
            list: {
                onChooseEvent: function() {
                    let autorData = this.nomeOrSobrenomeInput.getSelectedItemData();
                    this.emitter.trigger('autor-selecionado', autorData);
                    this.nomeOrSobrenomeInput.val('');
                }.bind(this)
            }
        };

        this.nomeOrSobrenomeInput.easyAutocomplete(options);
        $(".easy-autocomplete").removeAttr("style");
    }

    return Autocomplete;
})();