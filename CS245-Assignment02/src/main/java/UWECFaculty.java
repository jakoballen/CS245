public class UWECFaculty extends UWECAcademic implements UWECEmployee {
    private double yearlySalary;

    public UWECFaculty(int uwecId, String firstName, String lastName, int numTotalCredits) {
        super(uwecId, firstName, lastName);
        setNumTotalCredits(numTotalCredits);
        if(numTotalCredits<48){
            setTitle("Adjunct Professor");
        }else if(numTotalCredits<=119){
            setTitle("Assistant Professor");
        }else if(numTotalCredits<=239){
            setTitle("Associate Professor");
        }else{
            setTitle("Professor");
        }
    }
    public void setNumTotalCredits(int numTotalCredits){
        super.setNumTotalCredits(numTotalCredits);
        if(numTotalCredits<48){
            setTitle("Adjunct Professor");
        }else if(numTotalCredits<=119){
            setTitle("Assistant Professor");
        }else if(numTotalCredits<=239){
            setTitle("Associate Professor");
        }else{
            setTitle("Professor");
        }
    }

    public final double getYearlySalary() {
        return yearlySalary;
    }

    public void setYearlySalary(double yearlySalary) {
        this.yearlySalary = yearlySalary;
    }

    public double computePaycheck() {
        return yearlySalary/26;
    }

    public String toString() {
        return "UWECFaculty = uwecId: "+getUwecId()+", name: "+getFirstName()+" "+getLastName()+", title: "+getTitle()+", credits: "+getNumTotalCredits()+", yearlySalary: "+ yearlySalary;
    }

    public boolean equals(Object other) {
        if (other instanceof UWECFaculty) {
            UWECFaculty o = (UWECFaculty) other;
            return super.equals(o) && o.getYearlySalary() == yearlySalary;
        } else {
            return false;
        }
    }
}
