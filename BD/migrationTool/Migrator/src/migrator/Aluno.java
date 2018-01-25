/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migrator;

import java.util.ArrayList;

/**
 *
 * @author Vitor Castro
 */
public class Aluno {

    private int Numero;
    private String Nome;
    private String Curso;
    private ArrayList<Turno> Turnos;

    public Aluno(int numero, String nome, String curso, ArrayList<Turno> turnos) {

        Numero = numero;
        Nome = nome;
        Curso = curso;
        Turnos = turnos;
    }

}
