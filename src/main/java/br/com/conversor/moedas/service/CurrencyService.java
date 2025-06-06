package br.com.conversor.moedas.service;

import br.com.conversor.moedas.dto.ConversionResponseDto;
import br.com.conversor.moedas.dto.ExchangeRateApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService {

    @Value("${exchangerate.api.key}")
    private String apiKey;

    @Value("${exchangerate.api.url}")
    private String apiUrlBase;

    // A ÚNICA dependência que o serviço precisa
    private final RestTemplate restTemplate;

    private static final List<String> ALLOWED_CURRENCIES = Arrays.asList("USD", "EUR", "BRL");

    // ESTE É O CONSTRUTOR CORRETO
    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // O restante da classe permanece o mesmo...
    public ExchangeRateApiResponse getRates(String baseCurrency) {
        String url = apiUrlBase + apiKey + "/latest/" + baseCurrency;
        try {
            ExchangeRateApiResponse response = restTemplate.getForObject(url, ExchangeRateApiResponse.class);
            if (response == null || !"success".equals(response.getResult())) {
                throw new RuntimeException("Falha ao obter cotações. A API externa não retornou sucesso.");
            }
            return response;
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Moeda base '" + baseCurrency + "' inválida ou não encontrada na API externa.", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar com a API de cotações.", e);
        }
    }

    public ConversionResponseDto convertCurrency(String fromCurrency, String toCurrency, double amount) {
        fromCurrency = fromCurrency.toUpperCase();
        toCurrency = toCurrency.toUpperCase();

        if (!ALLOWED_CURRENCIES.contains(fromCurrency) || !ALLOWED_CURRENCIES.contains(toCurrency)) {
            throw new IllegalArgumentException("Moeda inválida. Permitidas: USD, EUR, BRL.");
        }
        if (fromCurrency.equals(toCurrency)) {
            throw new IllegalArgumentException("A moeda de origem e destino não podem ser iguais.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("O valor a ser convertido deve ser maior que zero.");
        }

        ExchangeRateApiResponse apiResponse = getRates(fromCurrency);
        Map<String, Double> rates = apiResponse.getConversion_rates();

        Double targetRate = rates.get(toCurrency);
        if (targetRate == null) {
            throw new RuntimeException("Não foi possível obter a taxa de conversão para " + toCurrency);
        }

        double convertedAmount = amount * targetRate;

        return new ConversionResponseDto(fromCurrency, toCurrency, amount, targetRate, convertedAmount, LocalDate.now());
    }
}