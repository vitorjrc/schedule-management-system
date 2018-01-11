
import java.util.*;

public class Turno {

	private int codigoUC;
	private String nomeUC;
	private int ano;
	private int ects;
	private String turno;
	private int anoLetivo;
	private ArrayList<Docente> docentes;
	private ArrayList<Aluno> alunos;

	public Turno(int codigoUC, String nomeUC, int ano, int ects, String turno, int anoLetivo, ArrayList<Docente> docentes, Arraylist<Aluno> alunos) {

		this.codigoUC = codigoUC;
		this.nomeUC = nomeUC;
		this.ano = ano;
		this.ects = ects;
		this.turno = turno;
		this.anoLetivo = anoLetivo;
		this.docentes = docentes;
		this.alunos = alunos;
	}

}
