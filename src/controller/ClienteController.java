package controller;

import dao.BancoConnection;
import model.ClienteModel;

import java.sql.PreparedStatement;


public class ClienteController {

    BancoConnection dao = new BancoConnection();

    public void cadastrarCliente(ClienteModel cliente){

        String sql = "INSERT INTO clientes (cpf, nome, telefone, endereco_id) VALUES (?, ?, ?, ?)";

        try{
            this.dao.abrirConexao();
            PreparedStatement pstmt = this.dao.getConect().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setString(1,cliente.getCpf());
            pstmt.setString(2,cliente.getNome());
            pstmt.setString(3,cliente.getTelefone());
            pstmt.setInt(4,cliente.getEndereco().getId());

            pstmt.execute();
            System.out.println("\n ---- Endereço cadastrado com sucesso (ID: " + cliente.getId() + ") ----");
            this.dao.fecharConexao();
        }catch (Exception e){
            System.out.println("Falha ao cadastrar o cliente");
            e.printStackTrace();
        }
    }
}

