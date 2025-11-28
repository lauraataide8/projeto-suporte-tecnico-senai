🛠️ Projeto: Suporte Técnico SENAI

## 💻 Descrição do Projeto

Este é um Sistema de Suporte Técnico desenvolvido em Java com o framework Spring Boot. O objetivo é gerenciar e acompanhar solicitações de suporte, utilizando MySQL para persistência de dados.

## ⚙️ Tecnologias Principais

Backend: Java 21, Spring Boot

Banco de Dados: MySQL

Build Tool: Maven

Frontend: HTML, CSS, Bootstrap, Thymeleaf

## 🚀 Como Rodar o Projeto

Para executar a aplicação localmente, siga os passos abaixo:

## 1. Clonar e Navegar

Clone o repositório e entre na pasta:

git clone [https://github.com/lauraataide8/projeto-suporte-tecnico-senai.git](https://github.com/lauraataide8/projeto-suporte-tecnico-senai.git)
cd projeto-suporte-tecnico-senai


## 2. Configurar o Banco de Dados

Crie um banco de dados MySQL e atualize o arquivo src/main/resources/application.properties com suas credenciais:

# Credenciais
spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_seu_banco?createDatabaseIfNotExist=true
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update


## 3. Executar a Aplicação

## Execute a aplicação via Maven:

mvn spring-boot: run


## A aplicação estará acessível em: http://localhost:8080

## Desenvolvido por Laura Ataíde.
