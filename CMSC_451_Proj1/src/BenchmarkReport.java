import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BenchmarkReport {
  private static double computeCoef(ArrayList<Long> values, long mean) {
    double sumOfSquares = 0;
    for (long value : values) {
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

    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(filePath));
      String line;
      while ((line = br.readLine()) != null) {
        String[] data = line.split(" ");
        int size = Integer.parseInt(data[0]);

        ArrayList<Long> counts = new ArrayList<>();
        ArrayList<Long> times = new ArrayList<>();

        for (int i = 1; i < data.length - 1; i += 2) {
          counts.add(Long.parseLong(data[i]));
          times.add(Long.parseLong(data[i + 1]));
        }

        long sumCount = 0;
        for (long count : counts) {
          sumCount += count;
        }
        long avgCount = (long) sumCount / counts.size();
        double coefCount = computeCoef(counts, avgCount);

        long sumTime = 0;
        for (long time : times) {
          sumTime += time;
        }
        long avgTime = (long) sumTime / times.size();
        double coefTime = computeCoef(times, avgTime);

        dtm.addRow(new Object[] {size, avgCount, coefCount, avgTime, coefTime});
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
