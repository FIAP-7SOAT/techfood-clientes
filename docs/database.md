# Documentação do Banco de Dados

Com base no schema do banco de dados fornecido, vamos detalhar a estrutura e os relacionamentos entre as tabelas, bem como justificar a escolha do PostgreSQL para este projeto.

## Esquema de Banco de Dados

### Tabelas e Campos

1. tb_products

- `id` **(UUID, PK)**: Identificador único do produto.
- `name` **(VARCHAR(255))**: Nome do produto.
- `description` \*\*(VARCHAR(255)): Descrição do produto.
- `price` **(NUMERIC(38, 2))**: Preço do produto.
- `category` **(SMALLINT)**: Categoria do produto (Lanche, Acompanhamento, Bebida, Sobremesa).
- `imageurl` **(VARCHAR(255))**: URL da imagem do produto.

2. tb_clients

- `id` **(UUID, PK)**: Identificador único do cliente.
- `name` **(VARCHAR(255))**: Nome do cliente.
- `cpf` **(VARCHAR(255), Unique)**: CPF do cliente.
- `email` **(VARCHAR(255), Unique)**: Email do cliente.

3. tb_orders

- `id` **(UUID, PK)**: Identificador único do pedido.
- `name` **(VARCHAR(255))**: Nome opcional do pedido.
- `status` **(INTEGER)**: Status do pedido (Recebido, Em preparação, Pronto, Finalizado).
- `is_anonymous` **(BOOLEAN)**: Indica se o pedido foi feito anonimamente.
- `time_to_prepare` **(INTEGER)**: Tempo estimado para preparação do pedido.
- `client_id` **(UUID, FK)**: Referência ao cliente que fez o pedido.
- `creation_date` **(TIMESTAMP)**: Data e hora da criação do pedido.
- `last_update_date` **(TIMESTAMP)**: Data e hora da última atualização do pedido.

4. tb_order_items

- `description` **(VARCHAR(255))**: Descrição do item do pedido.
- `quantity` **(INTEGER)**: Quantidade do item pedido.
- `order_id` **(UUID, FK)**: Referência ao pedido.
- `product_id` **(UUID, FK)**: Referência ao produto pedido.

### Relacionamentos

- **tb_clients** ↔ **tb_orders**: Um cliente pode fazer vários pedidos, mas cada pedido está associado a um único cliente (ou nenhum, se anônimo).
- **tb_orders** ↔ **tb_order_items**: Um pedido pode conter vários itens de pedido, mas cada item de pedido está associado a um único pedido.
- **tb_products** ↔ **tb_order_items**: Cada item de pedido refere-se a um único produto, mas um produto pode estar em vários itens de pedido.

### Justificativa da Escolha do PostgreSQL

PostgreSQL foi escolhido como o SGBD para este projeto devido a várias características que o tornam adequado para sistemas de gerenciamento de pedidos em tempo real:

1. **Suporte a Tipos de Dados Complexos:**

   PostgreSQL suporta uma ampla gama de tipos de dados, incluindo UUIDs, que são ideais para garantir a unicidade global dos identificadores em um ambiente distribuído.

2. **Conformidade ACID:**

   As propriedades ACID do PostgreSQL garantem transações confiáveis e seguras, essenciais para o processamento de pedidos e pagamentos.

3. **Escalabilidade e Desempenho:**

   PostgreSQL é altamente escalável e pode lidar com um grande volume de transações, o que é crucial para um sistema de pedidos em uma lanchonete em expansão.

4. **Extensões e Indexação Avançada:**

   Possui capacidades avançadas de indexação e extensões como PostGIS, que podem ser úteis para futuras expansões do sistema, como análises geográficas de pedidos.

5. **Comunidade Ativa e Suporte:**

   A comunidade ativa e o vasto ecossistema de suporte do PostgreSQL significam que é fácil obter ajuda e recursos para desenvolver e manter o sistema.

### Considerações

À medida que o sistema de autoatendimento da lanchonete evolui e as demandas aumentam, planejamos expandir nossa infraestrutura de banco de dados para incluir outras tecnologias que possam otimizar o desempenho e a escalabilidade. Uma dessas tecnologias é o Redis, um armazenamento de estrutura de dados em memória, que pode ser utilizado para melhorar a eficiência de operações de alta frequência e acesso rápido aos dados.
