package com.example.fqa.sqlserversample.Models;

import java.util.Locale;

public class Produto {
    private int id, quantidade;
    private String produto;
    private double valor;

    public Produto(int id, String produto, int quantidade, double valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.produto = produto;
        this.valor = valor;
    }
    public Produto(){
        this(0, "", 0, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    public String getUtil(int index){
        switch (index){
            case 1:
                return String.valueOf(id);
            case 2:
                return produto;
            case 3:
                return String.valueOf(quantidade);
            case 4:
                return String.valueOf(valor);
            default:
                throw new IndexOutOfBoundsException("Index fora do Range");
        }
    }
    public String getAtr(int index){
        switch (index){
            case 1:
                return "ID";
            case 2:
                return "PRODUTO";
            case 3:
                return "QUANTIDADE";
            case 4:
                return "VALOR";
            default:
                throw new IndexOutOfBoundsException("Index fora do Range");
        }
    }

    @Override
    public String toString() {
        return String.format(
                Locale.ENGLISH,
                "ID: %s\n" +
                "Produto: %s\n" +
                "Quantidade: %d\n" +
                "Preço: %.2f",
                id, produto, quantidade, valor
        );
    }
}
