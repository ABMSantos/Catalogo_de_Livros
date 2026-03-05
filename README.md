# LiterAlura 📚

Projeto desenvolvido como parte do desafio da formação Java da Alura. O LiterAlura é um catálogo de livros em linha de comando (CLI) que consome a [API Gutendex](https://gutendex.com/) para buscar informações de obras e autores, e persiste esses dados em um banco de dados relacional PostgreSQL.

## 🚀 Funcionalidades

O menu interativo via terminal oferece as seguintes opções:
1. **Buscar livro pelo título:** Faz uma requisição à API Gutendex, converte o JSON retornado e salva o livro e o autor no banco de dados (evitando duplicidades).
2. **Listar livros registrados:** Retorna todos os livros já salvos no banco local.
3. **Listar nossos autores:** Retorna todos os autores cadastrados no banco.
4. **Listar autores em determinado ano:** Filtra no banco de dados os autores que estavam vivos no ano pesquisado pelo usuário.
5. **Listar livros em determinado idioma:** Permite buscar no banco local livros por idiomas específicos (ex: `pt`, `en`, `fr`, `es`).

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA** (Hibernate)
* **PostgreSQL** (Banco de dados)
* **Jackson Databind** (Desserialização de JSON)
* **Maven** (Gerenciamento de dependências)

## ⚙️ Como executar o projeto

1. Clone este repositório.
2. Certifique-se de ter o PostgreSQL rodando localmente (ou via Docker).
3. Atualize o arquivo `application.properties` com as credenciais do seu banco de dados:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
   spring.datasource.username=SEU_USUARIO
   spring.datasource.password=SUA_SENHA

4. Execute a classe LiteraluraApplication na sua IDE ou rode o comando Maven:
Bash
mvn spring-boot:run

👩‍💻 Autora
Desenvolvido por Ana Bárbara como parte do Curso da One - Oracle Next Education! com Alura.