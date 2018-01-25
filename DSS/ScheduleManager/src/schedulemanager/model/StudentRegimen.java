package schedulemanager.model;

public enum StudentRegimen {

	STUDENT, WORKERSTUDENT;
    
    @Override
    public String toString() {
	    switch(this) {
	      case STUDENT: return "Regime Normal";
	      case WORKERSTUDENT: return "Trabalhador-Estudante";
	      default: throw new IllegalArgumentException();
	    }
    }
}
