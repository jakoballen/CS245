public abstract class UWECPerson {
    private int uwecId;
    private String firstName;
    private String lastName;
    private String title;

    public UWECPerson(int uwecId, String firstName, String lastName) {
        this.uwecId = uwecId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public final int getUwecId() {
        return uwecId;
    }

    public final String getFirstName() {
        return firstName;
    }

    public final String getLastName() {
        return lastName;
    }

    public final String getTitle(){return title;}

    public void setTitle(String title){ this.title = title;}

    public abstract String toString();
    /*public String toString() {
        return "UWECPerson = uwecId: " + uwecId + ", name: " + firstName + " " + lastName+", title: "+title;
    }*/

    public boolean equals(Object other) {
        /*return other.toString().equals(toString());*/
        if (other instanceof UWECPerson) {
            UWECPerson o = (UWECPerson) other;

            return this.uwecId == o.uwecId &&
                    this.firstName.equals(o.firstName) &&
                    this.lastName.equals(o.lastName)&&
                    this.title.equals(o.title);

        } else {
            return false;
        }
    }
}


