package br.com.fiap.domain.service;

import br.com.fiap.domain.entity.Turma;
import br.com.fiap.exception.ConnectionFactory;

import java.util.List;

public class TurmaService implements Service<Turma, Long> {
    @Override
    public List<Turma> findAll() {
        throw new ConnectionFactory("Método não Implementado");
    }

    @Override
    public Turma findById(Long id) {
        throw new ConnectionFactory("Método não Implementado");
    }

    @Override
    public List<Turma> findByName(String texto) {
        throw new ConnectionFactory("Método não Implementado");
    }

    @Override
    public Turma persist(Turma turma) {
        throw new ConnectionFactory("Método não Implementado");
    }
}
