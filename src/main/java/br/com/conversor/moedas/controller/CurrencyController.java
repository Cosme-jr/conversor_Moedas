package br.com.conversor.moedas.controller;

import br.com.conversor.moedas.dto.ConversionResponseDto;
import br.com.conversor.moedas.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * Endpoint para converter um valor entre duas moedas.
     * Exemplo de uso: /convert?from=USD&to=BRL&amount=100
     */
    @GetMapping("/convert")
    public ResponseEntity<?> convert(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount) {
        try {
            // Chama o serviço para realizar a conversão
            ConversionResponseDto response = currencyService.convertCurrency(from, to, amount);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Erros de validação (moeda inválida, valor inválido, etc.)
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            // Outros erros vindos do serviço (ex: falha na API externa)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro durante a conversão: " + e.getMessage());
        }
    }

    /**
     * Endpoint opcional para buscar todas as taxas de uma moeda base.
     * Exemplo de uso: /rates?base=USD
     */
    @GetMapping("/rates")
    public ResponseEntity<?> getExternalRates(@RequestParam(name = "base", defaultValue = "USD") String baseCurrency) {
        try {
            // Busca as taxas através do serviço
            Map<String, Double> rates = currencyService.getRates(baseCurrency.toUpperCase()).getConversion_rates();
            return ResponseEntity.ok(rates);
        } catch (RuntimeException e) {
            // Captura exceções lançadas pelo serviço
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar taxas: " + e.getMessage());
        }
    }
}
