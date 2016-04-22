package com.com.juson.algorithms;

/**
 * Created by juson on 4/16/16.
 *
 * 基本排序：
 * 归并（Merge）排序法是将两个（或两个以上）有序表合并成一个新的有序表，
 * 即把待排序序列分为若干个子序列，每个子序列是有序的。然后再把有序子序列合并为整
 * 体有序序列。
 */
public class MergeSort {

    private static int[] aux;
    //原地归并排序: 有序数组 a[0 ~ mid] 和 a[mid+1 ~ hi]
    private static void merge(int[] a, int lo, int mid, int hi, TestUpdate testUpdate){
        int l = lo;
        int r = mid+1;

        for (int i = lo; i <= hi; i++) {
            aux[i] = a[i];
        }
       // System.out.println(String.format("%2d %2d %2d", lo, mid, hi));

        for (int k = l; k <= hi; k++){
            int rec = 0;
            if (l > mid)                      { rec = r; a[k] = aux[r++]; } //左边用完，使用右边
            else if(r > hi)                   { rec = l; a[k] = aux[l++]; } //右边用完，使用左边
            else if(aux[r] < aux[l])          { rec = r; a[k] = aux[r++]; } //取右边最小的元素
            else                              { rec = l; a[k] = aux[l++]; } //取左边最小的元素

            testUpdate.onUpdate(a, rec, k);
        }

       // System.out.println(Arrays.toString(a));
    }


    private static void sort(int[] a, int lo, int hi, TestUpdate testUpdate){

        if (hi <= lo) return;
        int mid = lo + (hi-lo)/2;
        sort(a, lo, mid, testUpdate);          // 将左半部分排序
        sort(a, mid+1, hi, testUpdate);          // 将又半部分排序
        merge(a, lo, mid, hi, testUpdate);     // 归并结果

    }

    //自顶向下排序
    public static void sort(int[] a, TestUpdate testUpdate){

        aux = new int[a.length];//alloc buffer
        sort(a, 0, a.length-1, testUpdate);
    }

    //自底向上排序
    public static void sort1(int[] a, TestUpdate testUpdate){
        int N = a.length;
        aux = new int[N];
        for (int sz = 1; sz < N; sz += sz) {
            for (int lo = 0; lo < N-sz; lo += sz+sz) {
//                System.out.println(String.format("N:%d, sz:%d, lo:%d", N, sz, lo));
                merge(a, lo, lo+sz-1, lo+sz+sz-1, testUpdate);
            }
        }
    }

}
