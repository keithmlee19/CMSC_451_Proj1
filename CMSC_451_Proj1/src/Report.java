import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Report {
  /* Computes the coefficients of variance of a list
   * given the mean of the list */
  private static double computeCoef(ArrayList<Double> values, double mean) {
    double sumOfSquares = 0;
    for (double value : values) {
      sumOfSquares += Math.pow(value - mean, 2);
    }
    double variance = sumOfSquares / values.size();
    double stdev = Math.sqrt(variance);
    return (stdev / mean) * 100;
  }

  private static void createReport(String filePath) {
    // column names
    String[] columnNames = {"Size", "Avg Count", "Coef Count", "Avg Time", "Coef Time"};   
    // table to house data
    DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
    JTable table = new JTable(dtm);
    DecimalFormat df = new DecimalFormat("#.00"); // format to 2 decimal places

    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(filePath));
      String line;
      while ((line = br.readLine()) != null) { // while the next line is not null
        String[] data = line.split(" ");
        int size = Integer.parseInt(data[0]); // first entry is the size of the array

        // arraylists to hold counts and times from each line
        ArrayList<Double> counts = new ArrayList<>();
        ArrayList<Double> times = new ArrayList<>();

        for (int i = 1; i < data.length - 1; i += 2) {
          counts.add(Double.parseDouble(data[i])); // add the odd-numbered array entries to counts
          times.add(Double.parseDouble(data[i + 1])); // add even-numbered to times
        }

        double sumCount = 0;
        for (double count : counts) {
          sumCount += count;
        }
        double avgCount = sumCount / counts.size(); // get average of counts
        double coefCount = computeCoef(counts, avgCount); // get coefficient of variance of counts

        double sumTime = 0;
        for (double time : times) {
          sumTime += time;
        }
        double avgTime = sumTime / times.size(); // get average of times
        double coefTime = computeCoef(times, avgTime); // get coefficient of variance of times

        String avgCountString = df.format(avgCount); // format to 2 decimal places
        String coefCountString = df.format(coefCount) + "%"; // format to 2 decimal places with %
        String avgTimeString = df.format(avgTime);
        String coefTimeString = df.format(coefTime) + "%";

        dtm.addRow(
            new Object[] {size, avgCountString, coefCountString, avgTimeString, coefTimeString});
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // display table
    JFrame frame = new JFrame("Benchmark Report");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new JScrollPane(table));
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    // new JFileChooser
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(null);

    // display report from chosen file
    if (result == JFileChooser.APPROVE_OPTION) {
      String filePath = fileChooser.getSelectedFile().getPath();
      createReport(filePath);
    }
  }
}
