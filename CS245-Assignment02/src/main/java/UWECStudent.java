
public class UWECStudent extends UWECAcademic {
    private double gpa;

    public UWECStudent(int uwecId, String firstName, String lastName, double gpa){
        super(uwecId,firstName,lastName);
        this.gpa = gpa;
        setNumTotalCredits(0); //or setTitle("Freshman");

    }
    public void setNumTotalCredits(int numTotalCredits){
        super.setNumTotalCredits(numTotalCredits);
        if(numTotalCredits<24){
            super.setTitle("Freshman");
        }else if(numTotalCredits<58){
            super.setTitle("Sophomore");
        }else if(numTotalCredits<86){
            super.setTitle("Junior");
        }else{
            super.setTitle("Senior");
        }
    }

    public final double getGpa() {
        return gpa;
    }
    public String toString() {
        return "UWECStudent = uwecId: "+getUwecId()+", name: "+getFirstName()+" "+getLastName()+", title: "+getTitle()+", credits: "+getNumTotalCredits()+", gpa: "+gpa;
    }
    public boolean equals(Object other) {
        /*if (other instanceof UWECStudent) {
            UWECStudent o = (UWECStudent) other;
            return this.gpa == o.getGpa() && super.equals(o);
        } else {
            return false;
        }*/
        return toString().equals(other.toString());
    }
}
