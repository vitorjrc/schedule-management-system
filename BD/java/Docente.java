
import java.util.*;

public class Docente {

	private int numero;
	private String nome;
	private String escola;
	private ArrayList<Turno> turnos;

	public Docente(int numero, String nome, String escola, ArrayList<Turno> turnos) {

		this.numero = numero;
		this.nome = nome;
		this.escola = escola;
		this.turnos = turnos;
	}

}
