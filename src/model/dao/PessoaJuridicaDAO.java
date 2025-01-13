/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import model.PessoaJuridica;
import model.util.ConectorBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAO {

    // Método para incluir uma pessoa jurídica no banco
    public void incluir(PessoaJuridica pj) {
        String sqlPessoa = "INSERT INTO Pessoa (nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPessoaJuridica = "INSERT INTO PessoaJuridica (id, cnpj) VALUES (?, ?)";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica)) {

            conn.setAutoCommit(false);

            // Insere dados na tabela Pessoa
            stmtPessoa.setString(1, pj.getNome());
            stmtPessoa.setString(2, pj.getLogradouro());
            stmtPessoa.setString(3, pj.getCidade());
            stmtPessoa.setString(4, pj.getEstado());
            stmtPessoa.setString(5, pj.getTelefone());
            stmtPessoa.setString(6, pj.getEmail());
            stmtPessoa.executeUpdate();

            // Recupera o ID gerado
            ResultSet generatedKeys = stmtPessoa.getGeneratedKeys();
            if (generatedKeys.next()) {
                pj.setId(generatedKeys.getInt(1));
            }

            // Insere dados na tabela PessoaJuridica
            stmtPessoaJuridica.setInt(1, pj.getId());
            stmtPessoaJuridica.setString(2, pj.getCnpj());
            stmtPessoaJuridica.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para listar todas as pessoas jurídicas
    public List<PessoaJuridica> listar() {
        List<PessoaJuridica> pessoas = new ArrayList<>();
        String sql = "SELECT p.*, pj.cnpj FROM Pessoa p INNER JOIN PessoaJuridica pj ON p.id = pj.id";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PessoaJuridica pj = new PessoaJuridica(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("logradouro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cnpj")
                );
                pessoas.add(pj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pessoas;
    }

    // Método para buscar uma pessoa jurídica pelo ID
    public PessoaJuridica buscarPorId(int id) {
        String sql = "SELECT p.*, pj.cnpj FROM Pessoa p INNER JOIN PessoaJuridica pj ON p.id = pj.id WHERE p.id = ?";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PessoaJuridica(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("logradouro"),
                            rs.getString("cidade"),
                            rs.getString("estado"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getString("cnpj")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para excluir uma pessoa jurídica pelo ID
    public void excluir(int id) {
        String sqlPessoaJuridica = "DELETE FROM PessoaJuridica WHERE id = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE id = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica);
             PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa)) {

            conn.setAutoCommit(false);

            stmtPessoaJuridica.setInt(1, id);
            stmtPessoaJuridica.executeUpdate();

            stmtPessoa.setInt(1, id);
            stmtPessoa.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
