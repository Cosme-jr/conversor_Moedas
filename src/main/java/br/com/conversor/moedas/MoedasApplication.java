package br.com.conversor.moedas;

import br.com.conversor.moedas.dto.ConversionResponseDto;
import br.com.conversor.moedas.service.CurrencyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.InputMismatchException;
import java.util.Scanner;

@SpringBootApplication
public class MoedasApplication implements CommandLineRunner {

	private final CurrencyService currencyService;

	public MoedasApplication(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	public static void main(String[] args) {
		SpringApplication.run(MoedasApplication.class, args);
	}

	// O RestTemplate Bean foi movido para AppConfig.java
	// @Bean
	// public RestTemplate restTemplate() {
	//    return new RestTemplate();
	// }

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		// Loop infinito para o menu
		while (true) {
			System.out.println("\n*************************************************");
			System.out.println("Bem-vindo ao Conversor de Moedas!");
			System.out.println("Escolha uma opção de conversão:");
			System.out.println("1 - Real (BRL) para Euro (EUR)");
			System.out.println("2 - Euro (EUR) para Real (BRL)");
			System.out.println("3 - Real (BRL) para Dólar (USD)");
			System.out.println("4 - Dólar (USD) para Real (BRL)");
			System.out.println("5 - Dólar (USD) para Euro (EUR)");
			System.out.println("6 - Euro (EUR) para Dólar (USD)");
			System.out.println("0 - Sair");
			System.out.println("*************************************************");
			System.out.print("Escolha uma opção: ");

			int opcao;
			try {
				opcao = scanner.nextInt();
				scanner.nextLine(); // Consome a quebra de linha
			} catch (InputMismatchException e) {
				System.out.println("\nOpção inválida! Por favor, digite um número.\n");
				scanner.nextLine(); // Limpa o buffer do scanner
				continue; // Volta ao início do loop
			}

			if (opcao == 0) {
				System.out.println("Obrigado por usar o Conversor de Moedas! Saindo...");
				break; // Encerra o programa
			}

			String fromCurrency = "";
			String toCurrency = "";

			switch (opcao) {
				case 1:
					fromCurrency = "BRL";
					toCurrency = "EUR";
					break;
				case 2:
					fromCurrency = "EUR";
					toCurrency = "BRL";
					break;
				case 3:
					fromCurrency = "BRL";
					toCurrency = "USD";
					break;
				case 4:
					fromCurrency = "USD";
					toCurrency = "BRL";
					break;
				case 5:
					fromCurrency = "USD";
					toCurrency = "EUR";
					break;
				case 6:
					fromCurrency = "EUR";
					toCurrency = "USD";
					break;
				default:
					System.out.println("\nOpção de conversão inválida! Por favor, escolha uma opção de 1 a 6.\n");
					continue; // Volta ao início do loop
			}

			// Se chegamos aqui, a opção é válida, então pedimos o valor
			try {
				System.out.printf("Digite o valor em %s que deseja converter: ", fromCurrency);
				double amount = scanner.nextDouble();
				scanner.nextLine(); // Consome a quebra de linha

				// Chama o serviço para fazer a conversão
				ConversionResponseDto response = currencyService.convertCurrency(fromCurrency, toCurrency, amount);

				// Mostra o resultado formatado
				System.out.println("\n----------- RESULTADO -----------");
				System.out.printf("Valor original: %.2f %s\n", response.getAmountToConvert(), response.getFromCurrency());
				System.out.printf("Convertido para: %.2f %s\n", response.getConvertedAmount(), response.getToCurrency());
				System.out.printf("Taxa de Câmbio: 1 %s = %.4f %s\n", response.getFromCurrency(), response.getConversionRate(), response.getToCurrency());
				System.out.printf("Cotação obtida em: %s\n", response.getTimestamp()); // Adicionado timestamp
				System.out.println("---------------------------------\n");

			} catch (InputMismatchException e) {
				System.out.println("\nERRO: O valor a ser convertido deve ser um número. Tente novamente.\n");
				scanner.nextLine(); // Limpa o buffer do scanner
			} catch ( RuntimeException e) {
				// Captura os erros que nosso serviço pode lançar (API, moeda inválida, etc.)
				System.out.println("\nERRO NA CONVERSÃO: " + e.getMessage() + "\n");
			}
		}

		scanner.close(); // Fecha o scanner ao sair do loop
		System.exit(0); // Garante que a aplicação finalize
	}
}