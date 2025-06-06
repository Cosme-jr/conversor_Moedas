# 💰 Conversor de Moedas - Alura Challenge ONE

Este é um projeto de Conversor de Moedas desenvolvido como parte do Challenge ONE da Alura, utilizando Spring Boot para criar uma aplicação backend robusta com interação via console e uma API REST para conversão de moedas.

## ✨ Funcionalidades

* **Conversão de Moedas por Console:** Interface interativa via linha de comando para realizar conversões entre BRL, USD e EUR.
* **API REST para Conversão:** Endpoints HTTP para consumo por outras aplicações ou ferramentas como Postman.
* **Integração com API Externa:** Utiliza a API [ExchangeRate-API](https://www.exchangerate-api.com/) para obter cotações de câmbio em tempo real.
* **Proteção de Chave de API:** Configuração segura para evitar a exposição da chave da API em repositórios públicos.
* **Tratamento de Erros:** Validação de entradas do usuário e tratamento de falhas na comunicação com a API externa.

## 🚀 Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3.x**
    * Spring Web (para API REST)
    * Spring Data JPA (pode ser removido se não for usar banco de dados, mas está no projeto)
    * Spring DevTools (para agilizar o desenvolvimento)
* **Gradle** (Sistema de automação de build)
* **RestTemplate** (Para consumir a API externa)
* **Postman** (Para testar a API REST)

## 🛠️ Como Rodar o Projeto

### Pré-requisitos

* Java 21 JDK instalado.
* Gradle (geralmente incluído no projeto com `gradlew`).
* Uma chave de API da [ExchangeRate-API](https://www.exchangerate-api.com/).

### Configuração da Chave da API

Para proteger sua chave da API, este projeto espera que ela seja configurada no arquivo `application.properties` (que é ignorado pelo Git) ou como uma variável de ambiente.

1.  Crie ou edite o arquivo `src/main/resources/application.properties` na **raiz do seu projeto** (este arquivo NÃO deve ser versionado no Git).
2.  Adicione as seguintes linhas, substituindo `SUA_CHAVE_DA_API` pela sua chave real da ExchangeRate-API:
    ```properties
    exchangerate.api.key=SUA_CHAVE_DA_API
    exchangerate.api.url=[https://v6.exchangerate-api.com/v6/](https://v6.exchangerate-api.com/v6/)
    ```
3.  Certifique-se de que a linha `application.properties` está presente no seu arquivo `.gitignore` (na raiz do projeto).

### Executando via IDE (IntelliJ IDEA, Eclipse, etc.)

1.  Abra o projeto na sua IDE.
2.  Localize a classe principal `MoedasApplication.java` (em `src/main/java/br/com/conversor/moedas`).
3.  Clique com o botão direito na classe e selecione `Run 'MoedasApplication.main()'`.
4.  A aplicação iniciará e o menu de conversão aparecerá no console da IDE.

### Executando via Linha de Comando

1.  Navegue até a raiz do projeto no seu terminal.
2.  Compile o projeto (se necessário) e execute-o. Para Spring Boot com Gradle:
    ```bash
    ./gradlew bootRun
    ```
    (No Windows, use `gradlew bootRun`)

## 🌐 Endpoints da API REST

A API estará disponível em `http://localhost:8080`.

### 1. Converter Moeda

* **Endpoint:** `GET /convert`
* **Parâmetros de Query:**
    * `from`: Sigla da moeda de origem (ex: `USD`, `EUR`, `BRL`)
    * `to`: Sigla da moeda de destino (ex: `USD`, `EUR`, `BRL`)
    * `amount`: Valor a ser convertido (número decimal)
* **Exemplo de Requisição (com Postman):**
  `GET http://localhost:8080/convert?from=USD&to=BRL&amount=50`
* **Exemplo de Resposta (JSON):**
    ```json
    {
        "fromCurrency": "USD",
        "toCurrency": "BRL",
        "amountToConvert": 50.0,
        "conversionRate": 5.25,
        "convertedAmount": 262.50,
        "timestamp": "2025-06-06"
    }
    ```

### 2. Obter Taxas de Câmbio

* **Endpoint:** `GET /rates`
* **Parâmetros de Query:**
    * `base`: Sigla da moeda base (opcional, padrão `USD`)
* **Exemplo de Requisição (com Postman):**
  `GET http://localhost:8080/rates?base=BRL`
* **Exemplo de Resposta (JSON):**
    ```json
    {
        "USD": 0.19,
        "EUR": 0.17,
        "BRL": 1.0,
        // ... outras taxas ...
    }
    ```

## 🤝 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## 📄 Licença

Este projeto está licenciado sob a [MIT License](https://opensource.org/licenses/MIT).

## 📞 Contato

Se você tiver alguma dúvida ou sugestão, entre em contato:

* **[Cosme-jr]** - [https://github.com/Cosme-jr](https://github.com/SEU_USUARIO) 
* **[cosmejr1981@gmail.com]** 

---