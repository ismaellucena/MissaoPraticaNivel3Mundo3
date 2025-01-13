/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class PessoaFisica extends Pessoa {
    private String cpf;

    // Construtor padrão
    public PessoaFisica() {}

    // Construtor com parâmetros
    public PessoaFisica(int id, String nome, String logradouro, String cidade, String estado, String telefone, String email, String cpf) {
        super(id, nome, logradouro, cidade, estado, telefone, email);
        this.cpf = cpf;
    }

    // Getter para CPF
    public String getCpf() {
        return cpf;
    }

    // Setter para CPF com validação (opcional)
    public void setCpf(String cpf) {
        if (cpf == null || cpf.length() != 14) { // Exemplo de validação simples
            throw new IllegalArgumentException("CPF inválido. Deve ter 14 caracteres no formato 000.000.000-00.");
        }
        this.cpf = cpf;
    }
     // Sobrescreve o método exibir para adicionar o CPF
    @Override
    public void exibir() {
        super.exibir(); // Exibe os atributos herdados de Pessoa
        System.out.println("CPF: " + cpf);
    }
    // Sobrescreve toString para representação textual
    @Override
    public String toString() {
        return super.toString() + ", CPF: " + cpf;
    }
    
}



