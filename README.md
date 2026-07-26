# 🚨 Relatos de Segurança API

API REST desenvolvida com **Java** e **Spring Boot** para gerenciamento de **Relatos de Segurança** em ambiente industrial.

O objetivo do projeto é permitir que colaboradores registrem situações de risco, atos inseguros, condições inseguras e oportunidades de melhoria relacionadas à segurança do trabalho. Posteriormente, técnicos e administradores poderão analisar cada relato, definir sua prioridade, acompanhar seu tratamento e registrar sua conclusão.

---

## 🚀 Tecnologias Utilizadas

<p align="left">

<img src="https://img.shields.io/badge/Java-21-red?style=for-the-badge&logo=openjdk&logoColor=white"/>

<img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>

<img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge"/>

<img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>

<img src="https://img.shields.io/badge/H2_Database-09476B?style=for-the-badge"/>

<img src="https://img.shields.io/badge/Lombok-EA1B22?style=for-the-badge"/>

<img src="https://img.shields.io/badge/ModelMapper-4A90E2?style=for-the-badge"/>

<img src="https://img.shields.io/badge/Jakarta_Validation-orange?style=for-the-badge"/>

<img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black"/>

<img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white"/>

<img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github"/>

</p>

---

# 📚 Arquitetura

O projeto foi desenvolvido utilizando arquitetura em camadas, promovendo baixo acoplamento e facilidade de manutenção.

```text
Controller
     │
     ▼
Service
     │
     ▼
Repository
     │
     ▼
Banco de Dados
```

Também foram utilizados DTOs para separar a camada de apresentação da camada de domínio.

```text
Request
Response
Entity
Assembler
Repository
Service
Controller
```

---

# 📂 Estrutura do Projeto

```text
src
└── main
    ├── controller
    ├── services
    ├── repository
    ├── assembler
    ├── dto
    │     ├── request
    │     └── response
    ├── infrastructure
    │     ├── entity
    │     └── exception
    ├── config
    └── enums
```

---

# ✅ Funcionalidades Implementadas

## Usuários

* Cadastro de usuários
* Consulta por ID
* Listagem de usuários
* Atualização de nome e e-mail
* Atualização de perfis (Roles)
* Exclusão de usuários

---

## Relatos

* Cadastro de relatos
* Consulta por ID
* Listagem de relatos
* Associação entre Relato e Usuário
* Controle de Status
* Controle de Prioridade

---

# 🏗️ Entidades

Atualmente o projeto possui as seguintes entidades:

* Usuário
* Relato
* Foto

Em desenvolvimento:

* Setor

---

# 📌 Próximas Implementações

* Tratamento global de exceções
* Exceções personalizadas
* Spring Security
* Autenticação JWT
* Controle de acesso por Roles
* Upload de imagens
* Cadastro de setores
* Flyway
* Testes Unitários
* Docker
* Documentação OpenAPI

---

# 🔐 Perfis de Usuário

O sistema utiliza perfis de acesso (Roles).

* ROLE_ADMIN
* ROLE_CUSTOMER

Em breve serão adicionados novos perfis para permitir maior controle das permissões do sistema.

---

# 💾 Banco de Dados

Durante o desenvolvimento está sendo utilizado o banco de dados **H2**.

A estrutura foi preparada para utilização futura com bancos relacionais como:

* PostgreSQL
* MySQL

---

# ▶️ Como executar

Clone o projeto:

```bash
git clone https://github.com/SEU_USUARIO/relatos-seguranca-api.git
```

Entre na pasta:

```bash
cd relatos-seguranca-api
```

Execute:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8080
```

Console H2:

```
http://localhost:8080/h2-console
```

---

# 👨‍💻 Autor

**Tiago Oliveira**

Tecnólogo em Análise e Desenvolvimento de Sistemas

Projeto desenvolvido para fins de estudo, aprimoramento profissional e composição de portfólio.
