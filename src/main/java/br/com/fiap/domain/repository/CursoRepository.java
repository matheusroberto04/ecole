package br.com.fiap.domain.repository;

import br.com.fiap.Main;
import br.com.fiap.domain.entity.Aluno;
import br.com.fiap.domain.entity.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CursoRepository implements Repository<Curso, Long> {
    public Curso persist(Curso curso){
        var sql = "BEGIN" +
                " INSERT INTO curso (NM_CURSO) " +
                "VALUES (?) " +
                "returning ID_CURSO into ?; " +
                "END;" +
                "";
        Connection connection = Main.getConnection();
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall( sql );
            cs.setString(1, curso.getNome());
            cs.registerOutParameter( 2 );
            cs.executeUpdate();
            curso.setId(cs.getLong( 2 ));
            cs.close();
            connection.close();
        }catch ( SQLException e ){
            System.err.println("Nao foi possivel executar! \n" + e.getMessage());
        }
        return curso;
    }
    @Override
    public List<Curso> findAll() {
        public List<Curso> findAll() {

            List<Curso> cursos = new ArrayList<>();

            try {
                Connection connection = Main.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery( "SELECT * FROM curso" );

                if (resultSet.isBeforeFirst()) {
                    while (resultSet.next()) {
                        Long id = resultSet.getLong( "ID_CURSO" );
                        String nome = resultSet.getString( "NM_CURSO" );
                        cursos.add( new Curso());
                    }
                }

                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                System.err.println( "Não foi possivel consultar os dados!\n" + e.getMessage() );
            }
            return cursos;
        }
    }

    @Override
    public Curso findById(Long id) {
       Curso curso = null;
        var sql = "SELECT * FROM curso where ID_CURSO=?";
        Connection connection = Main.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    curso = new Curso();
                }
            } else {
                System.out.println( "Curso não encontrado com o id = " + id );
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possível executar a consulta: \n" + e.getMessage() );
        }
        return curso;
    }

    @Override
    public List<Curso> findByName(String texto) {
        List<Curso> cursos = new ArrayList<>();
        var sql = "SELECT * FROM curso where UPPER(NM_CURSO) like ?";
        Connection connection = Main.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            texto = Objects.nonNull( texto ) ? texto.toUpperCase() : "";
            preparedStatement.setString( 1, "%" + texto + "%" );
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    cursos.add(
                            new Curso( resultSet.getLong( "ID_CURSO" ), resultSet.getString( "NM_CURSO ")
                }
            } else {
                System.out.println( "Curso não encontrado com o nome = " + texto );
            }
            resultSet.close(); preparedStatement.close(); connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possível executar a consulta: \n" + e.getMessage() );
        }
        return cursos;
    }

    @Override
    public Curso persist(Curso curso) {

        return null;
    }
}
