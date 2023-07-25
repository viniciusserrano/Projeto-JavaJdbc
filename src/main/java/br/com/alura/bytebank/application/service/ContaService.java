package br.com.alura.bytebank.application.service;

import br.com.alura.bytebank.application.repository.dto.DadosAberturaConta;
import br.com.alura.bytebank.application.repository.dao.ConexaoBancoDados;
import br.com.alura.bytebank.application.repository.dao.ContaDao;
import br.com.alura.bytebank.application.repository.entity.Conta;
import br.com.alura.bytebank.application.service.validator.ContaValidator;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Set;

public class ContaService {

    private ConexaoBancoDados connection;

    public ContaService() {
        this.connection = new ConexaoBancoDados();
    }

    public Set<Conta> listarContasAbertas() {
        Connection conn = connection.recuperarConexao();
        return new ContaDao(conn).listar();
    }

    public BigDecimal consultarSaldo(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        return conta.getSaldo();
    }

    public void abrir(DadosAberturaConta dadosDaConta) {
        Connection conn = connection.recuperarConexao();
        new ContaDao(conn).salvar(dadosDaConta);
    }

    public void realizarSaque(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);

        ContaValidator.validarSaque(valor, conta);

        BigDecimal novoValor = conta.getSaldo().subtract(valor);
        alterar(conta, novoValor);
    }

    public void realizarDeposito(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);

        ContaValidator.validarValorPositivo(valor);

        BigDecimal novoValor = conta.getSaldo().add(valor);
        alterar(conta, novoValor);
    }

    public void realizarTransferencia(Integer numeroDaContaOrigem, Integer numeroDaContaDestino, BigDecimal valor) {
        this.realizarSaque(numeroDaContaOrigem, valor);
        this.realizarDeposito(numeroDaContaDestino, valor);
    }

    public void encerrarLogico(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);

        ContaValidator.validarContaSemSaldo(conta);

        Connection conn = connection.recuperarConexao();

        new ContaDao(conn).alterarLogico(numeroDaConta);
    }

    private Conta buscarContaPorNumero(Integer numero) {
        Connection conn = connection.recuperarConexao();
        Conta conta = new ContaDao(conn).listarPorNumero(numero);
        ContaValidator.validarContaExistente(conta);
        return conta;
    }

    private void alterar(Conta conta, BigDecimal valor) {
        Connection conn = connection.recuperarConexao();
        new ContaDao(conn).alterar(conta.getNumero(), valor);
    }

}
