/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migrator;

/**
 *
 * @author Vitor Castro
 */
public class UC {

    private int Codigo;
    private String Nome;
    private int Ano;
    private int ECTS;

    public UC(int codigoUC, String nomeUC, int ano, int ects) {

        Codigo = codigoUC;
        Nome = nomeUC;
        Ano = ano;
        ECTS = ects;
    }

}
