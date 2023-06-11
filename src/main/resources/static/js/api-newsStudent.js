$(document).ready(function () {
    // Fazendo a requisição à API do NewsAPI
    $.ajax({
        url: 'https://newsapi.org/v2/everything?q=curso+gratuito&language=pt&sortBy=publishedAt&apiKey=bce250e04146430b847c90b11eec9fd4',
        method: 'GET',
        success: function (response) {
            // Verificando se a requisição foi bem-sucedida
            if (response.status === 'ok') {
                // Iterando sobre os artigos retornados
                response.articles.forEach(function (article) {
                    // Criando elementos HTML para exibir os dados do artigo
                    var cardElement = $('<div>').addClass('card mb-3');
                    var imageElement = $('<img>').addClass('card-img-top').attr(
                        'src', article.urlToImage);
                    var cardBodyElement = $('<div>').addClass('card-body');
                    var titleElement = $('<h5>').addClass('card-title').text(article
                        .title);
                    var descriptionElement = $('<p>').addClass('card-text').text(
                        article.description);
                    var publishedAtElement = $('<p>').addClass('card-text').append(
                        $('<small>').addClass('text-muted').text(
                            'Published At: ' + article.publishedAt));
                    var urlElement = $('<a>').attr('href', article.url).text(
                        'Read More');

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