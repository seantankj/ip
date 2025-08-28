import java.util.ArrayList;
import java.util.Scanner;

public class Griddybot {
    public static void main(String[] args) {
        String line = "_________________________________" + System.lineSeparator();
        System.out.println(line + "Hello! I'm Griddybot." + System.lineSeparator() + "What can I do for you?" + System.lineSeparator() + line);

        boolean isExit = false;
        ArrayList<String> listItems = new ArrayList<>();
        int counter = 0;

        while (!isExit) {
            String inputLine;
            Scanner input = new Scanner(System.in);
            inputLine = input.nextLine();

            switch (inputLine) {
            case "bye":
                System.out.println(line + "Bye. Come back soon!" + System.lineSeparator() + line);
                isExit = true;
                break;
            case "list":
                System.out.print(line);
                for (String item : listItems) {
                    counter++;
                    System.out.print(counter + ". " + item + System.lineSeparator());
                }
                System.out.print(line);
                break;
            default:
                listItems.add(inputLine);
                System.out.println(line + "added: " + inputLine + System.lineSeparator() + line);
            }
        }
    }
}
