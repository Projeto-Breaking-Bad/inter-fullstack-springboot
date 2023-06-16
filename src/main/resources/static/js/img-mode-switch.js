        // Função para atualizar a imagem de fundo com base nos switches
        function updateBackgroundImage() {
            const darkSwitch = document.getElementById("darkSwitch");
            const contrastSwitch = document.getElementById("contrastSwitch");
            const card = document.getElementById("card");
    
            if (darkSwitch.checked && contrastSwitch.checked) {
                card.style.backgroundImage = "url('/images/card-6-dark.svg')";
            } else if (darkSwitch.checked) {
                card.style.backgroundImage = "url('/images/card-6-dark.svg')";
            } else if (contrastSwitch.checked) {
                card.style.backgroundImage = "url('/images/card-6-dark.svg')";
            } else {
                card.style.backgroundImage = "url('/images/card-6.svg')";
            }
        }
    
        // Evento de carregamento da página
        window.addEventListener("load", function () {
            // Define a imagem de fundo inicial com base no estado dos switches
            updateBackgroundImage();
    
            // Evento de alteração nos switches
            document.getElementById("darkSwitch").addEventListener("change", function () {
                updateBackgroundImage();
            });
    
            document.getElementById("contrastSwitch").addEventListener("change", function () {
                updateBackgroundImage();
            });
        });