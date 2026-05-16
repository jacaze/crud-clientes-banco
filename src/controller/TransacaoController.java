package controller;

import dao.BancoConnection;
import model.ContaBancariaModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransacaoController {

    BancoConnection dao = new BancoConnection();
    ContaBancariaController contaBancariaController = new ContaBancariaController();

    // --- Metodo para registrar as operações ---
    private void registrarOperacao(int contaId, double valor, String tipo, double novoSaldo) throws SQLException {
        String sqlTransacao = "INSERT INTO transacoes (conta_id, valor, tipo_transacao, saldo_restante) VALUES (?, ?, ?, ?)";

        PreparedStatement stmt = dao.getConect().prepareStatement(sqlTransacao);
        stmt.setInt(1, contaId);
        stmt.setDouble(2, valor);
        stmt.setString(3, tipo);
        stmt.setDouble(4, novoSaldo);
        stmt.executeUpdate();
        stmt.close();
    }

    // --- Metodo de saque ---
    public void saque(String cpf, double valorSaque) {
        ContaBancariaModel conta = contaBancariaController.buscaDeContasPorCPF(cpf);

        if (conta == null || !conta.isAtiva() || valorSaque <= 0 || valorSaque > conta.getSaldo()) {
            System.out.println("Erro: Saque não permitido.");
            return;
        }

        try {
            dao.abrirConexao();
            dao.getConect().setAutoCommit(false);

            double novoSaldo = conta.getSaldo() - valorSaque;

            // Atualiza Saldo
            PreparedStatement st = dao.getConect().prepareStatement("UPDATE contas SET saldo = ? WHERE id = ?");
            st.setDouble(1, novoSaldo);
            st.setInt(2, conta.getId());
            st.executeUpdate();

            // Registra Transação
            registrarOperacao(conta.getId(), valorSaque, "SAQUE", novoSaldo);

            dao.getConect().commit();
            System.out.println("Saque realizado com sucesso! Saldo: R$ " + novoSaldo);
        } catch (Exception e) {
            try { dao.getConect().rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally { dao.fecharConexao(); }
    }

    // --- Metodo de Deposito ---
    public void deposito(String cpf, double valorDeposito) {
        ContaBancariaModel conta = contaBancariaController.buscaDeContasPorCPF(cpf);

        if (conta == null || !conta.isAtiva() || valorDeposito <= 0) {
            System.out.println("Erro: Depósito inválido.");
            return;
        }

        try {
            dao.abrirConexao();
            dao.getConect().setAutoCommit(false);

            double novoSaldo = conta.getSaldo() + valorDeposito;

            PreparedStatement st = dao.getConect().prepareStatement("UPDATE contas SET saldo = ? WHERE id = ?");
            st.setDouble(1, novoSaldo);
            st.setInt(2, conta.getId());
            st.executeUpdate();

            registrarOperacao(conta.getId(), valorDeposito, "DEPOSITO", novoSaldo);

            dao.getConect().commit();
            System.out.println("Depósito realizado com sucesso! Saldo: R$ " + novoSaldo);
        } catch (Exception e) {
            try { dao.getConect().rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally { dao.fecharConexao(); }
    }

    // --- Metodo de extrato ---
    public void extrato(String cpf) {
        String sql = """
            SELECT t.tipo_transacao, t.valor, t.saldo_restante, t.data_transacao
            FROM transacoes t
            JOIN contas c ON t.conta_id = c.id
            JOIN clientes cli ON c.cliente_id = cli.id
            WHERE cli.cpf = ?
            ORDER BY t.data_transacao DESC
            """;

        try {
            dao.abrirConexao();
            PreparedStatement stmt = dao.getConect().prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n===== EXTRATO BANCÁRIO =====");
            boolean temDados = false;
            while (rs.next()) {
                temDados = true;
                System.out.println(String.format("Data: %s | %-15s | Valor: R$ %-8.2f | Saldo: R$ %.2f",
                        rs.getTimestamp("data_transacao"),
                        rs.getString("tipo_transacao"),
                        rs.getDouble("valor"),
                        rs.getDouble("saldo_restante")));
            }
            if (!temDados) System.out.println("Nenhuma transação encontrada.");
            System.out.println("============================\n");

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Erro ao gerar extrato.");
            e.printStackTrace();
        } finally {
            dao.fecharConexao();
        }
    }
}