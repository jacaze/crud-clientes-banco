package model;

public class EnderecoModel {
    private int id;
    private String logradouro;
    private int numero;
    private String cep;
    private String municipio;

    public EnderecoModel(String logradouro, int numero, String cep, String municipio) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.municipio = municipio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setCidade(String cidade) {
        this.municipio = cidade;
    }


}
