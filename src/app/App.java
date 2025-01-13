/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import model.PessoaFisica;
import model.PessoaJuridica;
import model.dao.PessoaFisicaDAO;
import model.dao.PessoaJuridicaDAO;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        PessoaFisicaDAO fisicaDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO juridicaDAO = new PessoaJuridicaDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Incluir Pessoa Física");
            System.out.println("2. Listar Pessoas Físicas");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    System.out.println("Digite os dados da pessoa física:");
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Logradouro: ");
                    String logradouro = scanner.nextLine();
                    System.out.print("Cidade: ");
                    String cidade = scanner.nextLine();
                    System.out.print("Estado: ");
                    String estado = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();

                    PessoaFisica pf = new PessoaFisica(0, nome, logradouro, cidade, estado, telefone, email, cpf);
                    fisicaDAO.incluir(pf);
                    System.out.println("Pessoa física incluída com sucesso!");
                    break;

                case 2:
                    System.out.println("Lista de Pessoas Físicas:");
                    fisicaDAO.listar().forEach(PessoaFisica::exibir);
                    break;

                case 3:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}

