# Kanban API

A **Kanban API** é um backend desenvolvido em Java com Spring Boot, que gerencia projetos, clientes e tarefas em um sistema de gerenciamento estilo Kanban. A API oferece uma interface para manipulação e gerenciamento de dados relacionados a projetos, clientes e tarefas, integrando de forma eficiente com o front-end desenvolvido em React.

## Tecnologias Utilizadas
- **Spring Boot**: Framework Java para construção de aplicações empresariais.
- **Spring Data JPA**: Facilita a interação com bancos de dados relacionais usando JPA.
- **PostgreSQL**: Banco de dados relacional para armazenar dados de projetos, clientes e tarefas.
- **Springdoc OpenAPI**: Geração automática de documentação OpenAPI para APIs REST.
- **Lombok**: Biblioteca Java para reduzir o boilerplate no código, como getters, setters e construtores.
- **Logback**: Framework de logging utilizado na aplicação.

## Funcionalidades da API
A Kanban API permite o gerenciamento de projetos, clientes e tarefas, facilitando a manipulação de dados essenciais para a construção de uma plataforma de Kanban eficiente. A API inclui funcionalidades como:

- **Gerenciamento de Projetos**: Criação, edição e exclusão de projetos.
- **Cadastro e Gerenciamento de Clientes**: Criação, edição e exclusão de clientes que estão associados a projetos e tarefas.
- **Gerenciamento de Tarefas**: Criação, edição, listagem e exclusão de tarefas dentro de projetos.
- **Associação de Tarefas e Clientes**: As tarefas podem ser associadas a clientes específicos dentro dos projetos.

## Acessando a Documentação da API
A documentação interativa da API está disponível através do Swagger UI. Para acessar os endpoints da API e entender como interagir com as entidades, você pode visitar o seguinte link:

[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

No Swagger UI, você encontrará todos os endpoints disponíveis, as entidades que são manipuladas pela API, além de poder testar as requisições diretamente pela interface web.

## Banco de Dados com Docker Compose

Para rodar a API com um banco de dados PostgreSQL usando **Docker Compose**, siga as etapas abaixo:

1. **Subir o ambiente com Docker Compose**

Basta rodar o seguinte comando para iniciar os containers do banco de dados e da API:

```bash
docker-compose up
