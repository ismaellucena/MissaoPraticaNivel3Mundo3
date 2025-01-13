/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import model.PessoaFisica;
import model.util.ConectorBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {

    // Método para incluir uma pessoa física no banco
    public void incluir(PessoaFisica pf) {
        String sqlPessoa = "INSERT INTO Pessoa (nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPessoaFisica = "INSERT INTO PessoaFisica (id, cpf) VALUES (?, ?)";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtPessoaFisica = conn.prepareStatement(sqlPessoaFisica)) {

            conn.setAutoCommit(false); // Inicia uma transação

            // Insere dados na tabela Pessoa
            stmtPessoa.setString(1, pf.getNome());
            stmtPessoa.setString(2, pf.getLogradouro());
            stmtPessoa.setString(3, pf.getCidade());
            stmtPessoa.setString(4, pf.getEstado());
            stmtPessoa.setString(5, pf.getTelefone());
            stmtPessoa.setString(6, pf.getEmail());
            stmtPessoa.executeUpdate();

            // Recupera o ID gerado
            ResultSet generatedKeys = stmtPessoa.getGeneratedKeys();
            if (generatedKeys.next()) {
                pf.setId(generatedKeys.getInt(1));
            }

            // Insere dados na tabela PessoaFisica
            stmtPessoaFisica.setInt(1, pf.getId());
            stmtPessoaFisica.setString(2, pf.getCpf());
            stmtPessoaFisica.executeUpdate();

            conn.commit(); // Finaliza a transação
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para listar todas as pessoas físicas
    public List<PessoaFisica> listar() {
        List<PessoaFisica> pessoas = new ArrayList<>();
        String sql = "SELECT p.*, pf.cpf FROM Pessoa p INNER JOIN PessoaFisica pf ON p.id = pf.id";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PessoaFisica pf = new PessoaFisica(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("logradouro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cpf")
                );
                pessoas.add(pf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pessoas;
    }

    // Método para buscar pessoa física por ID
    public PessoaFisica buscarPorId(int id) {
        String sql = "SELECT p.*, pf.cpf FROM Pessoa p INNER JOIN PessoaFisica pf ON p.id = pf.id WHERE p.id = ?";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PessoaFisica(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("logradouro"),
                            rs.getString("cidade"),
                            rs.getString("estado"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getString("cpf")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para excluir uma pessoa física por ID
    public void excluir(int id) {
        String sqlPessoaFisica = "DELETE FROM PessoaFisica WHERE id = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE id = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoaFisica = conn.prepareStatement(sqlPessoaFisica);
             PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa)) {

            conn.setAutoCommit(false); // Inicia uma transação

            stmtPessoaFisica.setInt(1, id);
            stmtPessoaFisica.executeUpdate();

            stmtPessoa.setInt(1, id);
            stmtPessoa.executeUpdate();

            conn.commit(); // Finaliza a transação
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
