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
public class Turno {

    private int UC_Codigo;
    private String UC_Nome;
    private String Turno;
    private int AnoLetivo;

    public Turno(int codigoUC, String nomeUC, String turno, int anoLetivo) {

        UC_Codigo = codigoUC;
        UC_Nome = nomeUC;
        Turno = turno;
        AnoLetivo = anoLetivo;
    }

}
