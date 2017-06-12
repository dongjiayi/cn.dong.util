package cn.dong.util.sort;

public class ShellSort extends CommonSort {

	public static <T extends Comparable<T>> void sort(T[] array, int start, int end,
			int interval) {
		int length = (end + 1 - start) / interval;
		for (int h = length / 2; h > 0; h = h / 2) {
			InsertSort.sort(array, start, end, interval * h);
		}
	}

	public static <T extends Comparable<T>> void sort(T[] array) {
		sort(array, 0, array.length - 1, 1);
	}
}
