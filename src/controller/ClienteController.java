package controller;

import dao.BancoConnection;
import model.ClienteModel;
import model.EnderecoModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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

    public List<ClienteModel> listagemClientes(){
        List<ClienteModel> lista = new ArrayList<>();

        String sql = "SELECT c.id, c.cpf, c.nome, c.telefone, " +
                "e.logradouro, e.numero, e.cep, e.municipio " +
                "FROM clientes c " +
                "JOIN endereco e ON c.endereco_id = e.id";

        try{
           dao.abrirConexao();

            PreparedStatement stmt = dao.getConect().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n---- Clientes Cadastrados ----");

            while (rs.next()){

                //Cria o endereço na lista
                EnderecoModel endereco = new EnderecoModel(
                        rs.getString("logradouro"),
                        rs.getInt("numero"),
                        rs.getString("cep"),
                        rs.getString("municipio")
                );

                //Cria o Cliente na lista
                ClienteModel cliente = new ClienteModel(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        endereco
                );

                //setar o id do endereco
                cliente.setId(rs.getInt("id"));

                lista.add(cliente);
            }
            rs.close();
            stmt.close();
            dao.fecharConexao();

        }catch (Exception e){
            System.out.println("Erro ao listar clientes");
            e.printStackTrace();
        }
        return lista;
    }
}

