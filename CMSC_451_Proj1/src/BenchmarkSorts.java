import java.io.FileWriter;
import java.io.IOException;

public class BenchmarkSorts {
	private final static int NUM_DATASETS = 40;
	private static int[][] sortingData;
	private static int[][] warmupData;
	
	private static int[][] generateWarmupData() {
		int[][] generatedData = new int[5000][100]; // 5000 datasets of size 100
		for (int i = 0; i < 5000; i++) {
			for (int j = 0; j < 100; j++) {
				generatedData[i][j] = (int)(Math.random() * 1000);
			}
		}
		return generatedData;
	}
	
	private static int[][] generateSortingData(int datasetSize) {
		int[][] generatedData = new int[NUM_DATASETS][datasetSize];
		for (int i = 0; i < NUM_DATASETS; i++) {
			for (int j = 0; j < datasetSize; j++) {
				generatedData[i][j] = (int)(Math.random() * 1000);
			}
		}
		return generatedData;
	}

  public static void main(String[] args) throws IOException, UnsortedException{
	  // 12 evenly spaced data set sizes
	  int[] datasetSizes = new int[]{100,200,300,400,500,600,700,800,900,1000,1100,1200};
	  
	  // create sorts
	  AbstractSort bubbleSort = new BubbleSort();
	  AbstractSort insertionSort = new InsertionSort();
	  
	  // initialize FileWriters for outputs
	  FileWriter bubbleWriter = new FileWriter("bubbleOutput.txt");
	  FileWriter insertionWriter = new FileWriter("insertionOutput.txt");
	  
	  // 5000 warmup runs per sort - not recorded
	  warmupData = generateWarmupData();
	  for (int i = 0; i < warmupData.length; i++) {
		  bubbleSort.startSort();
		  int[] bubbleSortedData = bubbleSort.sort(warmupData[i]);			  
		  bubbleSort.endSort();
		  assert bubbleSort.isSorted(bubbleSortedData);
		  
		  insertionSort.startSort();
		  int[] insertionSortedData = insertionSort.sort(warmupData[i]);			  
		  insertionSort.endSort();
		  assert insertionSort.isSorted(insertionSortedData);
	  }
	    
	  // actual benchmarking
	  for (int size : datasetSizes) {
		  // create 40 datasets of the current size to be sorted by both algorithms
		  sortingData = generateSortingData(size);
		  
		  // benchmark bubble sort
		  // write size to file
		  bubbleWriter.write(size + " ");
		  // sort data, write outputs to file
		  for (int i = 0; i < sortingData.length; i++) {
			  bubbleSort.startSort();
			  int[] bubbleSortedData = bubbleSort.sort(sortingData[i]);			  
			  bubbleSort.endSort();
			  assert bubbleSort.isSorted(bubbleSortedData);
			  bubbleWriter.write(bubbleSort.getCount() + " " + bubbleSort.getTime() + " ");
			  // bubbleWriter.write("Count: " + bubbleSort.getCount() + " Comparisons: " + bubbleSort.getComparisons() + " Swaps: " + bubbleSort.getSwaps() + " ");
		  }
		  bubbleWriter.write("\n");		  
		  
		  // benchmark insertion sort
		  // write size to file
		  insertionWriter.write(size + " ");
		  // sort data, write outputs to file
		  for (int i = 0; i < sortingData.length; i++) {
			  insertionSort.startSort();
			  int[] insertionSortedData = insertionSort.sort(sortingData[i]);			  
			  insertionSort.endSort();
			  assert insertionSort.isSorted(insertionSortedData);
			  insertionWriter.write(insertionSort.getCount() + " " + insertionSort.getTime() + " ");
			  // insertionWriter.write("Count: " + insertionSort.getCount() + " Comparisons: " + insertionSort.getComparisons() + " Swaps: " + insertionSort.getSwaps() + " ");
		  }
		  insertionWriter.write("\n");
	  }
	  // close fileWriters
	  bubbleWriter.close();
	  insertionWriter.close();
  }
}
