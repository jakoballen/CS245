import java.text.DecimalFormat;
import java.util.*;
import java.io.*;
public class UWECPeopleDriver {
    // throw new IllegalArgumentException();
    public static void main(String[] args)  {
        try {
            System.out.print("Enter the name of the input file: ");
            Scanner stdIn = new Scanner(System.in);
            File input = new File(stdIn.nextLine());
            System.out.print("Enter the name of the output file: ");
            String output = stdIn.nextLine();
            PrintWriter fileOut = new PrintWriter(new File(output));
            Scanner fileIn = new Scanner(input);
            ArrayList<UWECPerson> universityPeople = new ArrayList<>();
            int menuChoice;
            do {
                menuChoice = getMenuChoice(fileIn);
                if (menuChoice == 1) {
                    addStudent(fileIn, universityPeople);
                } else if (menuChoice == 2) {
                    addStaff(fileIn, universityPeople);
                } else if (menuChoice == 3) {
                    addFaculty(fileIn, universityPeople);
                } else if (menuChoice == 4) {
                    computeTotalPayroll(fileOut, universityPeople);
                } else if (menuChoice == 5) {
                    printDirectory(fileOut, universityPeople);
                }
            } while (menuChoice != 6);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static void addStudent(Scanner fileIn, ArrayList<UWECPerson> universityPeople) {
        try {
            int id = fileIn.nextInt();
            String firstName = fileIn.next();
            String lastName = fileIn.next();
            int numCredits = fileIn.nextInt();
            double gpa = fileIn.nextDouble();

            UWECStudent s = new UWECStudent(id, firstName, lastName, gpa);
            s.setNumTotalCredits(numCredits);

            universityPeople.add(s);
        } catch (InputMismatchException e) {
            System.out.println("Unexpected input received in addStudent.");
            fileIn.nextLine();
        }
    }

    public static void addStaff(Scanner fileIn, ArrayList<UWECPerson> universityPeople){
            try {
                int id = fileIn.nextInt();
                String firstName = fileIn.next();
                String lastName = fileIn.next();
                String title = fileIn.next();

                UWECStaff s = new UWECStaff(id, firstName, lastName, title);
                s.setHourlyPay(fileIn.nextDouble());
                s.setHoursPerWeek(fileIn.nextDouble());

                universityPeople.add(s);
            } catch (InputMismatchException e) {
                System.out.println("Unexpected input received in addStaff.");
                fileIn.nextLine();
            }
        }

    public static void addFaculty(Scanner fileIn, ArrayList<UWECPerson> universityPeople) {
        try {
            int id = fileIn.nextInt();
            String firstName = fileIn.next();
            String lastName = fileIn.next();
            int credits = fileIn.nextInt();
            int yearlySalary = fileIn.nextInt();

            UWECFaculty s = new UWECFaculty(id, firstName, lastName, credits);
            s.setYearlySalary(yearlySalary);

            universityPeople.add(s);
        } catch (InputMismatchException e) {
            System.out.println("Unexpected input received in addFaculty.");
            fileIn.nextLine();

        }
    }

    public static void printDirectory(PrintWriter fileOut, ArrayList<UWECPerson> universityPeople) {
        if(universityPeople.size()!=0) {
            for (UWECPerson s : universityPeople) {
                fileOut.println(s);
               // System.out.println(s);
            }
        }
    }


    public static void computeTotalPayroll(PrintWriter fileOut, ArrayList<UWECPerson> universityPeople) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        double total = 0.00;
        if (universityPeople.size() != 0) {
            for (UWECPerson s : universityPeople) {
                /*if (s instanceof UWECFaculty) {
                    UWECFaculty c = (UWECFaculty) s;
                    total += c.computePaycheck();
                } else if (s instanceof UWECStaff) {
                    UWECStaff c = (UWECStaff) s;
                    total += c.computePaycheck();
                }*/
                if(s instanceof UWECEmployee){
                    UWECEmployee e = (UWECEmployee) s;
                    total += e.computePaycheck();
                }
            }
            fileOut.println("Total payroll: $" + df.format(total));
        }else{
            fileOut.println("Total payroll: $0.00");
        }
    }

    public static int getMenuChoice(Scanner fileIn) {
        if(fileIn.hasNextInt()){
            return fileIn.nextInt();
        }else{
            System.out.println("Non-integer entered in getMenuChoice.");
            return 6;
        }

    }


}



