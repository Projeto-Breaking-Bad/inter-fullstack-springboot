$(document).ready(function () {
    var protocol = window.location.protocol; // Obtém o protocolo da página (http ou https)

    // Define a URL base da API com base no protocolo da página
    var apiBaseUrl = protocol === 'https:' ? 'https://newsapi.org:8443' : 'http://newsapi.org:8443';

    // Concatena a URL base com o restante da URL da API
    var apiUrl = apiBaseUrl + '/v2/everything?q=curso+gratuito&language=pt&sortBy=publishedAt&apiKey=bce250e04146430b847c90b11eec9fd4';

    // Fazendo a requisição à API do NewsAPI
    $.ajax({
        url: apiUrl,
        method: 'GET',
        success: function (response) {
            // Verificando se a requisição foi bem-sucedida
            if (response.status === 'ok') {
                // Iterando sobre os artigos retornados
                response.articles.forEach(function (article) {
                    // Criando elementos HTML para exibir os dados do artigo
                    var cardElement = $('<div>').addClass('card mb-3');
                    var imageElement = $('<img>').addClass('card-img-top').attr('src', article.urlToImage);
                    var cardBodyElement = $('<div>').addClass('card-body');
                    var titleElement = $('<h5>').addClass('card-title').text(article.title);
                    var descriptionElement = $('<p>').addClass('card-text').text(article.description);
                    var publishedAtElement = $('<p>').addClass('card-text').append($('<small>').addClass('text-muted').text('Publicado em: ' + article.publishedAt));
                    var urlElement = $('<a>').attr('href', article.url).text('Consulte Mais informação');

                    // Adicionando os elementos ao card
                    cardBodyElement.append(titleElement);
                    cardBodyElement.append(descriptionElement);
                    cardBodyElement.append(publishedAtElement);
                    cardBodyElement.append(urlElement);
                    cardElement.append(imageElement);
                    cardElement.append(cardBodyElement);

                    // Adicionando o card ao corpo do documento
                    $('#news').append(cardElement);
                });
            } else {
                console.log('Falha na requisição à API do NewsAPI');
            }
        },
        error: function (xhr, status, error) {
            console.log('Erro na requisição à API do NewsAPI');
        }
    });
});