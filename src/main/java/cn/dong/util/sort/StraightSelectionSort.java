package cn.dong.util.sort;

public class StraightSelectionSort extends CommonSort {

	public static <T extends Comparable<T>> void sort(T[] array, int start, int end,
			int interval) {
		int min;
		for (int i = start; i <= end; i += interval) {
			min = i;
			for (int j = i; j <= end; j += interval) {
				if (array[j].compareTo(array[min]) < 0){
					min = j;
				}
			}

			if (min == i){
				continue;
			}
			swap(array, min, i);
		}
	}

	public static <T extends Comparable<T>> void sort(T[] array) {
		sort(array, 0, array.length - 1, 1);
	}
}
