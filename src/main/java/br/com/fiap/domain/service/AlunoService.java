package br.com.fiap.domain.service;

import br.com.fiap.domain.entity.Aluno;
import br.com.fiap.exception.ConnectionFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class AlunoService implements Service<Aluno, Long> {


    @Override
    public List<Aluno> findAll() {
        throw new ConnectionFactory("Método não Implementado");
    }

    @Override
    public Aluno findById(Long id) {
        throw new ConnectionFactory("Método não Implementado");
    }

    @Override
    public List<Aluno> findByName(String texto) {
        throw new ConnectionFactory("Método não Implementado");
    }

    /**
     * Gere matricula de forma randômica.
     * e-mail deve ser validado
     * id deve ser gerado pelo repository
     *
     * @param aluno
     * @return
     */
    @Override
    public Aluno persist(Aluno aluno) {
        throw new ConnectionFactory("Método não Implementado");
    }

    /**
     * Validando email
     * @param emailAddress
     * @return
     */


    /**
     * Gerando matricula randomicamente
     * @return
     */
    public String gerarMatricula() {
        Random r = new Random();
        var matricula = LocalDate.now().getYear() + "." + r.nextInt(1000, 9999) + "-" + r.nextInt(10, 99);
        return matricula;
    }

}
