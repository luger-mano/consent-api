# 📋 Consent API — Open Insurance Brasil

![Java](https://img.shields.io/badge/Java-21-000?logo=openjdk&logoColor=fff&style=flat)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.13-6DB33F?logo=springboot&logoColor=fff&style=flat)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-4169E1?logo=postgresql&logoColor=fff&style=flat)
![Redis](https://img.shields.io/badge/-Redis-DC382D?logo=Redis&logoColor=FFF)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=fff&style=flat)
![SOLID](https://img.shields.io/badge/Solid-2C4F7C?logo=solid&logoColor=fff&style=flat)
![JUnit5](https://img.shields.io/badge/JUnit5-25A162?logo=junit5&logoColor=fff&style=flat)
![GitHub Actions Badge](https://img.shields.io/badge/GitHub%20Actions-2088FF?logo=githubactions&logoColor=fff&style=flat)
---

## 📖 Sobre o Projeto

A **Consent API** é uma solução robusta desenvolvida para o ecossistema do **Open Insurance Brasil**. O objetivo principal é gerenciar o ciclo de vida de consentimentos, permitindo a criação, consulta e atualização de status de forma segura e padronizada.

O projeto foi construído seguindo rigorosos padrões de engenharia de software, garantindo escalabilidade, manutenibilidade e conformidade com as especificações do setor.

---

## 🏗️ Arquitetura do Projeto

A aplicação segue uma arquitetura modular inspirada em **DDD (Domain Driven Design)** e **Clean Architecture**, garantindo a separação de responsabilidades:

- **Camada de Domínio**: Contém as regras de negócio e entidades centrais.
- **Camada de Aplicação**: Gerencia os casos de uso e orquestração.
- **Camada de Infraestrutura**: Implementa detalhes técnicos como persistência (JPA/Hibernate) e configurações.
- **Camada de Adaptadores**: Controladores REST e DTOs para comunicação externa.

---

## 🛠️ Ferramentas Utilizadas

O projeto utiliza as melhores tecnologias e práticas do mercado:

| Ferramenta | Descrição |
| :--- | :--- |
| **Java 21** | Linguagem principal com recursos modernos. |
| **Spring Boot 3.5.13** | Framework para criação de microserviços. |
| **PostgreSQL 18** | Banco de dados relacional para persistência segura. |
| **MapStruct** | Mapeamento eficiente entre DTOs e Entidades. |
| **Lombok** | Redução de código boilerplate. |
| **Swagger (OpenAPI)** | Documentação interativa da API. |
| **JUnit 5 & Mockito** | Testes unitários e de integração. |
| **Testcontainers** | Ambiente de testes isolado com Docker. |
| **SOLID** | Princípios de design para código limpo e flexível. |
| **GitHub Actions** | Esteira de automação de testes. |
| **Redis** | Sistema de cache de alto desempenho. |
---

## 📂 Estrutura do Projeto

```plaintext
ConsentAPI /
├── config
|         ├── exceptions             
├── domain
|         ├── controller
|         ├── enums
|         ├── model            
├── dto
|      ├── req
|      ├── res 
├── mapper       
├── repository      
├── service
|          ├── consent
|          ├── idempotency 
└── ├──              
    ├── docker_app     # Docker Compose das Aplicações
    └── docker_db      # Docker Compose dos Bancos de Dados
```

---

## 🚀 Como Inicializar a Aplicação

### 📋 Pré-requisitos

- Docker e Docker Compose instalados.
- Java 21 (caso queira rodar localmente sem Docker).
- Maven 3.9+.

### 🐳 Via Docker (Recomendado)

Para subir todo o ambiente (Aplicação + Banco de Dados (PostgreSQL) + Redis) de forma automatizada:

1. Clone o repositório:
   ```bash
   git clone https://github.com/luger-mano/consent-api.git
   cd consent-api
   ```

2. Execute o Docker Compose:
   ```bash
   docker-compose up -d
   ```

A aplicação estará disponível em `http://localhost:8080`
e no **Swagger** `http://localhost:8080/swagger-ui/index.html`.

---

## Endpoints Configurados e mapeados. 

[![Postman Badge](https://img.shields.io/badge/Postman-FF6C37?logo=postman&logoColor=fff&style=flat)](https://web.postman.co/workspace/My-Workspace~912e6447-255d-4e54-8700-1363f4cb3c57/collection/32811777-1132c4d7-a70c-4140-9631-8a533befbceb?action=share&source=copy-link&creator=32811777)


<img width="183" height="161" alt="image" src="https://github.com/user-attachments/assets/86329f5f-9a53-4dfa-9a1b-373c2aa72d88" />


---

## 📡 API Endpoints & Exemplos

### Criar Consentimento (POST)

Cria um novo registro de consentimento com suporte a **Idempotência**.

- **URL:** `/consents`
- **Método:** `POST`
- **Headers:** 
  - `X-Idempotency-Key`: `ex: 1234-abcd`

**Corpo da Requisição:**
```json
{
  "cpf": "824.199.220-34",
  "status": "ACTIVE",
  "creationDateTime": "",
  "expirationDateTime": "",
  "additionalInfo": "consent created"
}
```

**Corpo da Resposta (200 Created):**
```json
{
    "status": "ACTIVE",
    "message": "Consent created",
    "consentStatus": "550e8400-e29b-41d4-a716-446655440000"
}
```
```
Com a idempotência implementada, toda vez que uma requisição é feita com determinada chave,
não há duplicidade de um objeto já existente. Ao invés de fazer um INSERT no banco de dados,
a consulta é feita por um SELECT.
```

### Busca Todos Consentimentos (GET)
Busca todos os consentimentos registrados na base de dados com **Paginação**.

- **URL:** `/consents`
- **Método:** `GET`


**Corpo da Resposta (200 OK):**
```json
[
    {
        "consent": {
            "id": "06ad6f1f-042c-411c-8e62-6420e5a0e30e",
            "cpf": "824.199.220-34",
            "status": "EXPIRED",
            "creationDateTime": "2026-03-29T22:25:31.911715",
            "expirationDateTime": "2026-03-29T22:27:31.911715",
            "additionalInfo": "consent created"
        },
        "page": 0,
        "pageSize": 10,
        "totalPages": 1,
        "totalElements": 2
    },
    {
        "consent": {
            "id": "f0ed5386-f6db-409a-839a-9f36671b715b",
            "cpf": "780.739.860-43",
            "status": "REVOKED",
            "creationDateTime": "2026-03-29T22:21:30.076016",
            "expirationDateTime": "2026-03-30T01:26:41.812",
            "additionalInfo": "consent updated"
        },
        "page": 0,
        "pageSize": 10,
        "totalPages": 1,
        "totalElements": 2
    }
]
```
**Corpo da Resposta (204 OK):**
```json
[]
```
### Busca Consentimento por ID (GET)
Busca consentimento pelo ID.

- **URL:** `/consents/{id}`
- **Método:** `GET`

**Corpo da Resposta (200 OK):**
```json
{
    "id": "f0ed5386-f6db-409a-839a-9f36671b715b",
    "cpf": "780.739.860-43",
    "status": "REVOKED",
    "creationDateTime": "2026-03-29T22:21:30.076016",
    "expirationDateTime": "2026-03-30T01:26:41.812",
    "additionalInfo": "consent updated"
}
```
**Corpo da Resposta (204 OK):**
```json
[]
```

### Atualiza Consentimento por ID (PUT)
Atualiza consentimento pelo ID.

- **URL:** `/consents/{id}`
- **Método:** `PUT`

**Corpo da Requisição:**
```json
{
  "status": "ACTIVE",
  "expirationDateTime": "2026-03-27T18:10:13",
  "additionalInfo": "consent created"
}
```
**Corpo da Resposta (200 OK):**
```json
{
    "id": "e36567dc-c42e-483b-ac1e-5c21ea7586f9",
    "cpf": "162.959.390-74",
    "status": "ACTIVE",
    "creationDateTime": "2026-03-30T04:27:53.965939",
    "expirationDateTime": "2026-03-27T18:10:13",
    "additionalInfo": "consent created"
}
```
### Deleta Consentimento por ID (DELETE)
Deleção lógica de um consentimento pelo ID.

- **URL:** `/consents/{id}`
- **Método:** `DELETE`

**Corpo da Resposta (200 OK):**
```json
Consent deleted
```

### Histórico de alterações (GET)
Retorna todas as alterações feitas através de um histórico de ações.

- **URL:** `/consents/history`
- **Método:** `GET`

**Corpo da Resposta (200 OK):**
```json
{"2026-03-27T18:10:13" : "Invoked Method: deleteConsentById"}
```
**Corpo da Resposta (204 OK):**
```json
{}
```

---

## ⚙️ Versões das Ferramentas

| Componente | Versão |
| :--- | :--- |
| **Java** | 21 |
| **Spring Boot** | 3.5.13 |
| **PostgreSQL** | 18 |
| **MapStruct** | 1.6.3 |
| **SpringDoc OpenAPI** | 2.8.16 |
| **Jakarta Persistence** | 3.1.0 |

---

## ✨ Autor

Desenvolvido por **Lucas Germano** (luger-mano).
Focado em engenharia de software moderna e arquiteturas escaláveis.
