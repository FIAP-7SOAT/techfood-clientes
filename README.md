# TechFood-Clientes - Sistema de Autoatendimento para Restaurante FastFood

Este é um projeto do curso de Pós-graduação em Arquitetura de Software da FIAP compreende uma solução possível para um sistema de autoatendimento de restaurante do tipo fast-food, com quiosques ou terminais de autoatendimento, com o objetivo de otimizar o processo de pedidos, pagamento, preparação e entrega de comida..

Acesse a **[documentação principal](https://github.com/FIAP-7SOAT/techfood-docs)** do projeto para mais detalhes!

Autores membros do Grupo:

- Geraldo Moratto Junior - RM356285
- Pedro Cantarelli - RM355410
- Vinicius Lopes - RM354901

## Índice

- [Recursos provisionados no Kubernetes](#recursos-provisionados-no-kubernetes)
- [APIs Disponíveis](#apis-disponíveis)
- [Como Executar](#como-executar)
- [Banco de dados](#banco-de-dados)
- [Acessando Swagger](#acessando-swagger)
- [Postman Collection](#postman-collection)

## Recursos provisionados no Kubernetes

Lista de arquivos YAML com recursos do Kubernetes:

- **config-db.yaml:** Configurações necessárias para o funcionamento do banco de dados;
- **deployment-app.yaml:** Deployment para disponibilização da aplicação;
- **deployment-db.yaml:** Deployment para disponibilização do banco de dados;
- **hpa-app.yaml:** Mapeamento de quantidade de réplicas para escalabilidade da aplicação;
- **pv-db.yaml:** Mapeamento de persistência de volume para os arquivos de banco de dados;
- **pvc-db.yaml:** Mapeamento de persistência de volume com configuração de claims para os volumes do banco de dados;
- **secrets.yaml:** Armazenamento das chaves/tokens para a API;
- **service-app.yaml:** Mapeamento das portar para acesso ao serviço NodePort da aplicação;
- **service-db.yaml:** Mapeamento das portas para acesso ao serviço ClusterIP de banco de dados;

[Arquitetura Kubernetes](https://www.figma.com/board/JpMG7uY03GHnNY92hHxdb3/Lanchonete-de-Bairro?node-id=0-1&t=W1aQzvEzhq0IOrMn-0)
![Arquitetura Kubernetes](https://cdn.discordapp.com/attachments/1310749229756448779/1311490696871411712/image.png?ex=674a5de4&is=67490c64&hm=c6cffb64ceae81f29f690cf4cdbad9fcbc03b2259043409c7e14e168f6e20640&)

## APIs Disponíveis

O TechFood Clientes expõe as seguintes APIs para integração:

- Buscar Todos os Clientes
- Buscar Cliente por CPF
- Cadastrar Cliente
- Atualizar Cliente

### Como Executar

Para executar o sistema, siga as instruções abaixo:

1. Certifique-se de ter o Docker, Docker Compose, Docker Desktop instalados em seu computador.
2. Clone o repositório, no terminal executando o comando:

```
$ git clone https://github.com/FIAP-7SOAT/techfood-clientes
```

3. Entre na pasta do projeto:

```
$ cd techfood-clientes
```

4. Escolha como quer executar

- [Docker](docs/docker.md)
- [Kubernetes](docs/kubernetes.md)

### Banco de dados

Leia a documentação do banco de dados [aqui](docs/database.md)

Para vizualizar o Banco de Dados através, recomendamos que baixe o DBeaver ou outro Gerenciador de banco de dados para PostgreSQL de sua preferência:

- Criar nova conexão
- Host: localhost
- Port: 5432
- Database: techfood
- Username: postgres
- Password: postgres

### Acessando Swagger

Acesse a documentação da API através do Swagger para começar a interagir com o sistema.
Para acessar o Swagger utilize a url [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

### Postman Collection

Baixar o Postman ou o API Client de sua preferência e importar a collection:

[API Client Collection](src/main/resources/collection/fiap_techfood_postman_collection.json).

### Principais Tecnologias Utilizadas

- **Kotlin**
- **Java 17**
- **Spring-Boot 3.2.5**
- **PostgreSQL**
- **Docker**
- **Swagger**
- **Gradle 8**
- **Kubernetes**
- **Terraform**

--

Acesse a **[documentação principal](https://github.com/FIAP-7SOAT/techfood-docs)** do projeto para mais detalhes!
<!--

### Video da Arquitetura

- [Funcionamento da apliação](https://www.youtube.com/watch?v=33iDsv87Nnc&ab_channel=PedroCantarelli).
- [Arquitetura do Projeto](https://www.youtube.com/watch?v=a7mExdMBwO4&ab_channel=PedroCantarelli)

-->
