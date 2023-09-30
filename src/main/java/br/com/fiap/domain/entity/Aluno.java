package br.com.fiap.domain.entity;

public class Aluno {

    private Long id;

    private String nome;

    private String email;

    private String matricula;

    public Aluno(long idCliente, String nmCliente) {
    }

    public Aluno(Long id, String nome, String matricula, String email) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public Aluno setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Aluno setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getMatricula() {
        return matricula;
    }

    public Aluno setMatricula(String matricula) {
        this.matricula = matricula;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Aluno setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
