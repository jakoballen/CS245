
public class UWECPerson {
    private int uwecId;
    private String firstName;
    private String lastName;

    public UWECPerson(int uwecId, String firstName, String lastName) {
        this.uwecId = uwecId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUwecId() {
        return uwecId;
    }

    public void setUwecId(int uwecId) {
        this.uwecId = uwecId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String toString() {
        return "UWECPerson = uwecId: " + uwecId + ", name: " + firstName + " " + lastName;
    }

    public boolean equals(Object other) {
        /*return other.toString().equals(toString());*/
        if (other instanceof UWECPerson) {
            UWECPerson o = (UWECPerson) other;

            return this.uwecId == o.uwecId &&
                    this.firstName.equals(o.firstName) &&
                    this.lastName.equals(o.lastName);

        } else {
            return false;
        }
    }
}


