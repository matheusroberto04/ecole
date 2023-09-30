package br.com.fiap.domain.repository;

import br.com.fiap.Main;
import br.com.fiap.domain.entity.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AlunoRepository implements Repository<Aluno, Long> {
    public Aluno persist(Aluno aluno) {
        Connection connection = Main.getConnection();
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall(sql);
            cs.setString(1, aluno.getNome());
            cs.registerOutParameter(2, Types.BIGINT);
            cs.executeUpdate();
            aluno.setId(cs.getLong(2));
            cs.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Nao foi possivel executar! \n" + e.getMessage());
        }
        return aluno;
    }
    @Override
    public List<Aluno> findAll() {
        List<Aluno> alunos = new ArrayList<>();

        try {
            Connection connection = Main.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT * FROM aluno" );

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong( "ID_ALUNO" );
                    String nome = resultSet.getString( "NM_ALUNO" );
                    alunos.add( new Aluno( id, nome ) );
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possivel consultar os dados!\n" + e.getMessage() );
        }
        return alunos;
    }

    @Override
    public Aluno findById(Long id) {
        public Aluno findById(Long, id) {
            Aluno aluno = null;
            var sql = "SELECT * FROM aluno where ID_ALUNO=?";
            Connection connection = Main.getConnection();

            try {
                PreparedStatement preparedStatement = connection.prepareStatement( sql );
                preparedStatement.setLong( 1, id );
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.isBeforeFirst()) {
                    while (resultSet.next()) {
                        aluno = new Aluno(
                                resultSet.getLong( "ID_ALUNO" ),
                                resultSet.getString( "NM_ALUNO" )
                        );
                    }
                } else {
                    System.out.println( "Aluno não encontrado com o id = " + id );
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                System.err.println( "Não foi possível executar a consulta: \n" + e.getMessage() );
            }
            return aluno;
        }
    }

    @Override
    public List<Aluno> findByName(String texto) {
        List<Aluno> alunos = new ArrayList<>();
        var sql = "SELECT * FROM aluno where UPPER(NM_ALUNO) like ?";
        Connection connection = Main.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            texto = Objects.nonNull( texto ) ? texto.toUpperCase() : "";
            preparedStatement.setString( 1, "%" + texto + "%" );
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    alunos.add(
                            new Aluno( resultSet.getLong( "ID_ALUNO" ), resultSet.getString( "NM_CLIENTE" ) )
                    );
                }
            } else {
                System.out.println( "Aluno não encontrado com o nome = " + texto );
            }
            resultSet.close(); preparedStatement.close(); connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possível executar a consulta: \n" + e.getMessage() );
        }
        return alunos;
    }

    @Override
    public Aluno persist(Aluno aluno) {
        return aluno;
    }
}
