print("Iniciando o script de inicialização...");

db.createUser({
    user: "admin",
    pwd: "password",
    roles: [{ role: "root", db: "admin" }]
});

print("Usuário admin criado com sucesso.");

db.createCollection("clients");

print("Coleção 'clients' criada.");

db.clients.insertMany([
    { _id: UUID("e2b9928f-dcf2-41ab-9e77-3ad3be753226"), name: "Client A", cpf: "12345678901", email: "clientA@example.com" },
    { _id: UUID("4e8e7c86-bf02-4016-b7f3-08d6a7b6b60f"), name: "Client B", cpf: "98765432100", email: "clientB@example.com" }
]);

print("Dados iniciais inseridos na coleção 'clients'.");
