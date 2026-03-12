# Sales API

API REST desenvolvida com Spring Boot para registrar vendas e consolidar o desempenho dos vendedores em um periodo informado.

## Objetivo do desafio

Implementar dois servicos REST:

- criar uma nova venda;
- listar vendedores com total de vendas e media diaria de vendas no intervalo informado.

## Tecnologias utilizadas

- Java 8
- Spring Boot 2.7.18
- Spring Web
- Spring Data JPA
- H2 Database
- JUnit 5 / MockMvc / Mockito
- JaCoCo para relatorio de cobertura

## Decisoes tecnicas

- Optei por usar `H2` em memoria para manter a configuracao simples e alinhada ao enunciado.
- A aplicacao foi dividida em `controller`, `service`, `repository` e `dto` para deixar cada responsabilidade clara.
- A media diaria foi calculada considerando todos os dias do intervalo informado, inclusive dias sem venda. Essa foi uma premissa adotada por nao haver uma definicao mais detalhada no desafio.
- O total de vendas do vendedor foi interpretado como a soma monetaria do campo `value` no periodo.
- Foi criado um tratamento global de excecoes para padronizar erros de validacao e regras de negocio.

## Estrutura do projeto

- `controller`: expõe os endpoints REST e recebe os parametros da requisicao.
- `service`: concentra as regras de negocio e a orquestracao da aplicacao.
- `repository`: faz a persistencia das vendas e a consulta agregada por vendedor.
- `dto`: separa o contrato da API dos objetos de persistencia.
- `exception`: padroniza as respostas de erro para validacoes e cenarios de negocio.

## Endpoints

### Criar venda

`POST /api/v1/sales`

#### Exemplo de requisicao

```json
{
  "saleDate": "2026-03-10",
  "value": 1200.50,
  "sellerId": 10,
  "sellerName": "Lucas Silva"
}
```

#### Exemplo de resposta

```json
{
  "id": 1,
  "saleDate": "2026-03-10",
  "value": 1200.50,
  "sellerId": 10,
  "sellerName": "Lucas Silva"
}
```

### Resumo por vendedor

`GET /api/v1/vendors/summary?startDate=2026-03-01&endDate=2026-03-31`

#### Exemplo de resposta

```json
[
  {
    "sellerName": "Marina",
    "totalSales": 300.00,
    "dailyAverageSales": 9.68
  },
  {
    "sellerName": "Lucas",
    "totalSales": 150.00,
    "dailyAverageSales": 4.84
  }
]
```

## Como executar

### Pre-requisitos

- Java 8 ou superior

### Passos

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicacao sobe por padrao em `http://localhost:8080`.

### Console do H2

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:salesdb`
- Usuario: `sa`
- Senha: em branco

## Como rodar os testes

```bash
./mvnw test
```

Para gerar tambem o relatorio de cobertura:

```bash
./mvnw verify
```

O relatorio do JaCoCo sera gerado em `target/site/jacoco/index.html`.

## Validacoes implementadas

- data da venda obrigatoria e nao futura;
- valor obrigatorio e maior que zero;
- id do vendedor obrigatorio;
- nome do vendedor obrigatorio;
- periodo do relatorio com data final maior ou igual a data inicial.

## Melhorias que eu faria em uma evolucao

- documentacao OpenAPI/Swagger;
- paginacao e filtros adicionais;
- versionamento e migracoes de banco com Flyway;
- endpoint para consulta individual de venda.
