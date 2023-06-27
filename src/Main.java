import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://imdb-api.com/en/API/MostPopularMovies/k_2wikxsgk";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados
        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println();
            System.out.println("Titulo:" + pintaCaracterer(filme.get("title")));
            System.out.println("Poster: " + filme.get("image"));
            String imDbRating = filme.get("imDbRating");

            System.out.print("Classificação: ");

            if (imDbRating != null && !imDbRating.equals("")) {
                double imDbRatingDouble = Double.parseDouble(imDbRating);
                int imDbRatingInt = (int) imDbRatingDouble;

                for (int i = 0; i < imDbRatingInt; i++) {
                    System.out.print(starEmoji());
                }
            }

            System.out.println();
        }
    }

    public static String pintaCaracterer(String frase) {
        return "\u001b[37;1m \u001b[44;1m" + frase + "\u001b[m";
    }

    public static String starEmoji() {
        return "⭐";
    }
}