package cn.dong.util.sort;

public class HeapSort extends CommonSort {
	
	private static <T extends Comparable<T>> void BuildHeap(T[] array,
			int start, int end, int interval) {
		int child;

		for (int i = (end + 1 - start) / 2; i > start; i -= interval) {
			child = i * 2 - 1;
			if ((child + interval) <= end
					&& array[child].compareTo(array[child + interval]) < 0) {
				child += interval;
			}

			if (array[i - 1].compareTo(array[child]) < 0) {
				swap(array, i - 1, child);
				
				HeapAdjust(array, child, end, interval);
			}
		}
	}

	private static <T extends Comparable<T>> void HeapAdjust(T[] array,
			int index, int end, int interval) {
		int child = (index + 1) * 2 - 1;
		if (child > end) {
			return;
		}
		if ((child + interval) <= end
				&& array[child].compareTo(array[child + interval]) < 0) {
			child += interval;
		}
		if (array[index].compareTo(array[child]) < 0) {
			
			swap(array, index, child);
			
			HeapAdjust(array, child, end, interval);
		}
	}

	public static <T extends Comparable<T>> void sort(T[] array, int start,
			int end, int interval) {

		end = (end + 1 - start) / interval * interval - 1 + start;

		BuildHeap(array, start, end, interval);
		for (int i = end; i > start; i -= interval) {
			swap(array, i, start);
			HeapAdjust(array, start, i - interval, interval);
		}
	}

	public static <T extends Comparable<T>> void sort(T[] array) {
		sort(array, 0, array.length - 1, 1);
	}
}
