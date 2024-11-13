# Requisitos

Há uma hamburgueria sem um sistema de controle de pedidos, o atendimento aos clientes está sendo demorado e confuso.

Sem um sistema de controle de pedidos, estão ocorrendo atrasos na preparação e entrega dos pedidos e insatisfação dos clientes.

O sistema de controle de pedidos é essencial para garantir que a hamburgueria possa atender os clientes de maneira eficiente, gerenciando seus pedidos e estoques de forma adequada. Sem o sistema a hamburgueria pode acabar não dando certo, resultando em clientes insatisfeitos e impactando os negócios de forma negativa.

Para solucionar o problema, a hamburgueria irá investir em um sistema de autoatendimento de fast food que permitirá aos clientes selecionar e fazer pedidos sem precisar interagir com um atendente, o sistema terá as seguintes funcionalidades:

### Pedido

Os clientes são apresentados a uma interface de seleção na qual podem optar por se identificarem via CPF, se cadastrarem com nome, e-mail ou não se identificar, podendo montar o combo na seguinte sequência, sendo todas elas opcionais:

1. Lanche
2. Acompanhamento
3. Bebida

Em cada etapa é exibido o nome, descrição e preço de cada produto.

### Pagamento

O sistema deverá possuir uma opção de pagamento integrada para MVP. A forma de pagamento oferecida será via QRCode do Mercado Pago.

### Acompanhamento

Uma vez que o pedido é confirmado e pago, ele é enviado para a cozinha para ser preparado. Simultaneamente deve aparecer em um monitor para o cliente acompanhar o progresso do seu pedido com as seguintes etapas:

- Recebido
- Em preparação
- Pronto
- Finalizado

### Entrega

Quando o pedido estiver pronto, o sistema deverá notificar o cliente que ele está pronto para retirada. Ao ser retirado, o pedido deve ser atualizado para o status finalizado.

Além das etapas do cliente, o estabelecimento precisa de um acesso administrativo:

### Gerenciar clientes

Com a identificação dos clientes o estabelecimento pode trabalhar em campanhas promocionais.

### Gerenciar produtos e categorias

Os produtos dispostos para escolha do cliente serão gerenciados pelo estabelecimento, definindo nome, categoria, preço, descrição e imagens. Para esse sistema teremos categorias fixas

- Lanche
- Acompanhamento
- Bebida
- Sobremesa

### Acompanhamento de pedidos

Deve ser possível acompanhar os pedidos em andamento e tempo de espera de cada pedido.

As informações dispostas no sistema de pedidos precisarão ser gerenciadas pelo estabelecimento através de um painel administrativo.
