package br.com.conversormoedas.model;

public class LibraEstelina extends Moeda {
    private double taxaCambioParaDolar;

    public LibraEstelina(double taxaCambioParaDolar) {
        super("LibraEstelina","GBP");
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
