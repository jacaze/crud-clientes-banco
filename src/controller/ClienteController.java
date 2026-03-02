package controller;

import dao.BancoConnection;
import model.ClienteModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


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
            pstmt.close();
            this.dao.fecharConexao();
        }catch (Exception e){
            System.out.println("Falha ao cadastrar o cliente");
            e.printStackTrace();
        }
    }

    public void buscarPorCPF(String cpf){

        String sql =  "SELECT * FROM CLIENTES WHERE CPF = ?";

        try{
            this.dao.abrirConexao();
            PreparedStatement stmt = dao.getConect().prepareStatement(sql);
            stmt.setString(1,cpf);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                System.out.println("Cliente encontrado!");
                System.out.println("Nome: "+rs.getString("nome"));
                System.out.println("Telefone: " +rs.getString("telefone"));
            }else{
                System.out.println("\nCliente não encontrado");
            }
            rs.close();
            stmt.close();
            this.dao.fecharConexao();
        }catch (Exception e){
            System.out.println("Falha ao buscar o cliente");
            e.printStackTrace();
        }
    }
}

