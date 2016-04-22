package com.com.juson.algorithms;

/**
 * Created by juson on 4/18/16.
 */
public class GenArray {

    public static void genIntArray(int[] a, int bits){

        int len = a.length;

        for (int i = 0 ; i < len; i++){
            a[i] = (int) Math.round(Math.random()*bits);
        }

    }
}
