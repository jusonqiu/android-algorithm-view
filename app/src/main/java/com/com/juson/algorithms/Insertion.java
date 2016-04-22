package com.com.juson.algorithms;

/**
 * Created by juson on 4/18/16.
 */
public class Insertion {

    public static void sort(int[] a, TestUpdate testUpdate){

        for (int i = 0; i < a.length; i++) {
            for (int j = i; j > 0 ; j--) {
                if (a[j] < a[j-1]){
                    int tmp = a[j];
                    a[j]  = a[j-1];
                    a[j-1] = tmp;

                    testUpdate.onUpdate(a, j-1, j);
                }
            }
        }
    }
}
