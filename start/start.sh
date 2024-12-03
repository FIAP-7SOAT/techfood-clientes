#!/bin/bash

# Nome da rede compartilhada
NETWORK_NAME="techfood-network"

# Obtém o diretório do script, independentemente de onde ele é executado
SCRIPT_DIR=$(dirname "$0")
PROJECT_DIR=$(realpath "$SCRIPT_DIR/..")  # Caminho absoluto do diretório do projeto

# Verifica se a rede já existe
if ! docker network ls --format '{{.Name}}' | grep -q "^${NETWORK_NAME}\$"; then
  echo "Rede '${NETWORK_NAME}' não encontrada. Criando..."
  docker network create ${NETWORK_NAME}
else
  echo "Rede '${NETWORK_NAME}' já existe."
fi

# Inicia o serviço techfood-clientes
echo "Iniciando techfood-clientes..."
(cd "$PROJECT_DIR" && docker-compose up -d)

echo "Serviços iniciado."
