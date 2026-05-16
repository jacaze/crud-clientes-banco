package model;

import java.time.LocalDateTime;

public class TransacaoModel {
    private int idTransacao;
    private ContaBancariaModel conta;
    private double valor;
    private TipoTransacao tipoTransacao;
    private double saldoRestante;
    private LocalDateTime dataTransacao;

    public TransacaoModel(int idTransacao, ContaBancariaModel conta, double valor, TipoTransacao tipoTransacao, double saldoRestante, LocalDateTime dataTransacao) {
        this.idTransacao = idTransacao;
        this.conta = conta;
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
        this.saldoRestante = saldoRestante;
        this.dataTransacao = dataTransacao;
    }

    public int getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(int idTransacao) {
        this.idTransacao = idTransacao;
    }

    public ContaBancariaModel getConta() {
        return conta;
    }

    public void setConta(ContaBancariaModel conta) {
        this.conta = conta;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public LocalDateTime getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public double getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(double saldoRestante) {
        this.saldoRestante = saldoRestante;
    }
}
