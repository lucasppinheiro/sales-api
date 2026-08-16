# Sales API

API REST para registro de vendas e consolidação do desempenho de vendedores em um período. O projeto foi evoluído para uma base de portfólio com Java 21, Spring Boot 3, PostgreSQL, Flyway, Docker, OpenAPI e CI.

## Stack

- Java 21 e Spring Boot 3.4
- Spring Web, Validation, Data JPA e Actuator
- PostgreSQL 16 e Flyway
- OpenAPI 3 / Swagger UI
- JUnit 5, MockMvc, Mockito e JaCoCo
- Docker Compose e GitHub Actions

## Endpoints

| Método | Rota | Descrição |
| --- | --- | --- |
| `POST` | `/api/v1/sales` | Registra uma venda |
| `GET` | `/api/v1/vendors/summary?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` | Consolida vendas por vendedor |
| `GET` | `/actuator/health` | Verifica a saúde da aplicação |

## Demo online

- Swagger UI: <https://sales-api-p2u8.onrender.com/swagger-ui/index.html>
- Health check: <https://sales-api-p2u8.onrender.com/actuator/health>

## Executar com Docker

O Docker Compose inicia a API e um PostgreSQL configurado para desenvolvimento local.

```bash
docker compose up --build
```

Quando os serviços estiverem prontos:

- Swagger UI: <http://localhost:8080/swagger-ui/index.html>
- OpenAPI JSON: <http://localhost:8080/v3/api-docs>
- Health check: <http://localhost:8080/actuator/health>

Para encerrar, execute `docker compose down`. Use `docker compose down -v` somente se desejar apagar os dados locais do PostgreSQL.

## Executar com Maven

Requer Java 21 e um PostgreSQL acessível. As configurações podem ser fornecidas por variáveis de ambiente:

| Variável | Padrão local |
| --- | --- |
| `DB_URL` | `jdbc:postgresql://localhost:5432/salesdb` |
| `DB_USERNAME` | `sales_user` |
| `DB_PASSWORD` | `sales_password` |

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

O Flyway cria automaticamente a tabela `sales` na inicialização. Em testes, o perfil `test` usa H2 em memória e aplica as mesmas migrações.

## Exemplos de uso

```bash
curl -X POST http://localhost:8080/api/v1/sales \
  -H "Content-Type: application/json" \
  -d '{"saleDate":"2026-03-10","value":1200.50,"sellerId":10,"sellerName":"Lucas Silva"}'
```

```bash
curl "http://localhost:8080/api/v1/vendors/summary?startDate=2026-03-01&endDate=2026-03-31"
```

## Testes e qualidade

```bash
./mvnw verify
```

O comando executa testes unitários e de integração, valida as migrações Flyway e gera o relatório JaCoCo em `target/site/jacoco/index.html`. O workflow em `.github/workflows/ci.yml` executa essa validação em todo push e pull request.

## Regras de negócio

- Data obrigatória e não futura.
- Valor obrigatório, positivo e com no máximo duas casas decimais.
- Identificador e nome de vendedor obrigatórios.
- A data final do relatório não pode ser anterior à data inicial.
- A média diária considera todos os dias do intervalo, inclusive os sem venda.
