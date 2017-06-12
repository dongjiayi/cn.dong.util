package cn.dong.util.sort;

public class InsertSort extends CommonSort {

	public static <T extends Comparable<T>> void sort(T[] array, int start,
			int end, int interval) {
		for (int i = start + interval; i <= end; i += interval) {
			for (int j = i; j > start; j -= interval) {
				if (array[j].compareTo(array[j - interval]) < 0) {
					swap(array, j, j - interval);
				} else
					break;
			}
		}
	}

	public static <T extends Comparable<T>> void sort(T[] array) {
		sort(array, 0, array.length - 1, 1);
	}
}
