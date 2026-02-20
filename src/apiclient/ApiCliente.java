package apiclient;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import modelo.RespuestaApi;

public class ApiCliente {

    private final String apiKey;
    private final HttpClient client;
    private final Gson gson;

    public ApiCliente(String apiKey) {
        this.apiKey = apiKey;
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }


    public String obtenerTasas(String baseCode) {
        try {
            String base = baseCode.toUpperCase().trim();
            String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + base;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            int status = response.statusCode();
            if (status != 200) {
                throw new RuntimeException("Error HTTP: " + status + " | Body: " + response.body());
            }

            return response.body();

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar la API: " + e.getMessage(), e);
        }
    }

    public RespuestaApi obtenerDatos(String baseCode) {
        try {
            String json = obtenerTasas(baseCode);

            RespuestaApi datos = gson.fromJson(json, RespuestaApi.class);

            if (datos == null || datos.getResult() == null ||
                    !datos.getResult().equalsIgnoreCase("success")) {
                throw new RuntimeException("La API no devolvió resultado válido.");
            }

            return datos;

        } catch (Exception e) {
            throw new RuntimeException("Error al procesar JSON: " + e.getMessage(), e);
        }
    }
}