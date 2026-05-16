package controller;

import dao.BancoConnection;
import model.ClienteModel;
import model.ContaBancariaModel;
import model.TipoConta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ContaBancariaController {

    BancoConnection dao = new BancoConnection();
    ClienteController clienteController = new ClienteController();

    public void cadastrarConta(String cpf, TipoConta tipoConta){

        String sql = "INSERT INTO contas (saldo,tipo_conta,cliente_id,ativa) VALUES (?, ?, ?,?)";

        ClienteModel clienteEncontrado = clienteController.buscarClientePorCPF(cpf);

        if (clienteEncontrado == null) {
            System.out.println("CPF não encontrado");
            return;
        }

        try{
            this.dao.abrirConexao();
            PreparedStatement pstmt = this.dao.getConect().prepareStatement(sql);

            pstmt.setDouble(1,0.0);
            pstmt.setString(2,tipoConta.name());
            pstmt.setInt(3,clienteEncontrado.getId());
            pstmt.setBoolean(4,true);

            pstmt.executeUpdate();
            System.out.println("\nCriação de conta realizada com sucesso");
            pstmt.close();
            this.dao.fecharConexao();
        }catch (Exception e){
            System.out.println("Falha ao criar a conta");
            e.printStackTrace();
        }
    }

    public ContaBancariaModel buscaDeContasPorCPF(String cpf) {

        String sql = """
        SELECT 
            contas.id AS conta_id,
            contas.saldo,
            contas.tipo_conta,
            contas.ativa,

            clientes.id AS cliente_id,
            clientes.nome,
            clientes.cpf,
            clientes.telefone

        FROM contas
        JOIN clientes 
            ON contas.cliente_id = clientes.id

        WHERE clientes.cpf = ?
        """;

        try {

            dao.abrirConexao();

            PreparedStatement stmt = dao.getConect().prepareStatement(sql);
            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                // cliente
                ClienteModel cliente = new ClienteModel(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        null
                );

                cliente.setId(rs.getInt("cliente_id"));

                // conta
                ContaBancariaModel conta = new ContaBancariaModel(
                        cliente,
                        TipoConta.valueOf(rs.getString("tipo_conta"))
                );

                conta.setId(rs.getInt("conta_id"));
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setAtiva(rs.getBoolean("ativa"));

                rs.close();
                stmt.close();
                dao.fecharConexao();

                return conta;
            }

            rs.close();
            stmt.close();
            dao.fecharConexao();

        } catch (Exception e) {
            System.out.println("O CPF informado não possui conta no nosso banco");
            e.printStackTrace();
        }

        return null;
    }

    public void encerrarConta(String cpf) {

        ContaBancariaModel conta = buscaDeContasPorCPF(cpf);

        // verifica se a conta existe
        if (conta == null) {
            System.out.println("Nenhuma conta encontrada para esse CPF");
            return;
        }

        // verifica se já está encerrada
        if (!conta.isAtiva()) {
            System.out.println("A conta já está encerrada");
            return;
        }

        // não permite encerrar conta com saldo
        if (conta.getSaldo() > 0) {
            System.out.println("A conta não pode ser encerrada pois ainda possui saldo");
            return;
        }

        String sql = "UPDATE contas SET ativa = ? WHERE id = ?";

        try {

            dao.abrirConexao();

            PreparedStatement stmt = dao.getConect().prepareStatement(sql);

            stmt.setBoolean(1, false);
            stmt.setInt(2, conta.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {

                System.out.println("Conta encerrada com sucesso");

            } else {

                System.out.println("Falha ao encerrar conta");

            }

            stmt.close();
            dao.fecharConexao();

        } catch (Exception e) {

            System.out.println("Erro ao encerrar conta");
            e.printStackTrace();

        }
    }
}
