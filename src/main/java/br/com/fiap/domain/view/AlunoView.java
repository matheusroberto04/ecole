package br.com.fiap.domain.view;

import br.com.fiap.domain.entity.Aluno;
import br.com.fiap.domain.service.AlunoService;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

public class AlunoView implements View<Aluno, Long> {

    AlunoService service = new AlunoService();

    @Override
    public List<Aluno> findAll() {
        return service.findAll();
    }

    @Override
    public Aluno findById(Long id) {
        Long identificador = Long.valueOf(JOptionPane.showInputDialog("ID do Aluno", Objects.nonNull(id) ? id : 0));
        return service.findById(identificador);
    }

    @Override
    public List<Aluno> findByName(String texto) {
        String nome = service.valido( texto ) ? texto : JOptionPane.showInputDialog( "Nome do Aluno" );

        while (!service.valido( nome )) {
            nome = JOptionPane.showInputDialog( "Nome do Aluno" );
        }

        return service.findByName( nome );
    }

    @Override
    public Aluno persist(Aluno aluno) {

        var valido = false;

        String nome = null;

        do {
            nome = JOptionPane.showInputDialog("Nome do Aluno", Objects.nonNull(aluno) && Objects.nonNull(aluno.getNome()) ? aluno.getNome() : "");
        } while (!service.valido(nome));

        String email = null;

        do {
            email = JOptionPane.showInputDialog("e-mail do Aluno", Objects.nonNull(aluno) && Objects.nonNull(aluno.getEmail()) ? aluno.getEmail() : "");
            valido = service.validarEmail(email);
        } while (valido == false);


        Aluno a = new Aluno(resultSet.getLong("ID_CLIENTE"), resultSet.getString("NM_CLIENTE"));
        a.setNome(nome).setEmail(email).setMatricula(service.gerarMatricula());
        return service.persist(a);

    }
}
