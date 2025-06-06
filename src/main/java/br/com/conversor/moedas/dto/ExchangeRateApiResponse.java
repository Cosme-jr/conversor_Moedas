package br.com.conversor.moedas.dto;

import java.util.Map;

public class ExchangeRateApiResponse {

    private String result;
    private Map<String, Double> conversion_rates;

    // Getters necessários para o framework desserializar a resposta JSON
    public String getResult() {
        return result;
    }

    public Map<String, Double> getConversion_rates() {
        return conversion_rates;
    }

    // Setters (opcionais, mas boa prática)
    public void setResult(String result) {
        this.result = result;
    }

    public void setConversion_rates(Map<String, Double> conversion_rates) {
        this.conversion_rates = conversion_rates;
    }
}
