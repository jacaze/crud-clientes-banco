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

    // --- Metodo para cadastrar cliente ---
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

    // --- Metodo para buscar cliente atráves do cpf ---
    public ClienteModel buscarClientePorCPF(String cpf) {

        String sql = "SELECT * FROM clientes WHERE cpf = ?";

        try {
            dao.abrirConexao();

            PreparedStatement stmt = dao.getConect().prepareStatement(sql);
            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                ClienteModel cliente = new ClienteModel(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        null
                );

                cliente.setId(rs.getInt("id"));

                rs.close();
                stmt.close();
                dao.fecharConexao();

                return cliente;
            }

            rs.close();
            stmt.close();
            dao.fecharConexao();

        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente");
            e.printStackTrace();
        }

        return null;
    }

    public void buscarPorCPF(String cpf) {

        ClienteModel cliente = buscarClientePorCPF(cpf);

        if (cliente != null) {
            System.out.println("Cliente encontrado!");
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Telefone: " + cliente.getTelefone());
        } else {
            System.out.println("Cliente não encontrado");
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

    // --- Metodo para excluir cliente
    public void deletarCliente(String cpf) {
        String sqlVerificaConta = """
        SELECT COUNT(*) FROM contas co
        JOIN clientes cl ON co.cliente_id = cl.id
        WHERE cl.cpf = ? AND co.ativa = true
    """;

        String sqlDeleteCliente = "DELETE FROM clientes WHERE cpf = ?";

        try {
            dao.abrirConexao();

            // --- PASSO 1: VERIFICAÇÃO ---
            PreparedStatement stmtCheck = dao.getConect().prepareStatement(sqlVerificaConta);
            stmtCheck.setString(1, cpf);
            ResultSet rs = stmtCheck.executeQuery();

            if (rs.next()) {
                int contasAtivas = rs.getInt(1);
                if (contasAtivas > 0) {
                    System.out.println("Erro: Não é possível deletar o cliente pois ele possui " + contasAtivas + " conta(s) ativa(s).");
                    System.out.println("Encerre as contas antes de remover o cliente.");
                    return;
                }
            }

            // --- PASSO 2: DELEÇÃO (Só acontece se não houver conta ativa) ---
            PreparedStatement stmtDel = dao.getConect().prepareStatement(sqlDeleteCliente);
            stmtDel.setString(1, cpf);

            int linhasAfetadas = stmtDel.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Cliente com CPF " + cpf + " foi removido com sucesso.");
            } else {
                System.out.println("Nenhum cliente encontrado com o CPF " + cpf);
            }

            stmtCheck.close();
            stmtDel.close();
            dao.fecharConexao();

        } catch (Exception e) {
            System.out.println("Erro ao processar a deleção do cliente.");
            e.printStackTrace();
        }
    }
}

