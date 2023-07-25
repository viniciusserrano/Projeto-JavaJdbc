package br.com.alura.bytebank.application.repository.entity;

import br.com.alura.bytebank.application.repository.dto.DadosCadastroCliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
@Getter
@Setter
@AllArgsConstructor
public class Cliente {

    private String nome;
    private String cpf;
    private String email;

    public Cliente(DadosCadastroCliente dados) {
        this.nome = dados.nome();
        this.cpf = dados.cpf();
        this.email = dados.email();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return cpf.equals(cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

//    public String getNome() {
//        return nome;
//    }
//
//    public String getCpf() {
//        return cpf;
//    }
//
//    public String getEmail() {
//        return email;
//    }

}
