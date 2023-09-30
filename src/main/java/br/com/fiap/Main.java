package br.com.fiap;

import br.com.fiap.domain.entity.Aluno;
import br.com.fiap.domain.view.AlunoView;
import br.com.fiap.domain.view.MenuView;

import javax.swing.*;
import java.sql.Connection;

public class Main {

    public static Connection get;

    public static void main(String[] args) {

        MenuView view = new MenuView();
        view.show();

    }

    public static Connection getConnection() {
        return null;
    }
}