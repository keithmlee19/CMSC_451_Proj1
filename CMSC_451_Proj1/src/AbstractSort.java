
public abstract class AbstractSort {
	protected int count;
	protected long startTime, endTime, elapsedTime;
	
	public abstract int[] sort(int[] array);
	
	protected void startSort() {
		startTime = System.nanoTime();
	}
	
	protected void endSort() {
		endTime = System.nanoTime();
	}
	
	protected void incrementCount() {
		count++;
	}
	
	public int getCount() {
		return count;
	}
	
	public long getTime() {
		elapsedTime = endTime - startTime;
		return elapsedTime;
	}
}
