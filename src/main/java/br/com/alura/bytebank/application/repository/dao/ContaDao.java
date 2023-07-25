package br.com.alura.bytebank.application.repository.dao;

import br.com.alura.bytebank.application.repository.entity.Cliente;
import br.com.alura.bytebank.application.repository.dto.DadosCadastroCliente;
import br.com.alura.bytebank.application.repository.entity.Conta;
import br.com.alura.bytebank.application.repository.dto.DadosAberturaConta;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDao {
    Connection conn;

    public ContaDao(Connection connection) {
        this.conn = connection;
    }

    public void salvar(DadosAberturaConta dadosDaConta) {
        var cliente = new Cliente(dadosDaConta.dadosCliente());
        var conta = new Conta(dadosDaConta.numero(), BigDecimal.ZERO, cliente, true);

        String sql = "INSERT INTO conta (numero, saldo, cliente_nome, cliente_cpf, cliente_email, status)" + "VALUES (?, ? , ?, ?, ?, ?)";

        try (PreparedStatement prepareStatement = conn.prepareStatement(sql);) {
            prepareStatement.setInt(1, conta.getNumero());
            prepareStatement.setBigDecimal(2, BigDecimal.ZERO);
            prepareStatement.setString(3, dadosDaConta.dadosCliente().nome());
            prepareStatement.setString(4, dadosDaConta.dadosCliente().cpf());
            prepareStatement.setString(5, dadosDaConta.dadosCliente().email());
            prepareStatement.setBoolean(6, true);

            prepareStatement.execute();
            prepareStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Conta> listar() {
        String sql = "SELECT * FROM conta WHERE status = true";

        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Set<Conta> contas = new HashSet<>();
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer numeroDaConta = resultSet.getInt(1);
                BigDecimal saldoDaConta = resultSet.getBigDecimal(2);
                String nome = resultSet.getString(3);
                String cpf = resultSet.getString(4);
                String email = resultSet.getString(5);
                Boolean status = resultSet.getBoolean(6);

                DadosCadastroCliente dadosCadastroCliente = new DadosCadastroCliente(nome, cpf, email);
                Cliente cliente = new Cliente(dadosCadastroCliente);
                contas.add(new Conta(numeroDaConta, saldoDaConta, cliente, status));
            }
            preparedStatement.close();
            resultSet.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contas;
    }

    public Conta listarPorNumero(Integer numero) {
        String sql = "SELECT * FROM conta WHERE numero = ? AND status = true";

        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Conta conta = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, numero);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer numeroRecuperado = resultSet.getInt(1);
                BigDecimal saldoDaConta = resultSet.getBigDecimal(2);
                String nome = resultSet.getString(3);
                String cpf = resultSet.getString(4);
                String email = resultSet.getString(5);
                Boolean status = resultSet.getBoolean(6);

                DadosCadastroCliente dadosCadastroCliente = new DadosCadastroCliente(nome, cpf, email);
                Cliente cliente = new Cliente(dadosCadastroCliente);

                conta = new Conta(numeroRecuperado, saldoDaConta, cliente, status);
            }
            preparedStatement.close();
            resultSet.close();
            conn.close();
        } catch (SQLException e) {
            throw   new RuntimeException(e);
        }
        return conta;
    }

    public void alterar(Integer numero, BigDecimal valorSaldo) {
        PreparedStatement preparedStatement;

        String sql = "UPDATE conta SET saldo = ? WHERE numero = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setBigDecimal(1, valorSaldo);
            preparedStatement.setInt(2, numero);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Integer numeroDaConta) {
        String sql = "DELETE FROM conta WHERE numero = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, numeroDaConta);

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void alterarLogico (Integer numeroDaConta) {
        PreparedStatement preparedStatement;

        String sql = "UPDATE conta SET status = false WHERE numero = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, numeroDaConta);

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
