public abstract class UWECAcademic extends UWECPerson {
    private int numTotalCredits;

    public UWECAcademic(int uwecId, String firstName, String lastName){
        super(uwecId, firstName,lastName);
    }

    public final int getNumTotalCredits(){
        return numTotalCredits;
    }
    public void setNumTotalCredits(int numTotalCredits){
        this.numTotalCredits=numTotalCredits;
    }

    public abstract String toString();

    public boolean equals(Object other){
        if(other instanceof UWECAcademic){
            UWECAcademic o = (UWECAcademic) other;
            return super.equals(o) && o.getNumTotalCredits()==numTotalCredits;
        }else{
            return false;
        }

    }

}
