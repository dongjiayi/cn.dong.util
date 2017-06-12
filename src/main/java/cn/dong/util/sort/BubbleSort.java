package cn.dong.util.sort;

public class BubbleSort extends CommonSort {

	public static <T extends Comparable<T>> void sort(T[] array, int start,
			int end, int interval) {
		end = (end + 1 - start) / interval * interval - 1 + start;
		for (int i = end; i > start; i -= interval) {
			for (int j = start; j < i; j += interval){
				if (array[j+ interval].compareTo(array[j ]) < 0){
					swap(array, j, j+interval);
				}
			}
		}
	}

	public static <T extends Comparable<T>> void sort(T[] array) {
		sort(array, 0, array.length - 1, 1);
	}
}
