
public class UWECStaff extends UWECPerson {
    private String title;
    private double hourlyPay;
    private double hoursPerWeek;

    public UWECStaff(int uwecId, String firstName, String lastName) {
        super(uwecId, firstName, lastName);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getHourlyPay() {
        return hourlyPay;
    }

    public void setHourlyPay(double hourlyPay) {
        this.hourlyPay = hourlyPay;
    }

    public double getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(double hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }
    public String toString() {
        return "UWECStaff = uwecId: "+getUwecId()+", name: "+getFirstName()+" "+getLastName()+
                ", title: "+title+", hourly pay: "+hourlyPay+", hours/week: "+hoursPerWeek;
    }
    public boolean equals(Object other) {
        if (other instanceof UWECStaff) {
            UWECStaff o = (UWECStaff) other;

            return this.title.equals(o.title)&&
                    this.hourlyPay == o.hourlyPay &&
                    this.hoursPerWeek == o.hoursPerWeek &&
                    super.equals(o);

        } else {
            return false;
        }
    }

}

