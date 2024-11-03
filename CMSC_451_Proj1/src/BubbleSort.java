
public class BubbleSort extends AbstractSort {

  @Override
  public int[] sort(int[] arr) {
	  int[] array = arr.clone(); // need to clone array to avoid sequential sorting in place issue
	  int n = array.length;
	  for (int i = 0; i < n-1; i++) {
		  for (int j = 0; j < n-i-1; j++) {
			  if (array[j] > array[j+1]) {
					  int temp = array[j];
					  array[j] = array[j+1];
					  array[j+1] = temp;
					  incrementCount();
					  // incrementSwaps();
				  }
			  }
		  // incrementComparisons();
		  incrementCount();
		  }
	  return array;
	  }
}
