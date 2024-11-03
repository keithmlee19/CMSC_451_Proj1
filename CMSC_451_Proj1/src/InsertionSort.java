
public class InsertionSort extends AbstractSort {

  @Override
  public int[] sort(int[] arr) {
	  int[] array = arr.clone(); // need to clone array to avoid sequential sorting in place issue
	  int n = array.length;
	  for (int i = 1; i < n; i++) {
		  int key = array[i];
		  int j = i-1;
		  while (j >= 0 && array[j] > key) {
			  array[j+1] = array[j];
			  j--;
			  // incrementSwaps();
			  incrementCount();
			  }
		  array[j+1] = key;
		  // incrementComparisons();
		  incrementCount();
		  }
	  return array;
	  }
}
