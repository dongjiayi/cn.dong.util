package cn.dong.util.sort;

class CommonSort {
	
	protected static <T extends Comparable<T>> void swap(T[] array, int a, int b) {
		T tmp = array[b];
		array[b] = array[a];
		array[a] = tmp;
	}
}
