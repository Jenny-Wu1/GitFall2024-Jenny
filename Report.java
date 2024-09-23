import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;
import java.util.ArrayList;

/**
 * This class handles the report generation for both valid and void decks.
 */
public class Report {
    public Histogram histogram;

    /**
     * Constructor that initializes the instance of the histogram class.
     */
    public Report() {
        this.histogram = new Histogram();
    }

    /**
     * This method generates a report for valid decks. The steps are as follows:
     * 1. Creates and opens a new PDF document following the specified naming format.
     * 2. Adds the deck ID number and the total energy cost to the document.
     * 3. Generates a histogram based on the energy cost distribution and adds the
     *    resulting png file to the pdf.
     * 4. If there are any invalid cards in the deck, then an additional section is included in the pdf
     *    after the histogram with those specific cards.
     * 5. The document is closed, and the console reports if the pdf creation was successful or unsuccessful.
     * @param deckID        The ID number of the deck
     * @param totalCost     The total energy costs of all the valid cards in the deck.
     * @param histogramData An array that represents the energy cost distribution of the valid cards in the deck.
     * @param invalidCards  A list of the invalid card entries found in the deck.
     */
    public void generateReport(int deckID, int totalCost, int[] histogramData, ArrayList<String> invalidCards) {
        try {
            //PDF file creation
            Document document = new Document();
            String pdfFileName = "SpireDeck_" + deckID + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));

            //Adding the deck ID number and total energy costs
            document.open();
            document.add(new Paragraph("Deck ID: " + deckID));
            document.add(new Paragraph("Total Energy Cost: " + totalCost + " energy"));
            document.add(new Paragraph("\n"));

            //Creating the histogram for the deck and adding it to the pdf
            this.histogram.createChart(histogramData);
            this.histogram.addToPdf(document);
            document.add(new Paragraph("\n"));

            //Adds a section for invalid cards if there are any
            if(!invalidCards.isEmpty()) {
                document.add(new Paragraph("Invalid Cards: "));
                for(String invalidCard : invalidCards) {
                    document.add(new Paragraph(invalidCard));
                }
            }

            document.close();
            System.out.println("Report generated: " + pdfFileName);
        } catch (Exception e) {
            System.out.println("Error creating PDF: " + e.getMessage());
        }
    }

    /**
     * This method generates a void PDF report that indicates that the deck contains more than 10 invalid cards,
     * or over 1000 cards. The steps are as follows:
     * 1. Creates and opens a new PDF document following the specified naming format.
     * 2. Adds the message VOID in the document.
     * 3. The document is closed, and the console reports if the pdf creation was successful or unsuccessful.
     *
     * @param deckID The ID number of the deck
     */
    public void generateVoidReport(int deckID) {
        try {
            //PDF file creation
            Document document = new Document();
            String pdfFileName = "SpireDeck_" + deckID + "(VOID).pdf";
            PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));

            //Adding void message to the pdf
            document.open();
            document.add(new Paragraph("VOID"));
            document.close();

            System.out.println("VOID report generated: " + pdfFileName);
        } catch (Exception e) {
            System.out.println("Error creating VOID PDF: " + e.getMessage());
        }
    }
}
