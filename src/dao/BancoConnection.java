package dao;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Properties;
import java.io.FileInputStream;

public class BancoConnection {
    private Connection conect;
    private Statement state;
    private ResultSet result;


    public Connection getConect() {
        return conect;
    }

    public void setConect(Connection conect) {
        this.conect = conect;
    }

    public Statement getState() {
        return state;
    }

    public void setState(Statement state) {
        this.state = state;
    }

    public ResultSet getResult() {
        return result;
    }

    public void setResult(ResultSet result) {
        this.result = result;
    }

    public void abrirConexao(){
        try{
            Properties props = new Properties();
            props.load(new FileInputStream("db.properties"));

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            Class.forName("org.postgresql.Driver");

            this.conect = DriverManager.getConnection(url,user,password);
            System.out.println("\nConexão realizada com sucesso ");

        } catch (Exception e) {
            System.out.println("\nErro na conexão com o banco de dados !");
            e.printStackTrace();
        }

        if(this.conect!=null) {
            try {
                this.state = this.conect.createStatement(1004, 1007);
            } catch (Exception e) {
                System.out.println("\nFalha ao iniciar o State");
                e.printStackTrace();
            }
        }
    }
    public void fecharConexao() {
        if (this.conect != null) {
            try {
                this.conect.close();
                System.out.println("\nConexão com o Postgre fechada");
            } catch (Exception e) {
                System.out.println("\nOcorreu um erro ao fechar a conexão");
                e.printStackTrace();
            }
        }

    }

}



