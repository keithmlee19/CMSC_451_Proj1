import java.io.FileWriter;
import java.io.IOException;

public class BenchmarkSorts {
	private final static int NUM_DATASETS = 40;
	private static int[][] sortingData;
	
	private static int[][] generateSortingData(int datasetSize) {
		int[][] generatedData = new int[NUM_DATASETS][datasetSize];
		for (int i = 0; i < NUM_DATASETS; i++) {
			for (int j = 0; j < datasetSize; j++) {
				generatedData[i][j] = (int)(Math.random() * 10000);
			}
		}
		return generatedData;
	}

  public static void main(String[] args) throws IOException {
	  // 12 evenly spaced data set sizes
	  int[] datasetSizes = new int[]{500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000};
	  
	  // create sorts
	  AbstractSort bubbleSort = new BubbleSort();
	  AbstractSort insertionSort = new InsertionSort();
	  
	  // initialize FileWriters for outputs
	  FileWriter bubbleWriter = new FileWriter("bubbleOutput.txt");
	  FileWriter insertionWriter = new FileWriter("insertionOutput.txt");
	    
	  // benchmark each sort
	  for (int size : datasetSizes) {
		  // create data set of the current size to be sorted by both algorithms
		  sortingData = generateSortingData(size);
		  
		  // benchmark bubble sort
		  // write size to file
		  bubbleWriter.write(size + " ");
		  // sort data, write outputs to file
		  for (int[] dataSet : sortingData) {
			  bubbleSort.startSort();
			  bubbleSort.sort(dataSet);
			  bubbleSort.endSort();
			  bubbleWriter.write(bubbleSort.getCount() + " " + bubbleSort.getTime() + " ");
		  }
		  bubbleWriter.write("\n");
		  
		  // benchmark insertion sort
		  // write size to file
		  insertionWriter.write(size + " ");
		  // sort data, write outputs to file
		  for (int[] dataSet : sortingData) {
			  insertionSort.startSort();
			  insertionSort.sort(dataSet);
			  insertionSort.endSort();
			  insertionWriter.write(insertionSort.getCount() + " " + insertionSort.getTime() + " ");
		  }
		  insertionWriter.write("\n");
	  }
	  // close fileWriters
	  bubbleWriter.close();
	  insertionWriter.close();
  }
}
