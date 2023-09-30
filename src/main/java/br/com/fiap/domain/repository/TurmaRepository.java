package br.com.fiap.domain.repository;

import br.com.fiap.Main;
import br.com.fiap.domain.entity.Turma;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TurmaRepository implements Repository<Turma, Long> {
    public Turma persist(Turma turma) {
        var sql = "BEGIN" +
                " INSERT INTO turma (NM_TURMA) " +
                "VALUES (?) " +
                "returning ID_TURMA into ?; " +
                "END;" +
                "";

        Connection connection = Main.getConnection();
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall( sql );
            cs.setString( 1, turma.getNome() );
            cs.registerOutParameter( 2, Types.BIGINT );
            cs.executeUpdate();
            turma.setId( cs.getLong( 2 ) );
            cs.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possível executar o comando!\n" + e.getMessage() );
        }
        return turma;
    }
    @Override
    public List<Turma> findAll() {
        List<Turma turmas = new ArrayList<>();

        try {
            Connection connection = Main.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT * FROM turma" );

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong( "ID_TURMA" );
                    String nome = resultSet.getString( "NM_TURMA" );
                    turmas.add( new Turma( id, nome ) );
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possivel consultar os dados!\n" + e.getMessage() );
        }
        return turmas;
    }

    @Override
    public Turma findById(Long id) {
        Turma turma = null;
        var sql = "SELECT * FROM turma  where ID_TURMA=?";
        Connection connection = Main.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    turma = new Turma(
                            resultSet.getLong( "ID_TURMA" ),
                            resultSet.getString( "NM_TURMA" )
                    );
                }
            } else {
                System.out.println( "Cliente não encontrado com o id = " + id );
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possível executar a consulta: \n" + e.getMessage() );
        }
        return turma;
    }

    @Override
    public List<Turma> findByName(String texto) {

        List<Turma> turmas = new ArrayList<>();
        var sql = "SELECT * FROM turma where UPPER(NM_TURMA) like ?";
        Connection connection = Main.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            texto = Objects.nonNull( texto ) ? texto.toUpperCase() : "";
            preparedStatement.setString( 1, "%" + texto + "%" );
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    clientes.add(
                            new Turma( resultSet.getLong( "ID_TURMA" ), resultSet.getString( "NM_TURMA" ) )
                    );
                }
            } else {
                System.out.println( "Cliente não encontrado com o nome = " + texto );
            }
            resultSet.close(); preparedStatement.close(); connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possível executar a consulta: \n" + e.getMessage() );
        }
        return turmas;
    }


    @Override
    public Turma persist(Turma turma) {
        return null;
    }
}
