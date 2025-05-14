package br.com.conversormoedas.model;

public abstract class Moeda {
    private String nome;
    private String codigo;

    public Moeda(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public abstract double converterParaDolar(double valor);
    public abstract double converterDeDolar(double valorDolar);

    @Override
    public String toString() {
        return nome + " (" + codigo + ")";
    }
}