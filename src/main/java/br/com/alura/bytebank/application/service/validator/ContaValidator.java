package br.com.alura.bytebank.application.service.validator;

import br.com.alura.bytebank.application.repository.entity.Conta;
import br.com.alura.bytebank.application.service.RegraDeNegocioException;

import java.math.BigDecimal;

public class ContaValidator {

    public static void validarSaque(BigDecimal valor, Conta conta) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do saque deve ser superior a zero!");
        }

        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new RegraDeNegocioException("Saldo insuficiente!");
        }

        if (!conta.getStatus()) {
            throw new RegraDeNegocioException("Sua conta precisa estar ativa para realizar saque");
        }
    }

    public static void validarValorPositivo(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do deposito deve ser superior a zero!");
        }
    }

    public static void validarContaSemSaldo(Conta conta) {
        if (conta.possuiSaldo()) {
            throw new RegraDeNegocioException("Conta não pode ser encerrada pois ainda possui saldo!");
        }
    }

    public static void validarContaExistente(Conta conta) {
        if (conta == null) {
            throw new RegraDeNegocioException("Não existe conta cadastrada com esse número!");
        }
    }

}
