import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import com.itextpdf.text.Image;
import java.io.File;
import java.io.IOException;

/**
 * This class handles the creation of the histogram chart from the deck information.
 */
public class Histogram {

    /**
     * This method creates a histogram chart that represents the energy cost distribution
     * of the valid cards in the deck. The steps are as follows:
     * 1. Creates a dataset for the chart based on the provided histogram data that contains
     *    the number of cards and their respective energy costs.
     * 2. Configures the chart's axes and bar margins.
     * 3. Saves the resulting chart as a png file.
     *
     * @param histogram    An Array that represents the number of cards for each energy cost.
     * @throws IOException If an error occurs while trying to save the chart as a png file.
     */
    public void createChart(int[] histogram) throws IOException {
        DefaultCategoryDataset data = new DefaultCategoryDataset();

        //Populate the dataset with histogram information (card count, energy costs)
        for(int i = 0; i < histogram.length; i++) {
            data.addValue(histogram[i], "Number of Cards", i + " energy");
        }

        //Create the chart and its respective axes
        JFreeChart chart = ChartFactory.createBarChart("Card Cost Distribution", "Energy Cost",
                "Number of Cards", data, PlotOrientation.VERTICAL, false, false, false);

        //gets the max number of cards that's associated with an energy count in the deck
        int maxCards = 0;
        for(int count : histogram) {
            if (count > maxCards) {
                maxCards = count;
            }
        }

        //Configure the y-axis (number of cards side)
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setRange(0, maxCards + 1); //sets the upper and lower bounds for the y-axis
        range.setTickUnit(new NumberTickUnit(1)); //Ensures the values increment by 1

        //Configure the margin space between the bars of the energy cost distribution
        CategoryAxis axis = plot.getDomainAxis();
        axis.setLowerMargin(0);
        axis.setUpperMargin(0);
        axis.setCategoryMargin(0);

        //Saves the chart as a png image file
        File chartFile = new File("histogram.png");
        ChartUtilities.saveChartAsPNG(chartFile, chart, 600, 600);
    }

    /**
     * This method adds the generated histogram chart to the pdf document. It has the following steps:
     * 1. Gets the png file with the histogram chart.
     * 2. Scales the chart to fit inside the document.
     * 3. Adds the image to the document.
     *
     * @param document The PDF file that the chart will be added onto
     * @throws Exception 
     */
    public void addToPdf(com.itextpdf.text.Document document) throws Exception {
        Image chartPic = Image.getInstance("histogram.png");
        chartPic.scaleToFit(500, 500);
        document.add(chartPic);
    }
}
