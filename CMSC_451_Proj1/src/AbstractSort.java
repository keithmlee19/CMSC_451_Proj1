public abstract class AbstractSort {
  protected int count;
  // protected int comparisons, swaps;
  protected long startTime, endTime, elapsedTime;

  public abstract int[] sort(int[] array);

  protected void startSort() {
    count = 0;
    // swaps = 0;
    // comparisons = 0;
    startTime = System.nanoTime();
  }

  protected void endSort() {
    endTime = System.nanoTime();
  }

  protected void incrementCount() {
    count++;
  }

  /*
  protected void incrementComparisons() {
  	comparisons++;
  }

  protected void incrementSwaps() {
  	swaps++;
  }
  */

  public int getCount() {
    return count;
  }

  /*
  public int getComparisons() {
  	return comparisons;
  }

  public int getSwaps() {
  	return swaps;
  }
  */

  public long getTime() {
    elapsedTime = endTime - startTime;
    return elapsedTime;
  }

  protected boolean isSorted(int[] data) throws UnsortedException {
    for (int i = 0; i < data.length - 1; i++) {
      if (data[i] > data[i + 1]) { // if any value is greater than the one to its right
        throw new UnsortedException("Array not sorted"); // then array is not sorted
      }
    }
    return true;
  }
}
