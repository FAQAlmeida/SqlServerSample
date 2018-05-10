package com.example.fqa.sqlserversample.Connection;

import com.example.fqa.sqlserversample.Models.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Querys {
    private Connection conexao;
    private PreparedStatement comandoSql;

    public ArrayList<Produto> Pesquisar() throws SQLException, ClassNotFoundException{
        String sql = "Select * from produtos";
        ArrayList<Produto> listaProduto = new ArrayList<Produto>();
        ConnectionClass conn = new ConnectionClass();
        this.conexao= conn.getConnection();
        comandoSql = conexao.prepareStatement(sql);

        ResultSet rs = comandoSql.executeQuery();
        while (rs.next()) {
            Produto produto = new Produto(
                    rs.getInt("id_prod"),
                    rs.getString("nome_prod"),
                    rs.getInt("quant_prod"),
                    rs.getDouble("valor_prod")
            );
            listaProduto.add(produto);
        }
        rs.close();
        comandoSql.close();
        this.conexao.close();
        return listaProduto;
    }

    public static void main(String[] args) {
        try {
            ArrayList<Produto> produtos = new Querys().Pesquisar();
            for(Produto produto : produtos){
                System.out.println(produto.toString());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
