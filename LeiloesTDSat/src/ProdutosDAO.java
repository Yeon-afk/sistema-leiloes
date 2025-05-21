/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
    String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
    
    conn = new conectaDAO().connectDB();

    try {
        prep = conn.prepareStatement(sql);
        prep.setString(1, produto.getNome());
        prep.setInt(2, produto.getValor());
        prep.setString(3, produto.getStatus());
        
        prep.executeUpdate();
        JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");

    } catch (Exception erro) {
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + erro.getMessage());
    }
           
    }
    
    public ArrayList<ProdutosDTO> listarProdutos() {
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    String sql = "SELECT * FROM produtos";

    try (Connection conn = new conectaDAO().connectDB();
         PreparedStatement prep = conn.prepareStatement(sql);
         ResultSet resultset = prep.executeQuery()) {

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));
            listagem.add(produto);
        }

    } catch (Exception erro) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + erro.getMessage());
    }

    return listagem;
}
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
    ArrayList<ProdutosDTO> listaVendidos = new ArrayList<>();
    String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";

    try (Connection conn = new conectaDAO().connectDB();
         PreparedStatement prep = conn.prepareStatement(sql);
         ResultSet resultset = prep.executeQuery()) {

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));

            listaVendidos.add(produto);
        }

    } catch (Exception erro) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + erro.getMessage());
    }

    return listaVendidos;
}
    
   public void venderProduto(int id) {
    String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
    
    try (Connection conn = new conectaDAO().connectDB();
         PreparedStatement prep = conn.prepareStatement(sql)) {
        
        prep.setInt(1, id);
        int rowsAffected = prep.executeUpdate();
        
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado ou já vendido.");
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
    }
}      
}

