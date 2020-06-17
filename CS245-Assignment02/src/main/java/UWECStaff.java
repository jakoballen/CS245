
public class UWECStaff extends UWECPerson implements UWECEmployee {
    private double hourlyPay;
    private double hoursPerWeek;

    public UWECStaff(int uwecId, String firstName, String lastName, String title) {
        super(uwecId, firstName, lastName);
        setTitle(title);
    }



    final public double getHourlyPay() {
        return hourlyPay;
    }

    public void setHourlyPay(double hourlyPay) {
        this.hourlyPay = hourlyPay;
    }

    final public double getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(double hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public double computePaycheck() {
        return 2 * hourlyPay * hoursPerWeek;
    }

    public String toString() {
        return "UWECStaff = uwecId: "+getUwecId()+", name: "+getFirstName()+" "+getLastName()+
                ", title: "+getTitle()+", hourly pay: "+hourlyPay+", hours/week: "+hoursPerWeek;
    }



    public boolean equals(Object other) {
        if (other instanceof UWECStaff) {
            UWECStaff o = (UWECStaff) other;

            return this.hourlyPay == o.hourlyPay &&
                    this.hoursPerWeek == o.hoursPerWeek &&
                    super.equals(o);

        } else {
            return false;
        }
    }

}

