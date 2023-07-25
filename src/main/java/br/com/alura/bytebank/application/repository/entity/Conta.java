package br.com.alura.bytebank.application.repository.entity;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;
@Getter
public class Conta {

    private Integer numero;
    private BigDecimal saldo;
    private Cliente titular;
    private Boolean status;
    public Conta(Integer numero, BigDecimal saldo, Cliente titular, Boolean status) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
        this.status = status;
    }

    public boolean possuiSaldo() {
        return this.saldo.compareTo(BigDecimal.ZERO) != 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return numero.equals(conta.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "Conta{" +
                "numero='" + numero + '\'' +
                ", saldo=" + saldo +
                ", titular=" + titular +
                '}';
    }

}
