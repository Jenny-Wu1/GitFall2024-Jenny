import java.io.*;
import java.util.*;

/**
 * This class handles file reading, determining if the inputs are valid or invalid
 * and retrieving the data needed for the histogram like the number of cards, and total energy costs.
 */
public class FileReader {
    private int numOfCards;
    private int totalCost;
    private ArrayList<String> invalidCards;
    private int[] histogram;

    /**
     * Constructor that initializes the file reader and the invalid cards list
     * and adds base values for the number of cards, and total cost.
     */
    public FileReader() {
        this.numOfCards = 0;
        this.totalCost = 0;
        this.invalidCards = new ArrayList<>();
        this.histogram = new int[7];
    }

    /**
     * This method reads the specified file and processes the card entries in the file.
     * It determines the validity of each card while keeping track of the invalid cards,
     * accumulates the total cost, and keeps count the total number of valid cards.
     * The steps are as follows:
     * 1. Reads the file line by line.
     * 2. The input for each line is split into the card name and cost by the colon (:).
     * 3. Validates the card and updates the data (total cost, card count, histogram).
     * 4. Tracks invalid cards and adds any found to its separate list.
     * 5. Stops reading if the file contains more than 1000 cards.
     *
     * @param fileName               The name of the file with the deck information.
     * @throws FileNotFoundException If the file doesn't exist or is unreadable.
     */
    public void readFile(String fileName) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(fileName));

        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] parts = line.split(":");

            if(parts.length == 2) {
                String name = parts[0].trim();
                try {
                    int cost = Integer.parseInt(parts[1].trim());
                    if (isValid(name, cost)) {
                        totalCost += cost;
                        histogram[cost]++;
                        numOfCards++;
                    } else {
                        invalidCards.add(line);
                    }
                } catch (NumberFormatException e) {
                    invalidCards.add(line);
                }
            } else {
                invalidCards.add(line);
            }
            if (numOfCards > 1000) {
                break;
            }
        }
        scan.close();
    }

    /**
     * This method returns the total energy cost of all the valid cards in the deck.
     *
     * @return An integer that represents the total energy cost.
     */
    public int getTotalCost() {
        return totalCost;
    }

    /**
     * This method returns the total amount of valid cards in the deck.
     *
     * @return An integer representing the total amount of valid cards.
     */
    public int getNumOfCards() {
        return numOfCards;
    }

    /**
     * This method returns the histogram that has the energy cost distribution
     * of the valid cards in the deck.
     *
     * @return An array of integers where each index corresponds to an energy cost
     *         from 0-6 (inclusive), ad the value at each index corresponds to the
     *         number of cards with that specific cost.
     */
    public int[] getHistogram() {
        return histogram;
    }

    /**
     * This method returns a list of invalid card entries found in the deck.
     *
     * @return An arraylist of strings that represent the invalid cards.
     */
    public ArrayList<String> getInvalidCards() {
        return invalidCards;
    }

    /**
     * This method determines if each card entry has
     * a valid name and energy cost.
     *
     * @param name The name of the card.
     * @param cost The energy cost of the card.
     * @return     True if the card is valid (non-empty name and cost between [0-6]) or false otherwise.
     */
    public boolean isValid(String name, int cost) {
        boolean valid = !name.isEmpty() && cost >= 0 && cost <= 6;
        return valid;
    }
}
