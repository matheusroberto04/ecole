package br.com.fiap.domain.repository;

import br.com.fiap.Main;
import br.com.fiap.domain.entity.Instrutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InstrutorRepository implements Repository<Instrutor, Long> {
    public Instrutor persist(Instrutor instrutor) {
        var sql = "BEGIN" +
                " INSERT INTO cliente (NM_CLIENTE) " +
                "VALUES (?) " +
                "returning ID_CLIENTE into ?; " +
                "END;" +
                "";

        Connection connection = Main.getConnection();
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall( sql );
            cs.setString( 1, instrutor.getNome() );
            cs.registerOutParameter( 2, Types.BIGINT );
            cs.executeUpdate();
            instrutor.setId( cs.getLong( 2 ) );
            cs.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possível executar o comando!\n" + e.getMessage() );
        }
        return instrutor;
    }
    @Override
    public List<Instrutor> findAll() {
        List<Instrutor> instrutors = new ArrayList<>();

        try {
            Connection connection = Main.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT * FROM instrutor" );

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong( "ID_INSTRUTOR" );
                    String nome = resultSet.getString( "NM_INSTRUTOR" );
                    //Adicionando clientes na coleção
                    instrutors.add( new Instrutor( id, nome ) );
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possivel consultar os dados!\n" + e.getMessage() );
        }
        return instrutors;
    }


    @Override
    public Instrutor findById(Long id) {
        Instrutor instrutor = null;
        var sql = "SELECT * FROM instrutor where ID_INSTRUTOR=?";
        Connection connection = Main.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    instrutor = new Instrutor(
                            resultSet.getLong( "ID_INSTRUTOR" ),
                            resultSet.getString( "NM_INSTRUTOR" )
                    );
                }
            } else {
                System.out.println( "Instrutor não encontrado com o id = " + id );
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possível executar a consulta: \n" + e.getMessage() );
        }
        return instrutor;
    }


    @Override
    public List<Instrutor> findByName(String texto) {
        List<Instrutor> instrutors = new ArrayList<>();
        var sql = "SELECT * FROM instrutor where UPPER(NM_INSTRUTOR) like ?";
        Connection connection = Main.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            texto = Objects.nonNull( texto ) ? texto.toUpperCase() : "";
            preparedStatement.setString( 1, "%" + texto + "%" );
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    instrutors.add(
                            new Instrutor( resultSet.getLong( "ID_CLIENTE" ), resultSet.getString( "NM_CLIENTE" ) )
                    );
                }
            } else {
                System.out.println( "Instrutor não encontrado com o nome = " + texto );
            }
            resultSet.close(); preparedStatement.close(); connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possível executar a consulta: \n" + e.getMessage() );
        }
        return instrutors;
    }

    @Override
    public Instrutor persist(Instrutor instrutor) {
        return null;
    }
}
