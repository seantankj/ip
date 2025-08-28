import java.util.Scanner;

public class Griddybot {
    public static void main(String[] args) {
        String line = "_________________________________\n";
        System.out.println(line + "Hello! I'm Griddybot.\n" + "What can I do for you?\n" + line);

        boolean isExit = false;

        while (!isExit) {
            String inputLine;
            Scanner input = new Scanner(System.in);
            inputLine = input.nextLine();

            switch (inputLine) {
            case "bye":
                System.out.println(line + "Bye. Come back soon!\n" + line);
                isExit = true;
                break;
            default:
                System.out.println(line + inputLine + "\n" + line);
            }
        }
    }
}
