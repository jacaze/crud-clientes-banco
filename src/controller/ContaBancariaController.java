package controller;

import dao.BancoConnection;
import model.ClienteModel;
import model.TipoConta;

import java.sql.PreparedStatement;

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

}
