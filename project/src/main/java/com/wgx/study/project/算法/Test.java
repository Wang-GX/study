package com.wgx.study.project.算法;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://www.cnblogs.com/onepixel/articles/7674659.html
 */
public class Test {
    public static void main(String[] args) {
        Integer[] arr = {3, 38, 5, 15, 26};
        //bubbleSort(arr);
        //selectionSort(arr);
        insertionSort(arr);
        List list = new ArrayList<>(Arrays.asList(arr));//UnsupportedOperationException
        System.out.println(list);
    }

    /**
     * 冒泡排序
     * 它重复地走访过要排序的数列，一次比较两个元素，如果它们的顺序错误就把它们交换过来
     * @param arr
     * @return
     */
    public static Integer[] bubbleSort(Integer[] arr) {
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {        // 相邻元素两两对比
                    int temp = arr[j + 1];        // 元素交换
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }


    /**
     * 选择排序
     * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
     *
     * @return
     */
    public static Integer[] selectionSort(Integer[] arr) {
        int length = arr.length;
        int minIndex;
        int temp;
        for(int i = 0; i < length - 1; i++) {
            minIndex = i;
            for(int j = i + 1; j < length; j++) {
                if(arr[j] < arr[minIndex]) {     // 寻找最小的数
                    minIndex = j;                 // 将最小数的索引保存
                }
            }
            temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        return arr;
    }


    /**
     * 插入排序
     * 构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入
     * @param arr
     * @return
     */
    public static Integer[] insertionSort(Integer[] arr) {
        int length = arr.length;
        int preIndex;
        int current;
        for(int i = 1; i < length; i++) {
            preIndex = i - 1;
            current = arr[i];
            while(preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
        return arr;
    }
}
