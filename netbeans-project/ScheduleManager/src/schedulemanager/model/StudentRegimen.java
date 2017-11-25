package schedulemanager.model;

public enum StudentRegimen {
    STUDENT, WORKERSTUDENT;
    
    public static StudentRegimen convert(String str) {
        for (StudentRegimen regimen : StudentRegimen.values()) {
            if (regimen.toString().equals(str)) {
                return regimen;
            }
        }
        return null;
    }
    
 @Override
  public String toString() {
    switch(this) {
      case STUDENT: return "Regime Normal";
      case WORKERSTUDENT: return "Trabalhador-Estudante";
      default: throw new IllegalArgumentException();
    }
  }
}
