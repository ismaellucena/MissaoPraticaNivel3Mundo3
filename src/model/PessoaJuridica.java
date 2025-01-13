/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class PessoaJuridica extends Pessoa {
    private String cnpj;

    // Construtor padrão
    public PessoaJuridica() {}

    // Construtor com parâmetros
    public PessoaJuridica(int id, String nome, String logradouro, String cidade, String estado, String telefone, String email, String cnpj) {
        super(id, nome, logradouro, cidade, estado, telefone, email);
        this.cnpj = cnpj;
    }

    // Getter para CNPJ
    public String getCnpj() {
        return cnpj;
    }

    // Setter para CNPJ com validação (opcional)
    public void setCnpj(String cnpj) {
        if (cnpj == null || cnpj.length() != 18) { // Exemplo de validação simples
            throw new IllegalArgumentException("CNPJ inválido. Deve ter 18 caracteres no formato 00.000.000/0000-00.");
        }
        this.cnpj = cnpj;
    }
    // Sobrescreve toString para representação textual
    @Override
    public String toString() {
        return super.toString() + ", CNPJ: " + cnpj;
    }
}


