# 🏦 Sistema de Gerenciamento Bancário (Java + JDBC)

Este projeto é uma aplicação de console desenvolvida em **Java** que simula as operações essenciais de um banco. Ele utiliza o padrão de arquitetura **MVC (Model-View-Controller)** e integra-se a um banco de dados relacional via **JDBC** para garantir a persistência e integridade dos dados.

## 🚀 Funcionalidades

O sistema está dividido em dois grandes módulos:

### 1. Gestão de Clientes e Endereços
- **Cadastro Completo:** Registro de clientes vinculados a um endereço (relacionamento 1:1).
- **Listagem com JOIN:** Visualização detalhada de clientes e seus respectivos endereços através de consultas SQL otimizadas.
- **Busca por CPF:** Localização rápida de usuários na base de dados.
- **Exclusão Segura:** O sistema impede a remoção de clientes que possuam contas bancárias ativas, garantindo a integridade referencial.

### 2. Operações Bancárias e Transações
- **Abertura de Contas:** Suporte para Contas Correntes e Poupança.
- **Saques e Depósitos:** Validação de saldo e status da conta (apenas contas ativas podem operar).
- **Transferências:** Lógica de transferência entre contas com suporte a **Rollback**. Se o depósito falhar, o saque é desfeito automaticamente.
- **Extrato Detalhado:** Histórico completo de transações com data, tipo de operação e saldo resultante.
- **Encerramento de Conta:** A conta só pode ser encerrada se o saldo estiver zerado.

## 🛠️ Tecnologias Utilizadas
- **Linguagem:** Java 17+
- **Banco de Dados:** PostgreSQL / MySQL
- **Persistência:** JDBC (Java Database Connectivity)
- **Arquitetura:** MVC (Model-View-Controller)

## 🏗️ Estrutura do Banco de Dados
O projeto utiliza quatro tabelas principais interconectadas:
- `endereco`: Armazena dados de localização.
- `clientes`: Vinculados a um endereço.
- `contas`: Vinculadas a um cliente (ID do titular).
- `transacoes`: Registra cada movimentação financeira vinculada a uma conta.

## 🧠 Diferenciais Técnicos Implementados
Durante o desenvolvimento, foram aplicadas boas práticas de engenharia de software:
- **Segurança Transacional (ACID):** As transferências utilizam `setAutoCommit(false)`, garantindo que ou a operação completa ocorre com sucesso, ou nada é alterado no banco.
- **Validações de Regra de Negócio:**
  - Impedimento de saque maior que o saldo disponível.
  - Bloqueio de exclusão de cliente com vínculo bancário.
  - Verificação de contas inativas antes de qualquer transação.
- **Tratamento de Exceções:** Uso de blocos `try-catch-finally` para garantir que as conexões com o banco sejam fechadas corretamente.

## ⚙️ Como Executar o Projeto
- **Configurar o Banco de Dados:** Execute o script SQL fornecido na pasta `/sql` para criar as tabelas e relacionamentos.
- **Configurar a Conexão:** Ajuste as credenciais de acesso (URL, User, Password) na classe `BancoConnection`.
- **Compilar e Rodar:** Execute a classe `Main.java` para iniciar o menu interativo via console.

---
> **Status do Projeto:** Em desenvolvimento / Funcional.
> Desenvolvido com foco em lógica de persistência e integridade de dados.
