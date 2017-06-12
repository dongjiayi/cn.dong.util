package cn.dong.util.sort;

import java.util.LinkedList;

public class MergeSort extends CommonSort {

	public static <T extends Comparable<T>> void sort(T[] array, int start,
			int end, int interval) {
		int size = (end + 1 - start) / interval;
		end = size * interval + start - 1;
		LinkedList<T> tmp = new LinkedList<T>();
		for (int length = 1; length / 2 < size; length *= 2) {
			int low, high, lowEnd, highEnd, mergedSize;
			for (int i = start; i + length * interval < end; i += length
					* interval * 2) {
				tmp.clear();
				mergedSize = i + length * interval * 2 <= end ? length
						* interval * 2 : end - i + 1;
				low = i;
				high = lowEnd = i + length * interval;
				highEnd = i + mergedSize;
				while (low < lowEnd && high < highEnd)
					if (array[low].compareTo(array[high]) < 0) {
						tmp.add(array[low]);
						low += interval;
					} else {
						tmp.add(array[high]);
						high += interval;
					}
				while (low < lowEnd) {
					tmp.add(array[low]);
					low += interval;
				}
				while (high < highEnd) {
					tmp.add(array[high]);
					high += interval;
				}
				for (int j = 0; j < mergedSize; j += interval){
					array[j + i] = tmp.get(j);
				}
			}
		}
	}

	public static <T extends Comparable<T>> void sort(T[] array) {
		sort(array, 0, array.length - 1, 1);
	}
}
