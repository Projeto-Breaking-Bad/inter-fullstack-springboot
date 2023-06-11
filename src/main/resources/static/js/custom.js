(function () {
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

(function () {
    function counter(input) {
        var counterElement = document.createElement("span");
        counterElement.classList.add("text-muted");
        counterElement.classList.add("small");
        counterElement.classList.add("float-end");
        counterElement.style.pointerEvents = "none";
        counterElement.update = function (input) {
            if (input.selectizeSource) {
                input = input.selectizeSource.$input.get()[0];
            }
            this.textContent = (input.value.length).toLocaleString() + " / " + (input.maxLength).toLocaleString();
        }
        input.addEventListener("keyup", counterElement.update.bind(counterElement, input));
        counterElement.update.bind(counterElement, input)();
        input.parentNode.insertBefore(counterElement, input.nextSibling);
    }
    // CONTADOR DE CHARS NAS TEXTAREAS E NOS INPUTS
    window.addEventListener("load", function () {
        var inputs = document.querySelectorAll("textarea[maxlength],input[maxlength]:not(.selectized)");
        for (var i = 0; i < inputs.length; i++) {
            counter(inputs[i]);
        }
    });
})();

$(document).ready(function () {
    var fonte = 17;

    (function () {
        $('#aumentar').on('click', function () {
            if (fonte < 23) {
                fonte = fonte + 1;
                $('body p, body span').css('font-size', fonte + 'px');
            }
        });

        $('#diminuir').on('click', function () {
            if (fonte > 17) {
                fonte = fonte - 1;
                $('body p, body span').css('font-size', fonte + 'px');
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
var CPF = (function () {
    "use strict";

    function r(r) {
        for (var t = null, n = 0; 9 > n; ++n) t += r.toString().charAt(n) * (10 - n);
        var i = t % 11;
        return (i = 2 > i ? 0 : 11 - i);
    }

    function t(r) {
        for (var t = null, n = 0; 10 > n; ++n) t += r.toString().charAt(n) * (11 - n);
        var i = t % 11;
        return (i = 2 > i ? 0 : 11 - i);
    }

    var n = "<small style='color:red'>CPF Inválido</small>",
        i = "";

    return {
        gera: function () {
            for (var n = "", i = 0; 9 > i; ++i) n += Math.floor(9 * Math.random()) + "";
            var o = r(n),
                a = n + "-" + o + t(n + "" + o);
            return a;
        },

        valida: function (o) {
            for (var a = o.replace(/\D/g, ""), u = a.substring(0, 9), f = a.substring(9, 11), v = 0; 10 > v; v++)
                if ("" + u + f == "" + v + v + v + v + v + v + v + v + v + v + v) return n;
            var c = r(u),
                e = t(u + "" + c);
            return f.toString() === c.toString() + e.toString() ? i : n;
        }
    };
})();

$("#cpf").blur(function () {
    $("#resposta").html(CPF.valida($(this).val()));
});

(function () {
    function validar(documento) {
        function normalizar(documento) {
            return documento.replace(/[^0-9]/g, '');
        }

        function validarCpf(cpf) {
            if (
                !cpf ||
                cpf.length != 11 ||
                cpf == "00000000000" ||
                cpf == "11111111111" ||
                cpf == "22222222222" ||
                cpf == "33333333333" ||
                cpf == "44444444444" ||
                cpf == "55555555555" ||
                cpf == "66666666666" ||
                cpf == "77777777777" ||
                cpf == "88888888888" ||
                cpf == "99999999999"
            )
                return false;
            var soma = 0;
            var resto;
            for (var i = 1; i <= 9; i++)
                soma = soma + parseInt(cpf.substring(i - 1, i)) * (11 - i);
            resto = (soma * 10) % 11;
            if (resto == 10 || resto == 11) resto = 0;
            if (resto != parseInt(cpf.substring(9, 10))) return false;
            soma = 0;
            for (var i = 1; i <= 10; i++)
                soma = soma + parseInt(cpf.substring(i - 1, i)) * (12 - i);
            resto = (soma * 10) % 11;
            if (resto == 10 || resto == 11) resto = 0;
            if (resto != parseInt(cpf.substring(10, 11))) return false;
            return true;
        }

        function validarCnpj(cnpj) {
            if (
                !cnpj ||
                cnpj.length != 14 ||
                cnpj == "00000000000000" ||
                cnpj == "11111111111111" ||
                cnpj == "22222222222222" ||
                cnpj == "33333333333333" ||
                cnpj == "44444444444444" ||
                cnpj == "55555555555555" ||
                cnpj == "66666666666666" ||
                cnpj == "77777777777777" ||
                cnpj == "88888888888888" ||
                cnpj == "99999999999999"
            )
                return false;
            var tamanho = cnpj.length - 2;
            var numeros = cnpj.substring(0, tamanho);
            var digitos = cnpj.substring(tamanho);
            var soma = 0;
            var pos = tamanho - 7;
            for (var i = tamanho; i >= 1; i--) {
                soma += numeros.charAt(tamanho - i) * pos--;
                if (pos < 2) pos = 9;
            }
            var resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
            if (resultado != digitos.charAt(0)) return false;
            tamanho = tamanho + 1;
            numeros = cnpj.substring(0, tamanho);
            soma = 0;
            pos = tamanho - 7;
            for (var i = tamanho; i >= 1; i--) {
                soma += numeros.charAt(tamanho - i) * pos--;
                if (pos < 2) pos = 9;
            }
            resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
            if (resultado != digitos.charAt(1)) return false;
            return true;
        }
        documento = normalizar(documento);
        if (documento.length == 14) {
            return validarCnpj(documento);
        }
        return validarCpf(documento);
    }

    function cpfCnpj(elemento) {
        async function onCpfCnpjChange(event) {
            var documento = this.value;
            if (validar(documento)) {
                document.querySelector('#cnpj-resposta').innerHTML = "";
            } else {
                document.querySelector('#cnpj-resposta').innerHTML =
                    "<small style='color:red'>CNPJ Inválido</small>";
            }
        }
        elemento.addEventListener("change", onCpfCnpjChange);
    }
    cpfCnpj(document.getElementById("cnpj"));
})();