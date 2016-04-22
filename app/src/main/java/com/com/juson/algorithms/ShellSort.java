package com.com.juson.algorithms;

/**
 * Created by juson on 4/18/16.
 */
public class ShellSort {

    //Shell 排序使用系列：1/2(3^k-1)
    public static void sort(int[] a, TestUpdate testUpdate){
        int N = a.length;
        int h = 1;
        while(h < N/3) h = 3*h+1;

        while(h >= 1) {
            for (int i = h; i < N; i ++) {
                for (int j = i; j >= h; j -= h) {
                    if (a[j - h] > a[j]) {
                        int tmp = a[j - h];
                        a[j - h] = a[j];
                        a[j] = tmp;
                        testUpdate.onUpdate(a, j-h, j);
                    }
                }//for
            }//for
            h = h/3;
        }


    }


}
