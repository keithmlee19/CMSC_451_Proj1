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
  /* Computes the coefficients of variance of a list */
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
    String[] columnNames = {"Size", "Avg Count", "Coef Count", "Avg Time", "Coef Time"};
    DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
    JTable table = new JTable(dtm);
    DecimalFormat df = new DecimalFormat("#.00");

    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(filePath));
      String line;
      while ((line = br.readLine()) != null) {
        String[] data = line.split(" ");
        int size = Integer.parseInt(data[0]);

        ArrayList<Double> counts = new ArrayList<>();
        ArrayList<Double> times = new ArrayList<>();

        for (int i = 1; i < data.length - 1; i += 2) {
          counts.add(Double.parseDouble(data[i]));
          times.add(Double.parseDouble(data[i + 1]));
        }

        double sumCount = 0;
        for (double count : counts) {
          sumCount += count;
        }
        double avgCount = sumCount / counts.size();
        double coefCount = computeCoef(counts, avgCount);

        double sumTime = 0;
        for (double time : times) {
          sumTime += time;
        }
        double avgTime = sumTime / times.size();
        double coefTime = computeCoef(times, avgTime);

        String avgCountString = df.format(avgCount);
        String coefCountString = df.format(coefCount) + "%";
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

    JFrame frame = new JFrame("Benchmark Report");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new JScrollPane(table));
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {
      String filePath = fileChooser.getSelectedFile().getPath();
      createReport(filePath);
    }
  }
}
