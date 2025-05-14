import br.com.conversormoedas.conversor.ConversorMoedas;
import br.com.conversormoedas.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Moeda> moedas = Arrays.asList(
                new Real(0.0),
                new Dolar(),
                new LibraEstelina(0.0),
                new Euro(0.0)


        );

        System.out.println("=== Conversor de Moedas ===");
        System.out.println("Moedas disponiveis:");
        IntStream.range(0, moedas.size()).forEach(i ->
                System.out.println(i + " - " + moedas.get(i))
        );

        int origemIdx;
        while (true) {
            System.out.println("Escolha uma moeda de Origem (número): ");
            if (sc.hasNextInt()) {
                origemIdx = sc.nextInt();
                if (origemIdx >= 0 && origemIdx < moedas.size()) {
                    break;
                } else {
                    System.out.println("Opção inválida. Escolha um número da lista.");
                }
            } else {
                System.out.println("Entrada inválida. Digite um número.");
                sc.next(); // Limpar o scanner
            }
        }

        int destinoIdx;
        while (true) {
            System.out.println("Escolha uma moeda de Destino (número): ");
            if (sc.hasNextInt()) {
                destinoIdx = sc.nextInt();
                if (destinoIdx >= 0 && destinoIdx < moedas.size()) {
                    break;
                } else {
                    System.out.println("Opção inválida. Escolha um número da lista.");
                }
            } else {
                System.out.println("Entrada inválida. Digite um número.");
                sc.next(); // Limpar o scanner
            }
        }

        System.out.println("Escolha valor para conversão:");
        double valor;
        while (true) {
            if (sc.hasNextDouble()) {
                valor = sc.nextDouble();
                break;
            } else {
                System.out.println("Entrada inválida. Digite um valor numérico.");
                sc.next(); // Limpar o scanner
            }
        }

        Moeda origem = moedas.get(origemIdx);
        Moeda destino = moedas.get(destinoIdx);

        ConversorMoedas conversor = new ConversorMoedas();

        try {
            double resultado = conversor.converter(valor, origem.getCodigo(), destino.getCodigo());
            System.out.printf("Resultado: %.2f %s = %.2f %s%n",
                    valor, origem.getCodigo(), resultado, destino.getCodigo());
        } catch (Exception e) {
            System.err.println("Erro na conversão: " + e.getMessage());
        }

        sc.close();
    }
}