package br.com.conversormoedas.model;

public class Real extends Moeda {
    private double taxaCambioParaDolar;

    public Real(double taxaCambioParaDolar) {
        super("Real", "BRL");
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