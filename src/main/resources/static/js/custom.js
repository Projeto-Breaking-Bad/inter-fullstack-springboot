/** HiperDialog */
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