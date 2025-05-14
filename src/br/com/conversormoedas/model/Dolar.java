package br.com.conversormoedas.model;

public class Dolar extends Moeda {

    public Dolar() {

        super("Dolar", "USD");
    }

    @Override
    public double converterParaDolar(double valor) {
        return valor;
    }

    @Override
    public double converterDeDolar(double valorDolar) {
        return valorDolar;
    }
}
