package br.com.conversormoedas.model;

public class Euro extends Moeda {
    private double taxaCambioParaDolar;

    public Euro(double taxaCambioParaDolar) {
        super("Euro", "EUR");
        this.taxaCambioParaDolar = taxaCambioParaDolar;
    }

    public double getTaxaCambioParaDolar() {
        return taxaCambioParaDolar;
    }

    @Override
    public double converterParaDolar(double valor) {
        return valor / taxaCambioParaDolar;
    }

    @Override
    public double converterDeDolar(double valorDolar) {
        return valorDolar * taxaCambioParaDolar;
    }
}