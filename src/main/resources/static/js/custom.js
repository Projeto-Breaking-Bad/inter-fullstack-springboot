(function() {
    var linkColor = document.createElement('link');
    linkColor.rel = 'stylesheet';
    linkColor.id = 'dialog-lights';
    linkColor.type = 'text/css';
    linkColor.href = 'https://hiperesp.github.io/HiperDialog/src/css/color/default.css';
    document.head.appendChild(linkColor);

    var linkStyle = document.createElement('link');
    linkStyle.rel = 'stylesheet';
    linkStyle.type = 'text/css';
    linkStyle.href = 'https://hiperesp.github.io/HiperDialog/src/css/style.css';
    document.head.appendChild(linkStyle);

    var scriptCore = document.createElement('script');
    scriptCore.type = 'application/javascript';
    scriptCore.src = 'https://hiperesp.github.io/HiperDialog/src/js/core.js';
    document.head.appendChild(scriptCore);

    var scriptExtra = document.createElement('script');
    scriptExtra.type = 'application/javascript';
    scriptExtra.src = 'https://hiperesp.github.io/HiperDialog/src/js/extra.js';
    document.head.appendChild(scriptExtra);
})();

(function() {
    function counter(input) {
        var counterElement = document.createElement("span");
        counterElement.classList.add("text-muted");
        counterElement.classList.add("small");
        counterElement.classList.add("float-end");
        counterElement.style.pointerEvents = "none";
        counterElement.update = function(input) {
            if(input.selectizeSource) {
                input = input.selectizeSource.$input.get()[0];
            }
            this.textContent = (input.value.length).toLocaleString()+" / "+(input.maxLength).toLocaleString();
        }
        input.addEventListener("keyup", counterElement.update.bind(counterElement, input));
        counterElement.update.bind(counterElement, input)();
        input.parentNode.insertBefore(counterElement, input.nextSibling);
    }
    // CONTADOR DE CHARS NAS TEXTAREAS E NOS INPUTS
    window.addEventListener("load", function() {
        var inputs = document.querySelectorAll("textarea[maxlength],input[maxlength]:not(.selectized)");
        for(var i=0; i<inputs.length; i++) {
            counter(inputs[i]);
        }
    });
})();

$(document).ready(function(){
    var fonte = 17;
    
    (function() {
        $('#aumentar').on('click', function(){
            if(fonte < 23){
                fonte = fonte + 1;
                $('body p, body span').css('font-size', fonte+'px');
            }
        });
        
        $('#diminuir').on('click', function(){
            if(fonte > 17){
                fonte = fonte - 1;
                $('body p, body span').css('font-size', fonte+'px');
            }
        });
    })();
});

(function () {
    function limpa_formulário_cep() {
        // Limpa valores do formulário de cep.
        $("#logradouro").val("");
        $("#bairro").val("");
        $("#cidade").val("");
        $("#uf").val("");
        // $("#ibge").val("");
    }

    //Quando o campo cep perde o foco.
    $("#cep").blur(function () {
        //Nova variável "cep" somente com dígitos.
        var cep = $(this).val().replace(/\D/g, '');

        //Verifica se campo cep possui valor informado.
        if (cep != "") {
            //Expressão regular para validar o CEP.
            var validacep = /^[0-9]{8}$/;

            //Valida o formato do CEP.
            if (validacep.test(cep)) {
                //Preenche os campos com "..." enquanto consulta webservice.
                $("#logradouro").val("...");
                $("#bairro").val("...");
                $("#cidade").val("...");
                $("#uf").val("...");
                // $("#ibge").val("...");

                //Consulta o webservice viacep.com.br/
                $.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?", function (
                    dados) {

                    if (!("erro" in dados)) {
                        //Atualiza os campos com os valores da consulta.
                        $("#logradouro").val(dados.logradouro);
                        $("#bairro").val(dados.bairro);
                        $("#cidade").val(dados.localidade);
                        $("#uf").val(dados.uf);
                        // $("#ibge").val(dados.ibge);
                    } else {
                        //CEP pesquisado não foi encontrado.
                        limpa_formulário_cep();
                        alertDialog("CEP não encontrado.");
                    }
                });
            } else {
                //cep é inválido.
                limpa_formulário_cep();
                alertDialog("Formato de CEP inválido.");
            }
        } else {
            //cep sem valor, limpa formulário.
            limpa_formulário_cep();
        }
    });
})();
