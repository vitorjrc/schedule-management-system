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
public class Docente {

    private int Numero;
    private String Nome;
    private String Escola;
    private ArrayList<UC> UCS;

    public Docente(int numero, String nome, String escola, ArrayList<UC> ucs) {

        Numero = numero;
        Nome = nome;
        Escola = escola;
        UCS = ucs;
    }

}
