import java.util.Random;
import java.util.Scanner;

/**
 * This class runs the program by reading the deck file, processing the information,
 * and generating a PDF report containing details about the deck.
 */
public class Main {
    /**
     * This is the main method that runs the program. The steps are as follows:
     * 1. Prompts the user to enter the file name of the deck.
     * 2. Reads the file using the FileReader class and processing the information of the cards.
     * 3. Generates a random 9-digit ID deck number for the report.
     * 4. A void report is generated if the deck has more than 1000 card entries or
     *    contains more than 10 invalid cards, otherwise a regular report is generated that
     *    has the deck ID number, total energy cost, the histogram of the energy cost distribution,
     *    and an invalid card section if there are any.
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        FileReader read = new FileReader();
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the name of the file containing");
        String fileName = input.nextLine();
        try {
            read.readFile(fileName);
        } catch (Exception e) {
            System.out.println("Error reading deck: " + e.getMessage());
            return;
        }

        Random random = new Random();
        int deckID = 100000000 + random.nextInt(900000000);

        Report report = new Report();
        if (read.getNumOfCards() > 1000 || read.getInvalidCards().size() > 10) {
            report.generateVoidReport(deckID);
        } else {
            report.generateReport(deckID, read.getTotalCost(), read.getHistogram(), read.getInvalidCards());
        }
        input.close();
    }
}
