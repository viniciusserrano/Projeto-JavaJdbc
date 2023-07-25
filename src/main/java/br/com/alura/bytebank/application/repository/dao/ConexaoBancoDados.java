package br.com.alura.bytebank.application.repository.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexaoBancoDados {
    public Connection recuperarConexao() {
        try {
            return createDataSource().getConnection();
        }catch (SQLException e){
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }

    }

    private HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/byte_bank");
        config.setUsername("root");
        config.setPassword("1234");
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }

}
