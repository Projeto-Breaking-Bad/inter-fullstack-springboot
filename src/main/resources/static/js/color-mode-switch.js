// Função para inicializar o tema
function initTheme() {
    var darkThemeSelected = localStorage.getItem("darkSwitch") !== null && localStorage.getItem("darkSwitch") === "dark";
    var contrastThemeSelected = localStorage.getItem("contrastSwitch") !== null && localStorage.getItem("contrastSwitch") === "contrast";

    if (darkThemeSelected) {
        document.body.setAttribute("data-theme", "dark");
        document.getElementById("darkSwitch").checked = true;
        document.getElementById("contrastSwitch").checked = false;
    } else if (contrastThemeSelected) {
        document.body.setAttribute("data-theme", "contrast");
        document.getElementById("darkSwitch").checked = false;
        document.getElementById("contrastSwitch").checked = true;
    } else {
        document.body.removeAttribute("data-theme");
        document.getElementById("darkSwitch").checked = false;
        document.getElementById("contrastSwitch").checked = false;
    }
}

// Função para resetar o tema
function resetTheme() {
    if (document.getElementById("darkSwitch").checked) {
        document.body.setAttribute("data-theme", "dark");
        localStorage.setItem("darkSwitch", "dark");
        document.getElementById("contrastSwitch").checked = false;
        localStorage.removeItem("contrastSwitch");
    } else if (document.getElementById("contrastSwitch").checked) {
        document.body.setAttribute("data-theme", "contrast");
        localStorage.setItem("contrastSwitch", "contrast");
        document.getElementById("darkSwitch").checked = false;
        localStorage.removeItem("darkSwitch");
    } else {
        document.body.removeAttribute("data-theme");
        localStorage.removeItem("darkSwitch");
        localStorage.removeItem("contrastSwitch");
    }
}

// Evento de carregamento da página
window.addEventListener("load", function () {
    initTheme();

    document.getElementById("darkSwitch").addEventListener("change", function () {
        resetTheme();
    });

    document.getElementById("contrastSwitch").addEventListener("change", function () {
        resetTheme();
    });
    
    document.getElementById("darkSwitch").addEventListener("click", function () {
        if (this.checked) {
            document.getElementById("contrastSwitch").checked = false;
        }
    });

    document.getElementById("contrastSwitch").addEventListener("click", function () {
        if (this.checked) {
            document.getElementById("darkSwitch").checked = false;
        }
    });
});
