package controller;

import dao.BancoConnection;
import model.EnderecoModel;

import java.sql.PreparedStatement;

public class EnderecoController {

    BancoConnection dao = new BancoConnection();

    // --- Metodo para cadastrar endereço ---
    public void cadastrarEndereco(EnderecoModel endereco) {

        String sql = "INSERT INTO endereco (logradouro, numero, cep, municipio) VALUES (?, ?, ?, ?)";

        try {
            this.dao.abrirConexao();

            PreparedStatement pstmt = this.dao.getConect().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, endereco.getLogradouro());
            pstmt.setInt(2, endereco.getNumero());
            pstmt.setString(3, endereco.getCep());
            pstmt.setString(4, endereco.getMunicipio());

            pstmt.execute();

            var rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                endereco.setId(rs.getInt(1)); // Coloca o ID real dentro do objeto
            }

            System.out.println("\n ---- Endereço cadastrado com sucesso (ID: " + endereco.getId() + ") ---- ");

            this.dao.fecharConexao();
        } catch (Exception e) {
            System.out.println("\nFalha ao cadastrar o Endereço");
            e.printStackTrace();
        }
    }

}
