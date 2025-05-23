package br.com.conversormoedas.conversor;

import br.com.conversormoedas.model.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConversorMoedas {
    private Map<String, Moeda> moedas;
    private String apiKey;
    private final String apiUrlBase = "https://v6.exchangerate-api.com/v6/";
    private String apiUrl;

    public ConversorMoedas() {
        this.moedas = new HashMap<>();
        carregarApiKey();
        if (this.apiKey != null && !this.apiKey.isEmpty()) {
            this.apiUrl = apiUrlBase + this.apiKey + "/latest/USD";
            carregarTaxasDeCambio();
        } else {
            System.err.println("Chave de API não carregada. Funcionalidade de câmbio limitada.");
            // Você pode definir taxas padrão aqui ou desabilitar a funcionalidade de câmbio
            this.moedas.put("EUR", new Euro(1.08));
            this.moedas.put("BRL", new Real(0.20));
            this.moedas.put("GBP", new LibraEstelina(1.33));
        }
        this.moedas.put("USD", new Dolar()); // Dólar sempre terá taxa 1.0
    }

    private void carregarApiKey() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("api_key.properties")) {
            if (input == null) {
                System.err.println("Não foi possível carregar o arquivo api_key.properties");
                return;
            }
            properties.load(input);
            this.apiKey = properties.getProperty("api.key");
            if (this.apiKey == null || this.apiKey.isEmpty()) {
                System.err.println("Chave de API não encontrada no arquivo api_key.properties");
            }
        } catch (IOException ex) {
            System.err.println("Erro ao carregar o arquivo api_key.properties: " + ex.getMessage());
        }
    }

    private void carregarTaxasDeCambio() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        builder.uri(URI.create(apiUrl));
        HttpRequest request = builder.build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                Map<String, Object> jsonResponse = gson.fromJson(response.body(), Map.class);
                Map<String, Double> rates = (Map<String, Double>) jsonResponse.get("conversion_rates");
                if (rates != null) {
                    this.moedas.put("EUR", new Euro(rates.get("EUR")));
                    this.moedas.put("BRL", new Real(rates.get("BRL")));
                    this.moedas.put("GBP", new LibraEstelina(rates.get("GBP")));
                } else {
                    System.err.println("Erro ao obter taxas de câmbio da API (rates ausentes). Usando taxas padrão.");
                    this.moedas.put("EUR", new Euro(1.08));
                    this.moedas.put("BRL", new Real(0.20));
                    this.moedas.put("GBP", new LibraEstelina(1.33));
                }
            } else {
                System.err.println("Erro ao acessar a API de câmbio. Status Code: " + response.statusCode() + ". Usando taxas padrão.");
                this.moedas.put("EUR", new Euro(1.08));
                this.moedas.put("BRL", new Real(0.20));
                this.moedas.put("GBP", new LibraEstelina(1.33));
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao acessar a API de câmbio: " + e.getMessage() + ". Usando taxas padrão.");
            this.moedas.put("EUR", new Euro(1.08));
            this.moedas.put("BRL", new Real(0.20));
            this.moedas.put("GBP", new LibraEstelina(1.33));
        }
    }

    public Moeda encontrarMoeda(String codigo) {
        return moedas.get(codigo.toUpperCase());
    }

    public double converter(double valor, String moedaOrigemCodigo, String moedaDestinoCodigo) {
        Moeda moedaOrigem = encontrarMoeda(String.valueOf(moedaOrigemCodigo));
        Moeda moedaDestino = encontrarMoeda(String.valueOf(moedaDestinoCodigo));

        if (moedaOrigem == null || moedaDestino == null) {
            System.out.println("Moeda de origem ou destino não encontrada.");
            return -1.0; // Indica erro
        }

        // Converter para Dólar (nossa moeda base na API)
        double valorEmDolar = moedaOrigem.converterParaDolar(valor);

        // Converter de Dólar para a moeda de destino
        return moedaDestino.converterDeDolar(valorEmDolar);
    }
}