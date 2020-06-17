import javax.swing.*;

public class SortRunnable implements Runnable {

	private String sortingAlgorithm;
	private int[] arr;
	private Sorter sorter;

	private int size;

	public SortRunnable(String sortingAlgorithm, int[] arr, Sorter sorter) {
		this.sortingAlgorithm = sortingAlgorithm;
		this.arr = arr;
		this.sorter = sorter;
		this.size = arr.length;
	}


	@Override
	public void run() {
		Thread.yield();
		long start = System.currentTimeMillis();

		if (sortingAlgorithm.equals("Insertion Sort")) {
			InsertionSort.sort(arr);
		}
		if (sortingAlgorithm.equals("Merge Sort")) {
			MergeSort.mergeSort(arr);
		} else {
			SelectionSort.sort(arr);
		}
		long end = System.currentTimeMillis();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				sorter.displayResult(size, end - start);
			}
		});





	}
}