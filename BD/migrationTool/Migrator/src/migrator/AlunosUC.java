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
public class AlunosUC {

    private int Codigo;
    private String Nome;
    private int Ano;
    private int ECTS;
    private ArrayList<Integer> Alunos;

    public AlunosUC(int codigoUC, String nomeUC, int ano, int ects, ArrayList<Integer> alunos) {

        Codigo = codigoUC;
        Nome = nomeUC;
        Ano = ano;
        ECTS = ects;
        Alunos = alunos;
    }
}
