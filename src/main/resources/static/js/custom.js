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