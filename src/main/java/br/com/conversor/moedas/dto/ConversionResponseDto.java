package br.com.conversor.moedas.dto;

import java.time.LocalDate;// Para registrar o momento da cotação

public class ConversionResponseDto {
    private String fromCurrency;
    private String toCurrency;
    private double amountToConvert;
    private double conversionRate;
    private double convertedAmount;
    private LocalDate timestamp;// Para sabermos quando a cotação foi obtida

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public double getAmountToConvert() {
        return amountToConvert;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    //construtor
    public ConversionResponseDto(String fromCurrency, String toCurrency, double amountToConvert, double conversionRate,
                                 double convertedAmount, LocalDate timestamp) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amountToConvert = amountToConvert;
        this.conversionRate = conversionRate;
        this.convertedAmount = convertedAmount;
        this.timestamp = timestamp; // Define o timestamp no momento da criação

        // Getters (e Setters se precisar, mas para DTO de resposta, getters costumam bastar)



    }

}
