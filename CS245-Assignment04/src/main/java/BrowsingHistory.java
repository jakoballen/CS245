import java.util.Scanner;

public class BrowsingHistory {
    public static void main(String[] args) {
        Stack<String> history = new Stack<>();
        Scanner stdIn = new Scanner(System.in);
        String userInput=null;
        String currentURL = "null";


        do {
            if(history.isEmpty()){
                System.out.print("Enter a URL or \"quit\": ");
                userInput = stdIn.nextLine();

                if(history.isEmpty()&&userInput.equals("back")){
                    System.out.println("No URL to go back to");
                    System.out.println();
                    if(!currentURL.equals("null")){
                        System.out.println("Current URL: "+currentURL);
                    }

                }else if(!userInput.equals("quit")&&history.isEmpty()){
                    if(!currentURL.equals("null")) {
                        history.push(currentURL);
                    }
                    currentURL = userInput;
                    System.out.println("Current URL: "+currentURL);
                    System.out.println();
                }
            }else{
                System.out.print("Enter a URL, \"back\", or \"quit\": ");
                userInput = stdIn.nextLine();

                if(userInput.equals("back")){
                    if(history.isEmpty()){
                        System.out.println("No URL to go back to");
                        System.out.println();
                    }else{
                        currentURL=history.pop();
                        System.out.println("Current URL: "+currentURL);
                        System.out.println();
                    }

                }else{
                    history.push(currentURL);
                    currentURL = userInput;
                    System.out.println("Current URL: "+currentURL);
                }
            }
        }while(!userInput.equals("quit"));
    }
}