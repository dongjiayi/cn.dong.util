package cn.dong.util.sort;

public class QuickSort extends CommonSort {

	public static <T extends Comparable<T>> void sort(T[] array, int start,
			int end, int interval) {
		end = (end + 1 - start) / interval * interval - 1 + start;

		int partition = start;
		for (int i = start; i <= end; i += interval) {
			if (array[i].compareTo(array[partition]) < 0) {
				for (int j = i; j > partition; j -= interval) {
					swap(array, j, j - interval);
				}
				partition += interval;
			}
		}
		if (partition > start)
			sort(array, start, partition - interval, interval);
		if (partition < end)
			sort(array, partition + interval, end, interval);
	}

	public static <T extends Comparable<T>> void sort(T[] array) {
		sort(array, 0, array.length - 1, 1);
	}
}
